/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.datasource;

import com.alibaba.dbhub.server.domain.support.enums.DriverTypeEnum;
import com.alibaba.dbhub.server.domain.support.sql.ConnectInfo;

import com.zaxxer.hikari.HikariDataSource;

/**
 * @author jipengfei
 * @version : MyDataSource.java
 */
public class MyDataSource extends HikariDataSource {

    public ConnectInfo getConnectInfo() {
        return connectInfo;
    }

    private ConnectInfo connectInfo;

    public MyDataSource(ConnectInfo connectInfo) {
        super();
        this.connectInfo = connectInfo;
    }
}