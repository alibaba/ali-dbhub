/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.alibaba.dbhub.server.domain.support.datasource.DataSourceManger;
import com.alibaba.dbhub.server.domain.support.dialect.MetaSchema;

import org.apache.commons.lang3.StringUtils;

/**
 * @author jipengfei
 * @version : DbhubContext.java
 */
public class DbhubContext {

    private static final ThreadLocal<ConnectInfo> CONNECT_INFO_THREAD_LOCAL = new ThreadLocal<>();

    public static List<String> JDBC_JAR_DOWNLOAD_URL_LIST;

    /**
     * 获取当前线程的ContentContext
     *
     * @return
     */
    public static ConnectInfo getConnectInfo() {
        return CONNECT_INFO_THREAD_LOCAL.get();
    }

    public static MetaSchema getMetaSchema() {
        return getConnectInfo().getDbType().metaSchema();
    }

    public static Connection getConnection() {
        return getConnectInfo().getConnection();
    }

    /**
     * 设置context
     *
     * @param info
     */
    public static void putContext(ConnectInfo info) {
        ConnectInfo connectInfo = CONNECT_INFO_THREAD_LOCAL.get();
        CONNECT_INFO_THREAD_LOCAL.set(info);
        if (connectInfo == null) {
            try {
                Connection connection = DataSourceManger.getDataSource(info).getConnection();
                info.setConnection(connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (StringUtils.isNotBlank(info.getDatabaseName())) {
                SQLExecutor.getInstance().connectDatabase(info.getDatabaseName());
            }
        }
    }

    /**
     * 设置context
     */
    public static void removeContext() {
        ConnectInfo connectInfo = CONNECT_INFO_THREAD_LOCAL.get();
        if (connectInfo != null) {
            Connection connection = connectInfo.getConnection();
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            CONNECT_INFO_THREAD_LOCAL.remove();
        }
    }

}