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

package io.entgra.device.mgt.plugins.virtualfirealarm.agent.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import io.entgra.device.mgt.plugins.virtualfirealarm.agent.communication.http.FireAlarmHTTPCommunicator;
import io.entgra.device.mgt.plugins.virtualfirealarm.agent.communication.mqtt.FireAlarmMQTTCommunicator;
import io.entgra.device.mgt.plugins.virtualfirealarm.agent.communication.xmpp.FireAlarmXMPPCommunicator;
import io.entgra.device.mgt.plugins.virtualfirealarm.agent.exception.AgentCoreOperationException;
import io.entgra.device.mgt.plugins.virtualfirealarm.agent.transport.TransportHandler;
import io.entgra.device.mgt.plugins.virtualfirealarm.agent.transport.TransportHandlerException;
import io.entgra.device.mgt.plugins.virtualfirealarm.agent.transport.TransportUtils;
import io.entgra.device.mgt.plugins.virtualfirealarm.agent.virtual.VirtualHardwareManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgentManager {

    private static final Log log = LogFactory.getLog(AgentManager.class);
    private static AgentManager agentManager;
    private String rootPath = "";

    private boolean deviceReady = false;
    private boolean isAlarmOn = false;

    private String deviceName, agentStatus;

    private int pushInterval;               // seconds
    private String prevProtocol, protocol;

    private String networkInterface;
    private List<String> interfaceList, protocolList;
    private Map<String, TransportHandler> agentCommunicator;

    private AgentConfiguration agentConfigs;

    private String deviceIP;
    private String enrollmentEP;
    private String ipRegistrationEP;
    private String pushDataAPIEP;

    private AgentManager() {
    }

    public static AgentManager getInstance() {
        if (agentManager == null) {
            agentManager = new AgentManager();
        }
        return agentManager;
    }

    public void init() {

        agentCommunicator = new HashMap<>();
        // Read IoT-Server specific configurations from the 'deviceConfig.properties' file
        try {
            this.agentConfigs = AgentUtilOperations.readIoTServerConfigs();
        } catch (AgentCoreOperationException e) {
            log.error("Reading device configuration from configuration file failed:\n");
            log.error(e);
            System.exit(0);
        }

        // Initialise IoT-Server URL endpoints from the configuration read from file
        AgentUtilOperations.initializeServerEndPoints();
        // Set the hostNameVerifier to the APIM-Server IPAddress to enable HTTPS handshake
        AgentUtilOperations.setHTTPSConfigurations();

        String analyticsPageContext = String.format(AgentConstants.DEVICE_ANALYTICS_PAGE_URL,
                                                    agentConfigs.getDeviceId(),
                                                    agentConfigs.getDeviceName());

        String controlPageContext = String.format(AgentConstants.DEVICE_DETAILS_PAGE_EP,
                                                  AgentConstants.DEVICE_TYPE,
                                                  agentConfigs.getDeviceId());

        this.agentStatus = AgentConstants.NOT_REGISTERED;
        this.deviceName = this.agentConfigs.getDeviceName();

        this.pushInterval = this.agentConfigs.getDataPushInterval();
        this.networkInterface = AgentConstants.DEFAULT_NETWORK_INTERFACE;

        this.protocol = AgentConstants.DEFAULT_PROTOCOL;
        this.prevProtocol = protocol;

        Map<String, String> xmppIPPortMap;
        try {
            xmppIPPortMap = TransportUtils.getHostAndPort(agentConfigs.getXmppServerEndpoint());
            String xmppServer = xmppIPPortMap.get("Host");
            int xmppPort = Integer.parseInt(xmppIPPortMap.get("Port"));

            TransportHandler xmppCommunicator = new FireAlarmXMPPCommunicator(xmppServer, xmppPort);
            agentCommunicator.put(AgentConstants.XMPP_PROTOCOL, xmppCommunicator);

        } catch (TransportHandlerException e) {
            log.info("XMPP Endpoint String - " + agentConfigs.getXmppServerEndpoint() +
                    ", provided in the configuration file is invalid. XMPP is not configured.");
        }
        String mqttTopic = String.format(AgentConstants.MQTT_SUBSCRIBE_TOPIC, agentConfigs.getTenantDomain(),
                                         agentConfigs.getDeviceId());

//        TransportHandler httpCommunicator = new FireAlarmHTTPCommunicator();
        TransportHandler mqttCommunicator = new FireAlarmMQTTCommunicator(agentConfigs.getDeviceOwner(),
                                                                          agentConfigs.getDeviceId(),
                                                                          agentConfigs.getMqttBrokerEndpoint(),
                                                                          mqttTopic);

//        agentCommunicator.put(AgentConstants.HTTP_PROTOCOL, httpCommunicator);
        agentCommunicator.put(AgentConstants.MQTT_PROTOCOL, mqttCommunicator);

        try {
            interfaceList = new ArrayList<>(TransportUtils.getInterfaceIPMap().keySet());
            protocolList = new ArrayList<>(agentCommunicator.keySet());
        } catch (TransportHandlerException e) {
            log.error("An error occurred whilst retrieving all NetworkInterface-IP mappings");
        }

        //Initializing hardware at that point
        //AgentManger.setDeviceReady() method should invoked from hardware after initialization
        VirtualHardwareManager.getInstance().init();

        //Wait till hardware get ready
        while (!deviceReady) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.info(AgentConstants.LOG_APPENDER + "Sleep error in 'device ready-flag' checking thread");
            }
        }

