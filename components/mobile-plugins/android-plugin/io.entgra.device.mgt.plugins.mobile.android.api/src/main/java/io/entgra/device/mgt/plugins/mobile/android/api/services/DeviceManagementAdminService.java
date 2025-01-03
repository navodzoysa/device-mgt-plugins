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
package io.entgra.device.mgt.plugins.mobile.android.api.services;

import io.entgra.device.mgt.plugins.mobile.android.api.bean.wrapper.*;
import io.entgra.device.mgt.plugins.mobile.android.api.util.AndroidConstants;
import io.entgra.device.mgt.plugins.mobile.android.api.bean.wrapper.*;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Info;
import io.swagger.annotations.ExtensionProperty;
import io.swagger.annotations.Extension;
import io.swagger.annotations.Tag;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import io.entgra.device.mgt.core.apimgt.annotations.Scope;
import io.entgra.device.mgt.core.apimgt.annotations.Scopes;
import io.entgra.device.mgt.core.device.mgt.common.operation.mgt.Activity;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@SwaggerDefinition(
        info = @Info(
                version = "1.0.0",
                title = "",
                extensions = {
                        @Extension(properties = {
                                @ExtensionProperty(name = "name",
                                        value = "Android Device Management Administrative Service"),
                                @ExtensionProperty(name = "context",
                                        value = "/api/device-mgt/android/v1.0/admin/devices"),
                        })
                }
        ),
        tags = {
                @Tag(name = "android,device_management", description = "")
        }
)
@Path("/admin/devices")
@Api(value = "Android Device Management Administrative Service", description = "Device management related admin APIs.")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Scopes(
        scopes = {
                @Scope(
                        name = "Lock Device",
                        description = "Hard lock own device",
                        key = "and:ops:lock-devices",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/lock"}
                ),
                @Scope(
                        name = "Unlock Device",
                        description = "Unlock permanently locked device",
                        key = "and:ops:unlock-devices",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/unlock"}
                ),
                @Scope(
                        name = "Get Location",
                        description = "Request device location coordinates",
                        key = "and:ops:location",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/location"}
                ),
                @Scope(
                        name = "Clear Password",
                        description = "Clear the password on Android devices",
                        key = "and:ops:clear-pwd",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/clear-password"}
                ),
                @Scope(
                        name = "Control Camera",
                        description = "Enabling or Disabling the Camera on Android Devices",
                        key = "and:ops:control-camera",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/camera"}
                ),
                @Scope(
                        name = "Get Info",
                        description = "Requesting device information from Android Devices",
                        key = "and:ops:device-info",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/info"}
                ),
                @Scope(
                        name = "Get Logs",
                        description = "Requesting Logcat Details from Android Devices",
                        key = "and:ops:logcat",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/logcat"}
                ),
                @Scope(
                        name = "Enterprise Wipe",
                        description = "Enterprise Wiping Android Devices",
                        key = "and:ops:enterprise-wipe",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/enterprise-wipe"}
                ),
                @Scope(
                        name = "Factory Reset",
                        description = "Factory Resetting Android Devices",
                        key = "and:ops:wipe",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/wipe"}
                ),
                @Scope(
                        name = "Get Installed Applications",
                        description = "Get list of installed applications",
                        key = "and:ops:app-list",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/applications"}
                ),
                @Scope(
                        name = "Ring Device",
                        description = "Ring Android devices",
                        key = "and:ops:ring",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/ring"}
                ),
                @Scope(
                        name = "Reboot Device",
                        description = "Reboot Android devices",
                        key = "and:ops:reboot",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/reboot"}
                ),
                @Scope(
                        name = "Mute Device",
                        description = "Mute Android devices",
                        key = "and:ops:mute",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/mute"}
                ),
                @Scope(
                        name = "Install Applications",
                        description = "Installing an Application on Android Devices",
                        key = "and:ops:install-app",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/install-app"}
                ),
                @Scope(
                        name = "Update Applications",
                        description = "Updating an Application on Android Devices",
                        key = "and:ops:update-app",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/update-app"}
                ),
                @Scope(
                        name = "Uninstall Applications",
                        description = "Uninstalling an Application on Android Devices",
                        key = "and:ops:uninstall-app",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/uninstall-app"}
                ),
                @Scope(
                        name = "Blacklist Applications",
                        description = "Blacklisting applications on Android Devices",
                        key = "and:ops:blacklist-app",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/blacklist-app"}
                ),
                @Scope(
                        name = "Upgrade Firmware",
                        description = "Upgrading Firmware of Android Devices",
                        key = "and:ops:upgrade-firmware",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/upgrade"}
                ),
                @Scope(
                        name = "Configure VPN",
                        description = "Configure VPN on Android Device",
                        key = "and:ops:configure-vpn",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/vpn"}
                ),
                @Scope(
                        name = "Send Notification",
                        description = "Sending a notification to Android Device",
                        key = "and:ops:send-notif",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/send-notification"}
                ),
                @Scope(
                        name = "Configure Wi-Fi",
                        description = "Configure Wi-Fi on Android Device",
                        key = "and:ops:configure-wifi",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/wifi"}
                ),
                @Scope(
                        name = "Encrypt Storage",
                        description = "Encrypting storage on Android Device",
                        key = "and:ops:encrypt-storage",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/encrypt"}
                ),
                @Scope(
                        name = "Change Password",
                        description = "Changing the lock code of an Android Device",
                        key = "and:ops:change-lock-code",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/change-lock-code"}
                ),
                @Scope(
                        name = "Password Policy",
                        description = "Set password policy of an Android Device",
                        key = "and:ops:pwd-policy",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/password-policy"}
                ),
                @Scope(
                        name = "Add Web clip",
                        description = "Setting a Web Clip on Android Devices",
                        key = "and:ops:set-webclip",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/webclip"}
                ),
                @Scope(
                        name = "File Transfer",
                        description = "Transferring a file to android devices",
                        key = "and:ops:file-transfer",
                        roles = {"Internal/devicemgt-admin"},
                        permissions = {"/device-mgt/devices/owning-device/operations/android/file-transfer"}
                )
        }
)
public interface DeviceManagementAdminService {

