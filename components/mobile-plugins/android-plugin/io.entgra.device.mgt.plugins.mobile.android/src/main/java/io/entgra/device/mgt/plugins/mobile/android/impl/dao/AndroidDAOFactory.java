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

package io.entgra.device.mgt.plugins.mobile.android.impl.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import io.entgra.device.mgt.core.device.mgt.common.DeviceManagementConstants;
import io.entgra.device.mgt.plugins.mobile.android.impl.dao.impl.AndroidDeviceDAOImpl;
import io.entgra.device.mgt.plugins.mobile.android.impl.dao.impl.AndroidFeatureDAOImpl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class AndroidDAOFactory extends AbstractMobileDeviceManagementDAOFactory {

    private static final Log log = LogFactory.getLog(AndroidDAOFactory.class);
    protected static DataSource dataSource;
    private static ThreadLocal<Connection> currentConnection = new ThreadLocal<>();

    public AndroidDAOFactory() {
        this.dataSource = getDataSourceMap().get(DeviceManagementConstants.MobileDeviceTypes.MOBILE_DEVICE_TYPE_ANDROID);
    }

    @Override
    public MobileDeviceDAO getMobileDeviceDAO() {
        return new AndroidDeviceDAOImpl();
    }

    public MobileFeatureDAO getMobileFeatureDAO() {
        return new AndroidFeatureDAOImpl();
    }

    public static void beginTransaction() throws MobileDeviceManagementDAOException {
        try {
            Connection conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            currentConnection.set(conn);
        } catch (SQLException e) {
            throw new MobileDeviceManagementDAOException("Error occurred while retrieving datasource connection", e);
        }
    }

    public static Connection getConnection() throws MobileDeviceManagementDAOException {
        if (currentConnection.get() == null) {
            try {
                currentConnection.set(dataSource.getConnection());
            } catch (SQLException e) {
                throw new MobileDeviceManagementDAOException("Error occurred while retrieving data source connection",
                        e);
            }
        }
        return currentConnection.get();
    }

    public static void commitTransaction() throws MobileDeviceManagementDAOException {
        try {
            Connection conn = currentConnection.get();
            if (conn != null) {
                conn.commit();
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Datasource connection associated with the current thread is null, hence commit " +
                            "has not been attempted");
                }
            }
        } catch (SQLException e) {
            throw new MobileDeviceManagementDAOException("Error occurred while committing the transaction", e);
        }
    }

    public static void closeConnection() throws MobileDeviceManagementDAOException {
        Connection conn = currentConnection.get();
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            log.error("Error occurred while close the connection");
        }
        currentConnection.remove();
    }

    public static void rollbackTransaction() throws MobileDeviceManagementDAOException {
        try {
            Connection conn = currentConnection.get();
            if (conn != null) {
                conn.rollback();
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Datasource connection associated with the current thread is null, hence rollback " +
                            "has not been attempted");
                }
            }
        } catch (SQLException e) {
            throw new MobileDeviceManagementDAOException("Error occurred while rollback the transaction", e);
        }
    }

}