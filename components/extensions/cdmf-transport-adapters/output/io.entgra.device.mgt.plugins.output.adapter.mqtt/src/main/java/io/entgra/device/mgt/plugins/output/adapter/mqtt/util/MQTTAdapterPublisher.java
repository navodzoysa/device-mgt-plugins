/*
 * Copyright (c) 2018 - 2023, Entgra (Pvt) Ltd. (http://www.entgra.io) All Rights Reserved.
 *
 * Entgra (Pvt) Ltd. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package io.entgra.device.mgt.plugins.output.adapter.mqtt.util;

import io.entgra.device.mgt.core.apimgt.keymgt.extension.DCRResponse;
import io.entgra.device.mgt.core.apimgt.keymgt.extension.TokenRequest;
import io.entgra.device.mgt.core.apimgt.keymgt.extension.TokenResponse;
import io.entgra.device.mgt.core.apimgt.keymgt.extension.exception.BadRequestException;
import io.entgra.device.mgt.core.apimgt.keymgt.extension.exception.KeyMgtException;
import io.entgra.device.mgt.core.apimgt.keymgt.extension.service.KeyMgtService;
import io.entgra.device.mgt.core.apimgt.keymgt.extension.service.KeyMgtServiceImpl;
import io.entgra.device.mgt.core.identity.jwt.client.extension.exception.JWTClientException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.ssl.Base64;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.jetbrains.annotations.NotNull;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.event.output.adapter.core.exception.ConnectionUnavailableException;
import org.wso2.carbon.event.output.adapter.core.exception.OutputEventAdapterException;
import org.wso2.carbon.event.output.adapter.core.exception.OutputEventAdapterRuntimeException;
import org.wso2.carbon.user.api.UserStoreException;

import java.util.ArrayList;
import java.util.List;

/**
 * MQTT publisher related configuration initialization and publishing capabilties are implemented here.
 */
public class MQTTAdapterPublisher {

    private static final Log log = LogFactory.getLog(MQTTAdapterPublisher.class);
    private MqttClient mqttClient;
    private MQTTBrokerConnectionConfiguration mqttBrokerConnectionConfiguration;
    String clientId;
    int tenantId;

    private String tenantDomain;

    public MQTTAdapterPublisher(MQTTBrokerConnectionConfiguration mqttBrokerConnectionConfiguration, String clientId
            , int tenantId) {
        this.tenantId = tenantId;
        this.tenantDomain = PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantDomain();
        if (clientId == null || clientId.trim().isEmpty()) {
            this.clientId = MqttClient.generateClientId();
        }
        this.mqttBrokerConnectionConfiguration = mqttBrokerConnectionConfiguration;
        connect();
    }

    public void connect() {
        if (clientId == null || clientId.trim().isEmpty()) {
            clientId = MqttClient.generateClientId();
        }
        boolean cleanSession = mqttBrokerConnectionConfiguration.isCleanSession();
        int keepAlive = mqttBrokerConnectionConfiguration.getKeepAlive();
        String temp_directory = System.getProperty(MQTTEventAdapterConstants.ADAPTER_TEMP_DIRECTORY_NAME);
        MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(temp_directory);
        try {
            MqttConnectOptions connectionOptions = new MqttConnectOptions();
            connectionOptions.setCleanSession(cleanSession);
            connectionOptions.setKeepAliveInterval(keepAlive);
            if (mqttBrokerConnectionConfiguration.getUsername() != null) {
                String accessToken = getToken();
                connectionOptions.setUserName(accessToken.substring(0, 18));
                connectionOptions.setPassword(accessToken.substring(19).toCharArray());
            }
            // Construct an MQTT blocking mode client
            mqttClient = new MqttClient(mqttBrokerConnectionConfiguration.getBrokerUrl(), clientId, dataStore);
            mqttClient.connect(connectionOptions);

        } catch (MqttException e) {
            log.error("Error occurred when constructing MQTT client for broker url : "
                              + mqttBrokerConnectionConfiguration.getBrokerUrl(), e);
            handleException(e);
        }
    }

    public boolean isConnected() {
        return mqttClient.isConnected();
    }

    public void publish(int qos, String payload, String topic) {
        try {
            // Create and configure a message
            MqttMessage message = new MqttMessage(payload.getBytes());
            message.setQos(qos);
            mqttClient.publish(topic, message);
        } catch (MqttException e) {
            log.error("Error occurred when publishing message for MQTT server : " + mqttClient.getServerURI(), e);
            handleException(e);
        }
    }

