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

package io.entgra.device.mgt.plugins.input.adapter.mqtt.util;

import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.event.input.adapter.core.exception.InputEventAdapterException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PropertyUtils {
    private static final String TENANT_DOMAIN_PROPERTY = "\\$\\{tenantDomain\\}";
    private static final String DEVICE_TYPE_PROPERTY = "\\$\\{deviceType\\}";
    private static final String DEVICE_ID_PROPERTY = "\\$\\{deviceId\\}";

    //This method is only used if the mb features are within DAS.
    static String replaceMqttProperty(String urlWithPlaceholders) throws InputEventAdapterException {
        String regex = "\\$\\{(.*?)\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matchPattern = pattern.matcher(urlWithPlaceholders);
        while (matchPattern.find()) {
            String sysPropertyName = matchPattern.group(1);
            String sysPropertyValue = System.getProperty(sysPropertyName);
            if (sysPropertyValue != null && !sysPropertyName.isEmpty()) {
                urlWithPlaceholders = urlWithPlaceholders.replaceAll("\\$\\{(" + sysPropertyName + ")\\}", sysPropertyValue);
            } else {
                throw new InputEventAdapterException("System property - " + sysPropertyName
                        + " is not defined, hence cannot resolve : " + urlWithPlaceholders);
            }
        }
        return urlWithPlaceholders;
    }

    public static String replaceTenantDomainProperty (String urlWithPlaceholders) {
        urlWithPlaceholders = urlWithPlaceholders.replaceAll(TENANT_DOMAIN_PROPERTY, PrivilegedCarbonContext
                .getThreadLocalCarbonContext().getTenantDomain(true));
        return urlWithPlaceholders;
    }

    public static String replacePlaceholders(String urlWithPlaceholders) {
        urlWithPlaceholders = urlWithPlaceholders.replaceAll(TENANT_DOMAIN_PROPERTY, "+")
                .replaceAll(DEVICE_TYPE_PROPERTY, "+").replaceAll(DEVICE_ID_PROPERTY, "+");
        return urlWithPlaceholders;
    }
}