    @POST
    @Path("/file-transfer")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Transferring file to the device.",
            notes = "Using this API you have the option to transfer a file from SFTP/FTP server or using an " +
                    "HTTP link to the device or retrieve file from the device to FTP/SFTP server .",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:file-transfer")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "File transferred.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified. \n" +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported.\n"),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while file transfer operation.")
    })
    Response fileTransfer(
            @ApiParam(
                    name = "fileTransfer",
                    value = "Provide the ID of the Android device. Multiple device IDs can be added by using " +
                            "comma separated values.",
                    required = true) FileTransferBeanWrapper fileTransferBeanWrapper);

    @POST
    @Path("/lock-devices")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Adding a Screen Lock on Android devices",
            notes = "Using this API you have the option of hard locking an Android device, where the Administrator " +
                    "permanently locks the device or screen locking an Android device.",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:lock-devices")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Successfully scheduled the device lock operation.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified. \n" +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported.\n"),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while locking the device.")
    })
    Response configureDeviceLock(
            @ApiParam(
                    name = "deviceLock",
                    value = "Provide the ID of the Android device, the message that needs to be sent out when locking the device, " +
                            "and define true as the value if you need to hard lock the device or define false as the value to " +
                            "screen lock the device." +
                            "Multiple device IDs can be added by using comma separated values. ",
                    required = true) DeviceLockBeanWrapper deviceLockBeanWrapper);

    @POST
    @Path("/unlock-devices")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Unlocking Android Devices",
            responseContainer = "List",
            notes = "Unlock devices that were locked permanently using the hard lock operation. Devices that are hard locked can only be unlocked by the EMM administrator.",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:unlock-devices")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Successfully scheduled the device unlock operation.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified.\n" +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported."),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while unlocking the device.")
    })
    Response configureDeviceUnlock(
            @ApiParam(
                    name = "deviceIDs",
                    value = "Provide the ID of the Android device. Multiple device IDs can be added by using comma separated values. ",
                    required = true)
                    List<String> deviceIDs);


    @POST
    @Path("/location")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Requesting Location Coordinates",
            responseContainer = "List",
            notes = "Request location coordinates of Android devices. \n" +
                    "Example: In situations where you have lost your device and need to find out where it is, you can use this REST API to get the location of the device.",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:location")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Get-location operation has successfully been scheduled",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified.\n" +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported."),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while adding a new get-location operation.")})
    Response getDeviceLocation(
            @ApiParam(
                    name = "deviceIDs",
                    value = "Provide the ID of the Android device. Multiple device IDs can be added by using comma separated values. ",
                    required = true)
                    List<String> deviceIDs);

    @POST
    @Path("/clear-password")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Clearing the Password on Android Devices",
            notes = "Clear the password on Android devices",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:clear-pwd")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Successfully scheduled the clear password operation.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified the last time.\n" +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.\n",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported."),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while adding a new clear password operation.")
    })
    Response removePassword(
            @ApiParam(name = "deviceIDs",
                    value = "Provide the ID of the Android device. Multiple device IDs can be added by using comma separated values. ",
                    required = true) List<String> deviceIDs);

    @POST
    @Path("/control-camera")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Enabling or Disabling the Camera on Android Devices",
            notes = "Enable or disable the camera on Android devices.",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:control-camera")
                    })
            })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Successfully scheduled the camera control operation.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified.\n" +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported."),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while adding the new camera control operation.")
    })
    Response configureCamera(
            @ApiParam(
                    name = "cameraControl",
                    value = "Define the properties to enable/disable the camera. " +
                            "Disable the camera on the device by assigning true as the value or enable the " +
                            "camera on the device to function by defining false as the value and the ID of the Android device. " +
                            "Multiple device IDs can be added by using comma separated values. ",
                    required = true)
                    CameraBeanWrapper cameraBeanWrapper);

    @POST
    @Path("/info")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Requesting Information from Android Devices",
            notes = "Using this REST API you are able to request for Android device details. Once this REST API is" +
                    " executed it will be in the Android operation queue until the device calls the server to retrieve " +
                    "the list of operations that needs to be executed on the device",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:device-info")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Successfully scheduled the device info operation.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified.\n" +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported."),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while adding a new device info operation.")
    })
    Response getDeviceInformation(
            @ApiParam(
                    name = "deviceIds",
                    value = "Provide the device ID of the Android device. Multiple device IDs can be added by using comma separated values.",
                    required = true)
                    List<String> deviceIDs);

    @POST
    @Path("/logcat")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Requesting Logcat Details from Android Devices",
            notes = "Using this REST API you are able to request for Android device log details. Once this REST API is" +
                    " executed it will be in the Android operation queue until the device calls the server to retrieve " +
                    "the list of operations that needs to be executed on the device.",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:logcat")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Successfully scheduled the operation to get the logcat details.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified.\n" +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported."),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while adding a new device logcat operation.")
    })
    Response getDeviceLogcat(
            @ApiParam(
                    name = "deviceIds",
                    value = "Provide the ID of the Android device. Multiple device IDs can be added by using comma separated values.",
                    required = true)
                    List<String> deviceIDs);

    @POST
    @Path("/enterprise-wipe")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Enterprise Wiping Android Devices",
            notes = "Enterprise wipe is the process of deleting enterprise related data on a device while keeping the " +
                    "personal data intact. You are able to enterprise wipe Android devices using this REST API.",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:enterprise-wipe")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Successfully scheduled the enterprise wipe operation.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified." +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.\n",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported."),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while adding the enterprise wipe operation.")})
    Response wipeDevice(
            @ApiParam(
                    name = "deviceIDs",
                    value = "Provide the ID of the Android device. Multiple device IDs can be added by using comma separated values. ",
                    required = true)
                    List<String> deviceIDs);

    @POST
    @Path("/wipe")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Factory Resetting an Android Device",
            notes = "Factory rest or erase all the data stored on the Android devices " +
                    "to restore them back to the original system.",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:wipe")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Successfully scheduled the device wipe/factory reset operation.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified.\n" +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported."),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while adding the device wipe operation.")})
    Response wipeData(
            @ApiParam(
                    name = "wipeData",
                    value = "Provide the the passcode, which is the passcode that the Android agent prompts the device owner to set at the time of device enrollment, " +
                            "to enable the factory reset operation, and the ID of the Android device. Multiple device IDs can be added by using comma separated values. ",
                    required = true)
            WipeDataBeanWrapper wipeDataBeanWrapper);

    @POST
    @Path("/applications")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Getting the List of Installed Applications on Android Devices",
            notes = "Using this REST API the server requests for the list of applications that are installed on" +
                    " the Android devices. Once this REST API is executed it will be in the Android operation queue " +
                    "until the device calls the server to retrieve the list of operations that needs to be executed " +
                    "on the device.",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:app-list")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Successfully scheduled the get-applications operation.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified.\n" +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported."),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while adding the new get-applications operation.")
    })
    Response getApplications(
            @ApiParam(
                    name = "deviceIDs",
                    value = "Provide the ID of the Android device. Multiple device IDs can be added using comma separated values.",
                    required = true)
                    List<String> deviceIDs);

    @POST
    @Path("/ring")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Ringing Android Devices",
            notes = "Ring Android devices.",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:ring")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Successfully scheduled the device ring operation.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified.\n" +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported.\n"),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while adding a new device ring operation.")
    })
    Response ringDevice(
            @ApiParam(
                    name = "deviceIDs",
                    value = "Provide the ID of the Android device. Multiple device IDs can be added using comma separated values.",
                    required = true)
                    List<String> deviceIDs);

    @POST
    @Path("/reboot")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Rebooting Android Devices",
            notes = "Reboot or restart your Android devices.",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:reboot")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Successfully scheduled the device reboot operation.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified.\n" +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.\n",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported.\n"),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while adding the new device reboot operation.")
    })
    Response rebootDevice(
            @ApiParam(
                    name = "deviceIDs",
                    value = "Provide the ID of the Android device. Multiple device IDs can be added using comma separated values. ",
                    required = true)
                    List<String> deviceIDs);

    @POST
    @Path("/mute")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Muting Android Devices",
            notes = "Mute or enable a silent profile for Android devices.",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:mute")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Successfully scheduled the device mute operation.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified.\n" +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported."),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while adding a new device mute operation.")
    })
    Response muteDevice(
            @ApiParam(
                    name = "deviceIDs",
                    value = "Provide the ID of the Android device. Multiple device IDs can be added using comma separated values. ",
                    required = true)
                    List<String> deviceIDs);

    @POST
    @Path("/install-application")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Installing an Application on Android Devices",
            notes = "Install an application on an Android device. If the device you are installing the application has the WSO2 system service application installed," +
                    " the application installation will happen in silent mode, else the device user's consent will be required.",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:install-app")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Successfully scheduled the install application operation.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified.\n" +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported.\n"),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while adding a new install-application operation.")
    })
    Response installApplication(
            @ApiParam(
                    name = "applicationInstallation",
                    value = "Properties required to install an application on Android devices. Provide the the package name, type," +
                            " URL and name of the application, the date and time for the scheduled installation, and the ID of the " +
                            "Android device. Multiple device IDs can be added by using comma separated values.",
                    required = true)
                    ApplicationInstallationBeanWrapper applicationInstallationBeanWrapper);

    @POST
    @Path("/update-application")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Updating an Application on Android Devices",
            notes = "Update an application on an Android device. If the device" +
                    " has the WSO2 system service application installed, the application update will happen in silent " +
                    "mode, else the device user's consent is required.",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:update-app")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Successfully scheduled the update-application operation.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified.\n" +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported."),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while adding the new update-application operation.")
    })
    Response updateApplication(
            @ApiParam(
                    name = "applicationUpdate",
                    value = "Properties required to update an application on Android devices. Provide the the package name, type," +
                            "URL and name of the application, the date and time for the scheduled installation, and the ID of the" +
                            "Android device. Multiple device IDs can be added by using comma separated values.",
                    required = true)
                    ApplicationUpdateBeanWrapper applicationUpdateBeanWrapper);

    @POST
    @Path("/uninstall-application")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Uninstalling an Application from Android Devices",
            notes = "Uninstall an application from Android devices.",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:uninstall-app")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Successfully scheduled the uninstall-application operation.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified.\n" +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported."),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while adding a new uninstall-application operation.")
    })
    Response uninstallApplication(
            @ApiParam(
                    name = "applicationUninstallation",
                    value = "Properties required to uninstall an application. Provide the the package name, type," +
                            "URL and name of the application, the date and time for the scheduled installation, and the ID of the" +
                            "Android device. Multiple device IDs can be added by using comma separated values.",
                    required = true)
            ApplicationUninstallationBeanWrapper applicationUninstallationBeanWrapper);

    @POST
    @Path("/blacklist-applications")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "BlackListing Applications for Android Devices",
            notes = "Prevents you from using specific applications. For Android operation systems before Lollipop," +
                    " when a blacklisted application is clicked a screen is displayed to prevent you from using the app. For the Lollipop" +
                    " Android operating systems and after, the blacklisted apps will be hidden. Blacklisting can be used on both BYOD and " +
                    "COPE devices. Applications can be blacklisted via the application restriction policy too.",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:blacklist-app")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Successfully scheduled the application blacklist operation.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified.\n" +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported"),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while adding the new blacklist-applications operation.")
    })
    Response blacklistApplications(
            @ApiParam(
                    name = "blacklistApplications",
                    value = "The properties required to blacklist applications. Provide the package name of the application to be blacklisted," +
                            " and the ID of the Android device. Multiple device IDs can be added by using comma separated values. ",
                    required = true)
            @Valid BlacklistApplicationsBeanWrapper blacklistApplicationsBeanWrapper);

    @POST
    @Path("/upgrade-firmware")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Upgrading Firmware of Android Devices",
            notes = "Upgrade the firmware of Android devices.",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:upgrade-firmware")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Successfully scheduled the firmware upgrade operation.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified.\n" +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported."),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while adding a new upgrade firmware operation.")
    })
    Response upgradeFirmware(
            @ApiParam(
                    name = "upgradeFirmware",
                    value = "Properties required to upgrade the firmware. Provide the date and time to schedule the firmware update in the " +
                            "yyyy-MM-dd'T'HH:mm:ss.SSSXXX format, the OTA upgrade server URL in one of the following formats " +
                            "(example: http//abc.com, http://abc.com/ota), " +
                            "and the ID of the Android device. Multiple device IDs can be added by using comma separated values.",
                    required = true)
                    UpgradeFirmwareBeanWrapper upgradeFirmwareBeanWrapper);

    @POST
    @Path("/configure-vpn")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Configuring VPN on Android devices",
            notes = "Configure VPN on Android devices.",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:configure-vpn")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Successfully scheduled the configure VPN operation.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified." +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported."),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while configuring the VPN.")
    })
    Response configureVPN(
            @ApiParam(
                    name = "vpnBean",
                    value = "VPN configuration and DeviceIds",
                    required = true)
            VpnBeanWrapper vpnBeanWrapper);

    @POST
    @Path("/send-notification")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Sending a Notification to Android Devices",
            notes = "Send a notification or message to Android devices.",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:send-notif")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Successfully sent the notification.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified.\n" +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported."),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while adding a new send notification operation.")
    })
    Response sendNotification(
            @ApiParam(
                    name = "notification",
                    value = "The properties required to send a notification. Provide the message you wish to send and the ID of the " +
                            "Android device. Multiple device IDs can be added by using comma separated values.",
                    required = true)
                    NotificationBeanWrapper notificationBeanWrapper);

    @POST
    @Path("/configure-wifi")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Configuring Wi-Fi on Android Devices",
            notes = "Configure Wi-Fi on Android devices.",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:configure-wifi")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Successfully scheduled the configure Wi-Fi operation.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified." +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported."),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while configuring Wi-Fi.")
    })
    Response configureWifi(
            @ApiParam(
                    name = "wifi",
                    value = "The properties required to configure Wi-Fi. Provide the password to connect to the specified Wi-Fi network," +
                            "the ssid or the name of the Wi-Fi network that you wish to configure and the ID of the Android device." +
                            " Multiple device IDs can be added by using comma separated values.",
                    required = true)
            WifiBeanWrapper wifiBeanWrapper);

    @POST
    @Path("/encrypt-storage")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Encrypting Storage on Android Devices",
            notes = "Encrypt the data stored on Android devices.",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:encrypt-storage")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Successfully scheduled the encrypt storage operation.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified." +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.\n",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported."),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while adding a new encrypt storage operation.")
    })
    Response encryptStorage(
            @ApiParam(
                    name = "encryption",
                    value = "Properties required to encrypt the storage. Encrypt the storage on the device by assigning " +
                            "true as the value or do not encrypt the storage on the device by assigning false as the value and " +
                            "provide the ID of the Android device. Multiple device IDs can be added by using comma separated values.",
                    required = true)
            EncryptionBeanWrapper encryptionBeanWrapper);

    @POST
    @Path("/change-lock-code")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Changing the Lock Code on Android Devices",
            notes = "Change the lock code on Android devices.",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:change-lock-code")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Successfully scheduled the change lock code operation.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified.\n" +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported."),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while adding a new change lock code operation.")
    })
    Response changeLockCode(
            @ApiParam(
                    name = "lockCode",
                    value = "The properties to change th lock code. Provide the lock code that will replace the current lock code on Android devices and " +
                            "the ID of the Android device. Multiple device IDs can be added by using comma separated values. " +
                            "If a passcode policy has been set in EMM, the lock code should comply to the passcode policy.\t",
                    required = true)
                    LockCodeBeanWrapper lockCodeBeanWrapper);

    @POST
    @Path("/set-password-policy")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Setting a Password Policy on Android Devices",
            notes = "Set a password policy on Android devices.",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:pwd-policy")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Successfully scheduled the set password policy operation.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified.\n" +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The entity of the request was in a not supported format."),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while adding a new set password policy operation.")
    })
    Response setPasswordPolicy(
            @ApiParam(
                    name = "passwordPolicy",
                    value = "The properties required to set a password policy.",
                    required = true)
                    PasswordPolicyBeanWrapper passwordPolicyBeanWrapper);

    @POST
    @Path("/set-webclip")
    @ApiOperation(
            consumes = MediaType.APPLICATION_JSON,
            httpMethod = "POST",
            value = "Setting a Web Clip on Android Devices",
            notes = "Set a web clip on Android devices. A web clip is used to add a bookmark to a web application.",
            response = Activity.class,
            tags = "Android Device Management Administrative Service",
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = AndroidConstants.SCOPE, value = "and:ops:set-webclip")
                    })
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Created. \n Successfully scheduled the set web clip operation.",
                    response = Activity.class,
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "URL of the activity instance that refers to the scheduled operation."),
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "Content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified.\n" +
                                            "Used by caches, or in conditional requests.")}),
            @ApiResponse(
                    code = 303,
                    message = "See Other. \n The source can be retrieved from the URL specified in the location header.\n",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Location",
                                    description = "The Source URL of the document.")}),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request. \n Invalid request or validation error."),
            @ApiResponse(
                    code = 415,
                    message = "Unsupported media type. \n The format of the requested entity was not supported."),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n " +
                            "Server error occurred while adding adding a the set web clip operation.")
    })
    Response setWebClip(
            @ApiParam(
                    name = "webClip",
                    value = "The properties to set the web clip.",
                    required = true)
                    WebClipBeanWrapper webClipBeanWrapper);


}