//        try {
//            if (!EnrollmentManager.getInstance().isEnrolled()) {
//                EnrollmentManager.getInstance().beginEnrollmentFlow();
//            }
//        } catch (AgentCoreOperationException e) {
//            log.error("Device Enrollment Failed:\n");
//            log.error(e);
//            System.exit(0);
//        }

        //Start agent communication
        agentCommunicator.get(protocol).connect();
    }

    private void switchCommunicator(String stopProtocol, String startProtocol) {
        agentCommunicator.get(stopProtocol).disconnect();

        while (agentCommunicator.get(stopProtocol).isConnected()) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                log.info(AgentConstants.LOG_APPENDER +
                                 "Sleep error in 'Switch-Communicator' Thread's shutdown wait.");
            }
        }

        agentCommunicator.get(startProtocol).connect();
    }

    public void setInterface(int interfaceId) {
        if (interfaceId != -1) {
            String newInterface = interfaceList.get(interfaceId);

            if (!newInterface.equals(networkInterface)) {
                networkInterface = newInterface;

                if (protocol.equals(AgentConstants.HTTP_PROTOCOL) && !protocol.equals(
                        prevProtocol)) {
                    switchCommunicator(prevProtocol, protocol);
                }
            }
        }
    }

    public void setProtocol(int protocolId) {
        if (protocolId != -1) {
            String newProtocol = protocolList.get(protocolId);

            if (!protocol.equals(newProtocol)) {
                prevProtocol = protocol;
                protocol = newProtocol;
                switchCommunicator(prevProtocol, protocol);
            }
        }
    }

    public void changeAlarmStatus(boolean isOn) {
        VirtualHardwareManager.getInstance().changeAlarmStatus(isOn);
        isAlarmOn = isOn;
    }

    public void updateAgentStatus(String status) {
        this.agentStatus = status;
    }

	/*------------------------------------------------------------------------------------------*/
    /* 		            Getter and Setter Methods for the private variables                 	*/
    /*------------------------------------------------------------------------------------------*/

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setDeviceReady(boolean deviceReady) {
        this.deviceReady = deviceReady;
    }

    public AgentConfiguration getAgentConfigs() {
        return agentConfigs;
    }

    public String getDeviceIP() {
        return deviceIP;
    }

    public void setDeviceIP(String deviceIP) {
        this.deviceIP = deviceIP;
    }

    public String getEnrollmentEP() {
        return enrollmentEP;
    }

    public void setEnrollmentEP(String enrollmentEP) {
        this.enrollmentEP = enrollmentEP;
    }

    public String getIpRegistrationEP() {
        return ipRegistrationEP;
    }

    public void setIpRegistrationEP(String ipRegistrationEP) {
        this.ipRegistrationEP = ipRegistrationEP;
    }

    public String getPushDataAPIEP() {
        return pushDataAPIEP;
    }

    public void setPushDataAPIEP(String pushDataAPIEP) {
        this.pushDataAPIEP = pushDataAPIEP;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getNetworkInterface() {
        return networkInterface;
    }

    public String getAgentStatus() {
        return agentStatus;
    }

    public int getPushInterval() {
        return pushInterval;
    }

    public void setPushInterval(int pushInterval) {
        this.pushInterval = pushInterval;
        TransportHandler transportHandler = agentCommunicator.get(protocol);

        switch (protocol) {
            case AgentConstants.HTTP_PROTOCOL:
                ((FireAlarmHTTPCommunicator) transportHandler).getDataPushServiceHandler()
                        .cancel(true);
                break;
            case AgentConstants.MQTT_PROTOCOL:
                ((FireAlarmMQTTCommunicator) transportHandler).getDataPushServiceHandler()
                        .cancel(true);
                break;
            case AgentConstants.XMPP_PROTOCOL:
                ((FireAlarmXMPPCommunicator) transportHandler).getDataPushServiceHandler()
                        .cancel(true);
                break;
            default:
                log.warn("Unknown protocol " + protocol);
        }
        transportHandler.publishDeviceData();

        if (log.isDebugEnabled()) {
            log.debug("The Data Publish Interval was changed to: " + pushInterval);
        }
    }

    public List<String> getInterfaceList() {
        return interfaceList;
    }

    public List<String> getProtocolList() {
        return protocolList;
    }

    /**
     * Get temperature reading from device
     *
     * @return Temperature
     */
    public int getTemperature() {
        return VirtualHardwareManager.getInstance().getTemperature();
    }

    /**
     * Get humidity reading from device
     *
     * @return Humidity
     */
    public int getHumidity() {
        return VirtualHardwareManager.getInstance().getHumidity();
    }

    public boolean isAlarmOn() {
        return isAlarmOn;
    }
}
