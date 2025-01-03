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
package io.entgra.device.mgt.plugins.mobile.android.api.services.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.entgra.device.mgt.plugins.mobile.android.api.exception.BadRequestException;
import io.entgra.device.mgt.plugins.mobile.android.api.exception.UnexpectedServerErrorException;
import io.entgra.device.mgt.plugins.mobile.android.api.services.EventReceiverService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import io.entgra.device.mgt.core.device.mgt.common.Device;
import io.entgra.device.mgt.core.device.mgt.common.DeviceIdentifier;
import io.entgra.device.mgt.core.device.mgt.common.exceptions.DeviceManagementException;
import io.entgra.device.mgt.core.device.mgt.common.EnrolmentInfo;
import io.entgra.device.mgt.core.device.mgt.core.util.DeviceManagerUtil;
import io.entgra.device.mgt.plugins.mobile.android.api.bean.ErrorResponse;
import io.entgra.device.mgt.plugins.mobile.android.api.bean.wrapper.EventBeanWrapper;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/events")
public class EventReceiverServiceImpl implements EventReceiverService {
    private static final String EVENT_STREAM_DEFINITION = "org.wso2.iot.LocationStream";
    private static final Log log = LogFactory.getLog(EventReceiverServiceImpl.class);
    private Gson gson = new Gson();

    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";
    private static final String TIME_STAMP = "timeStamp";
    private static final String LOCATION_EVENT_TYPE = "location";

    @POST
    @Path("/publish")
    @Override
    public Response publishEvents(@Valid EventBeanWrapper eventBeanWrapper) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking Android device event logging.");
        }
//        Device device;
//        try {
//            if (!DeviceManagerUtil.isPublishLocationResponseEnabled()) {
//                return Response.status(Response.Status.ACCEPTED).entity("Event is publishing has not enabled.").build();
//            }
//            DeviceIdentifier deviceIdentifier = new DeviceIdentifier(eventBeanWrapper.getDeviceIdentifier(),
//                                                                     AndroidConstants.DEVICE_TYPE_ANDROID);
//            device = AndroidAPIUtils.getDeviceManagementService().getDevice(deviceIdentifier, false);
//            if (device != null && EnrolmentInfo.Status.ACTIVE != device.getEnrolmentInfo().getStatus()){
//                return Response.status(Response.Status.ACCEPTED).entity("Device is not in Active state.").build();
//            } else if (device == null){
//                return Response.status(Response.Status.ACCEPTED).entity("Device is not enrolled yet.").build();
//            }
//        } catch (DeviceManagementException e) {
//            log.error("Error occurred while checking Operation Analytics is Enabled.", e);
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
//        }
//        String eventType = eventBeanWrapper.getType();
//        if (!LOCATION_EVENT_TYPE.equals(eventType)) {
//            String msg = "Dropping Android " + eventType + " Event.Only Location Event Type is supported.";
//            log.warn(msg);
//            return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
//        }
//        Message message = new Message();
//        Object[] metaData = {eventBeanWrapper.getDeviceIdentifier(), device.getEnrolmentInfo().getOwner(),
//                AndroidConstants.DEVICE_TYPE_ANDROID};
//        String eventPayload = eventBeanWrapper.getPayload();
//        JsonObject jsonObject = gson.fromJson(eventPayload, JsonObject.class);
//        Object[] payload = {
//                jsonObject.get(TIME_STAMP).getAsLong(),
//                jsonObject.get(LATITUDE).getAsDouble(),
//                jsonObject.get(LONGITUDE).getAsDouble()
//        };
//        try {
//            if (AndroidAPIUtils.getEventPublisherService().publishEvent(
//                    EVENT_STREAM_DEFINITION, "1.0.0", metaData, new Object[0], payload)) {
//                message.setResponseCode("Event is published successfully.");
//                return Response.status(Response.Status.CREATED).entity(message).build();
//            } else {
//                log.warn("Error occurred while trying to publish the event. This could be due to unavailability " +
//                        "of the publishing service. Please make sure that analytics server is running and accessible " +
//                        "by this server");
//                throw new UnexpectedServerErrorException(
//                        new ErrorResponse.ErrorResponseBuilder().setCode(503l).setMessage("Error occurred due to " +
//                                "unavailability of the publishing service.").build());
//            }
//        } catch (DataPublisherConfigurationException e) {
            String msg = "Error occurred while getting the Data publisher Service instance.";
