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
package io.entgra.device.mgt.plugins.output.adapter.websocket.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import io.entgra.device.mgt.plugins.output.adapter.websocket.WebsocketEventAdapterFactory;
import io.entgra.device.mgt.plugins.output.adapter.websocket.WebsocketOutputCallbackControllerServiceImpl;
import org.wso2.carbon.event.output.adapter.core.OutputEventAdapterFactory;
import io.entgra.device.mgt.plugins.output.adapter.websocket.WebsocketOutputCallbackControllerService;
import org.wso2.carbon.event.stream.core.EventStreamService;
import org.wso2.carbon.registry.core.service.RegistryService;
import org.osgi.service.component.annotations.*;

@Component(
        name = "io.entgra.device.mgt.plugins.output.adapter.mqtt.internal.MQTTEventAdapterServiceComponent",
        immediate = true)
public class WebsocketLocalEventAdapterServiceComponent {

    private static final Log log = LogFactory.getLog(WebsocketLocalEventAdapterServiceComponent.class);

    /**
     * initialize the websocket adapter service here service here.
     *
     * @param context
     */
    @Activate
    protected void activate(ComponentContext context) {

        try {
            WebsocketEventAdapterFactory websocketEventAdapterFactory = new WebsocketEventAdapterFactory();
            context.getBundleContext().registerService(OutputEventAdapterFactory.class.getName()
                    , websocketEventAdapterFactory, null);
            WebsocketOutputCallbackControllerServiceImpl UIOutputCallbackRegisterServiceImpl =
                    new WebsocketOutputCallbackControllerServiceImpl();
            context.getBundleContext().registerService(WebsocketOutputCallbackControllerService.class.getName(),
                    UIOutputCallbackRegisterServiceImpl, null);

            websocketEventAdapterFactory.setBundleContext(context.getBundleContext());

            WebsocketEventAdaptorServiceDataHolder.registerUIOutputCallbackRegisterServiceInternal(
                    UIOutputCallbackRegisterServiceImpl);
            if (log.isDebugEnabled()) {
                log.debug("Successfully deployed the output websocket adapter service");
            }
        } catch (RuntimeException e) {
            log.error("Can not create the output websocket adapter service ", e);
        } catch (Throwable e) {
            log.error("Error occurred while activating UI Event Adapter Service Component", e);
        }
    }

    @Reference(
            name = "event.stream.service",
            service = org.wso2.carbon.event.stream.core.EventStreamService.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unsetEventStreamService")
    protected void setEventStreamService(EventStreamService eventStreamService) {
        if (log.isDebugEnabled()) {
            log.debug("Setting the EventStreamService reference for the UILocalEventAdaptor Service");
        }
        WebsocketEventAdaptorServiceDataHolder.registerEventStreamService(eventStreamService);
    }

    protected void unsetEventStreamService(EventStreamService eventStreamService) {
        if (log.isDebugEnabled()) {
            log.debug("Un-Setting the EventStreamService reference for the UILocalEventAdaptor Service");
        }
        WebsocketEventAdaptorServiceDataHolder.registerEventStreamService(null);
    }

    /**
     * Sets Registry Service.
     *
     * @param registryService An instance of RegistryService
     */
    @Reference(
            name = "registry.service",
            service = org.wso2.carbon.registry.core.service.RegistryService.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unsetRegistryService")
    protected void setRegistryService(RegistryService registryService) {
        if (log.isDebugEnabled()) {
            log.debug("Setting Registry Service");
        }
        WebsocketEventAdaptorServiceDataHolder.setRegistryService(registryService);
    }

    /**
     * Unsets Registry Service.
     *
     * @param registryService An instance of RegistryService
     */
    protected void unsetRegistryService(RegistryService registryService) {
        if (log.isDebugEnabled()) {
            log.debug("Un setting Registry Service");
        }
        WebsocketEventAdaptorServiceDataHolder.setRegistryService(null);
    }
}
