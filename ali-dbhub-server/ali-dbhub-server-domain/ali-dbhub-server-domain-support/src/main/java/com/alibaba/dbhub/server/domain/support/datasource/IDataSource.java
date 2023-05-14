/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.datasource;

import java.io.Serial;
import java.sql.SQLException;

import com.alibaba.dbhub.server.domain.support.enums.DriverTypeEnum;
import com.alibaba.dbhub.server.domain.support.model.SSHInfo;
import com.alibaba.dbhub.server.domain.support.sql.ConnectInfo;
import com.alibaba.druid.pool.DruidDataSource;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * @author jipengfei
 * @version : DbhubDataSource.java
 */
public class IDataSource extends DruidDataSource {
    @Serial
    private static final long serialVersionUID = -232274227856574115L;

    public ConnectInfo getConnectInfo() {
        return connectInfo;
    }

    private ConnectInfo connectInfo;

    public IDataSource(ConnectInfo connectInfo, DriverTypeEnum driverTypeEnum, ClassLoader classLoader) {
        this.connectInfo = connectInfo;

    }

    @Override
    public void init() throws SQLException {
        if (inited) {
            return;
        }
        connectSession();
        super.init();
        inited = true;
    }

    private void connectSession() {
        SSHInfo ssh = connectInfo.getSsh();
        if (ssh != null && ssh.isUse()) {
            try {
                JSch jSch = new JSch();
                Session session = jSch.getSession(ssh.getUserName(), ssh.getHostName(),
                    Integer.parseInt(ssh.getPort()));
                if ("password".equals(ssh.getAuthenticationType())) {
                    session.setPassword(ssh.getPassword());
                } else {
                    jSch.addIdentity(ssh.getKeyFile(), ssh.getPassphrase());
                }
                session.setConfig("StrictHostKeyChecking", "no");
                session.connect();
                int port = session.setPortForwardingL(Integer.parseInt(ssh.getLocalPort()), connectInfo.getHost(),
                    connectInfo.getPort());
                System.out.println("getServerVersion :" + session.getServerVersion() + " setPortForwardingL:" + port);
                setUrl(connectInfo.getUrl().replace(connectInfo.getHost() + ":" + connectInfo.getPort(),
                    ssh.getHostName() + ":" + ssh.getLocalPort()));
            } catch (JSchException e) {
                throw new RuntimeException(e);
            }
        }
    }
}