//            log.error(msg, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(msg).build());
//        }
    }

    @GET
    @Override
    public Response retrieveAlerts(@QueryParam("id")
                                       @Size(min = 2, max = 45)
                                       String deviceId,
                                   @QueryParam("from") long from,
                                   @QueryParam("to") long to,
                                       @Size(min = 2, max = 45)
                                   @QueryParam("type") String type,
                                   @HeaderParam("If-Modified-Since") String ifModifiedSince) {

//        if (from != 0l && to != 0l && deviceId != null) {
//            return retrieveAlertFromDate(deviceId, from, to);
//        } else if (deviceId != null && type != null) {
//            return retrieveAlertByType(deviceId, type);
//        } else if (deviceId != null) {
//            return retrieveAlert(deviceId);
//        } else {
            throw new BadRequestException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(400l).setMessage("Request must contain " +
                            "the device identifier. Optionally, both from and to value should be present to get " +
                            "alerts between times.").build());
//        }
    }

//    private Response retrieveAlert(String deviceId) {
//        if (log.isDebugEnabled()) {
//            log.debug("Retrieving events for given device Identifier.");
//        }
//        String query = "deviceIdentifier:" + deviceId;
//        List<DeviceState> deviceStates;
//        try {
//            deviceStates = AndroidDeviceUtils.getAllEventsForDevice(EVENT_STREAM_DEFINITION, query);
//            if (deviceStates == null) {
//                throw new NotFoundException(
//                        new ErrorResponse.ErrorResponseBuilder().setCode(404l).setMessage("No any alerts are " +
//                                "published for Device: " + deviceId + ".").build());
//            } else {
//                return Response.status(Response.Status.OK).entity(deviceStates).build();
//            }
//        } catch (AnalyticsException e) {
//            String msg = "Error occurred while getting published events for specific device: " + deviceId + ".";
//            log.error(msg, e);
//            throw new UnexpectedServerErrorException(
//                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(msg).build());
//        }
//    }
//
//    private Response retrieveAlertFromDate(String deviceId, long from, long to) {
//        String fromDate = String.valueOf(from);
//        String toDate = String.valueOf(to);
//        if (log.isDebugEnabled()) {
//            log.debug("Retrieving events for given device Identifier and time period.");
//        }
//
//        String query = "deviceIdentifier:" + deviceId + " AND _timestamp: [" + fromDate + " TO " + toDate + "]";
//        List<DeviceState> deviceStates;
//        try {
//            deviceStates = AndroidDeviceUtils.getAllEventsForDevice(EVENT_STREAM_DEFINITION, query);
//            if (deviceStates == null) {
//                throw new NotFoundException(
//                        new ErrorResponse.ErrorResponseBuilder().setCode(404l).setMessage("No any alerts are " +
//                                "published on given date for given Device: " + deviceId + ".").build());
//
//            } else {
//                return Response.status(Response.Status.OK).entity(deviceStates).build();
//            }
//        } catch (AnalyticsException e) {
//            String msg = "Error occurred while getting published events for specific " +
//                    "Device: " + deviceId + " on given Date.";
//            log.error(msg, e);
//            throw new UnexpectedServerErrorException(
//                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(msg).build());
//        }
//    }
//
//    private Response retrieveAlertByType(String deviceId, String type) {
//        if (log.isDebugEnabled()) {
//            log.debug("Retrieving events for given device identifier and type.");
//        }
//        String query = "deviceIdentifier:" + deviceId + " AND type:" + type;
//        List<DeviceState> deviceStates;
//        try {
//            deviceStates = AndroidDeviceUtils.getAllEventsForDevice(EVENT_STREAM_DEFINITION, query);
//            if (deviceStates == null) {
//                throw new NotFoundException(
//                        new ErrorResponse.ErrorResponseBuilder().setCode(404l).setMessage("No any alerts are " +
//                                "published for given Device: '" + deviceId + "' and given specific Type.").build());
//
//            } else {
//                return Response.status(Response.Status.OK).entity(deviceStates).build();
//            }
//        } catch (AnalyticsException e) {
//            String msg = "Error occurred while getting published events for specific " +
//                    "Device: " + deviceId + "and given specific Type.";
//            log.error(msg, e);
//            throw new UnexpectedServerErrorException(
//                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(msg).build());
//        }
//    }

}