    public void publish(String payload, String topic) {
        try {
            // Create and configure a message
            MqttMessage message = new MqttMessage(payload.getBytes());
            mqttClient.publish(topic, message);
        } catch (MqttException e) {
            log.error("Error occurred when publishing message for MQTT server : " + mqttClient.getServerURI(), e);
            handleException(e);
        }
    }

    public void close() throws OutputEventAdapterException {
        try {
            mqttClient.disconnect(1000);
            mqttClient.close();
        } catch (MqttException e) {
            throw new OutputEventAdapterException(e);
        }
    }

    private void handleException(MqttException e) {
        //Check for Client not connected exception code and throw ConnectionUnavailableException
        if (e.getReasonCode() == 32104) {
            throw new ConnectionUnavailableException(e);
        } else {
            throw new OutputEventAdapterRuntimeException(e);
        }
    }

    private String getToken() {
        String username = this.mqttBrokerConnectionConfiguration.getUsername();
        String password = this.mqttBrokerConnectionConfiguration.getPassword();
        String dcrUrlString = this.mqttBrokerConnectionConfiguration.getDcrUrl();
        List<String> supportedGrantTypes = new ArrayList<>();

        if (dcrUrlString != null && !dcrUrlString.isEmpty()) {
            PrivilegedCarbonContext.startTenantFlow();
            PrivilegedCarbonContext.getThreadLocalCarbonContext().setTenantDomain(tenantDomain, true);
            try {
                KeyMgtService keyMgtService = new KeyMgtServiceImpl();
                String applicationName = MQTTEventAdapterConstants.APPLICATION_NAME_PREFIX
                        + mqttBrokerConnectionConfiguration.getAdapterName();
                DCRResponse dcrResponse = keyMgtService.dynamicClientRegistration(applicationName, username,
                        "client_credentials", null, new String[]{"device_management"}, false, Integer.MAX_VALUE, password,
                        supportedGrantTypes, dcrUrlString);
                return getToken(dcrResponse.getClientId(), dcrResponse.getClientSecret());
//                connectionOptions.setUserName(accessToken.substring(0, 18));
//                connectionOptions.setPassword(accessToken.substring(19).toCharArray());


            } catch (JWTClientException | UserStoreException e) {
                log.error("Failed to create an oauth token with client_credentials grant type.", e);
                throw new OutputEventAdapterRuntimeException("Failed to create an oauth token with client_credentials grant type.", e);
            } catch (KeyMgtException e) {
                log.error("Failed to create an application.", e);
                throw new OutputEventAdapterRuntimeException("Failed to create an application.", e);
            } finally {
                PrivilegedCarbonContext.endTenantFlow();
            }
        }
        throw new OutputEventAdapterRuntimeException("Invalid configuration for mqtt publisher");
    }

    private String getToken(String clientId, String clientSecret)
            throws UserStoreException, JWTClientException {
        try {
            TokenRequest tokenRequest = getTokenRequest(clientId, clientSecret);
            KeyMgtService keyMgtService = new KeyMgtServiceImpl();
            TokenResponse tokenResponse = keyMgtService.generateAccessToken(tokenRequest);

            return tokenResponse.getAccessToken();
        } catch (KeyMgtException | BadRequestException e) {
            log.error("Error while generating access token", e);
        }
        return null;
    }

    @NotNull
    private TokenRequest getTokenRequest(String clientId, String clientSecret) {
        String scopes = mqttBrokerConnectionConfiguration.getScopes();
        scopes += " perm:topic:pub:" + tenantDomain + ":+:+:operation";

        if (!StringUtils.isEmpty(mqttBrokerConnectionConfiguration.getTopic())) {
            scopes += " perm:topic:pub:" + mqttBrokerConnectionConfiguration.getTopic().replace("/",":");
        }

        TokenRequest tokenRequest = new TokenRequest(clientId, clientSecret,
                null, scopes.toString(), "client_credentials", null,
                null, null, null,  Integer.MAX_VALUE);
        return tokenRequest;
    }

    private String getBase64Encode(String key, String value) {
        return new String(Base64.encodeBase64((key + ":" + value).getBytes()));
    }

}
