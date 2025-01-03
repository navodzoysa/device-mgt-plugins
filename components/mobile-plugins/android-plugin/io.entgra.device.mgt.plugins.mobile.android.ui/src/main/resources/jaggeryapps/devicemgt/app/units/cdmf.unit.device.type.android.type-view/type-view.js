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

function onRequest(context) {
    var viewModel = {};
    var devicemgtProps = require("/app/modules/conf-reader/main.js")["conf"];
    var isCloud = devicemgtProps["isCloud"];
    viewModel["isVirtual"] = request.getParameter("type") == 'virtual';
    viewModel["isCloud"] = isCloud;
    viewModel["hostName"] = devicemgtProps["httpsURL"];
    if (isCloud) {
        viewModel["enrollmentURL"] = "https://play.google.com/store/apps/details?id=org.wso2.iot.agent";
    } else {
        viewModel["enrollmentURL"] = devicemgtProps["androidAgentDownloadURL"];
    }
    return viewModel;
}