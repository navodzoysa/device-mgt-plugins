/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * you may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.entgra.device.mgt.plugins.output.adapter.websocket.service;

import io.entgra.device.mgt.plugins.output.adapter.websocket.authentication.Authenticator;
import io.entgra.device.mgt.plugins.output.adapter.websocket.authorization.Authorizer;

/**
 * This returns the configured authenticator and authorizer for websocket.
 */
public class WebsocketValidationServiceImpl implements WebsocketValidationService{
    private Authenticator authenticator;
    private Authorizer authorizer;

    @Override
    public Authenticator getAuthenticator() {
        return authenticator;
    }

    @Override
    public Authorizer getAuthorizer() {
        return authorizer;
    }

    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    public void setAuthorizer(Authorizer authorizer) {
        this.authorizer = authorizer;
    }
}