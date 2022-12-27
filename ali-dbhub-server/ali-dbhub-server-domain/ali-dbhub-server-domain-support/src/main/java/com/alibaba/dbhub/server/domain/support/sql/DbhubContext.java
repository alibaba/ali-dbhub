/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.sql;

import java.util.Objects;

import com.alibaba.dbhub.server.domain.support.dialect.MetaSchema;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;

import org.springframework.util.ObjectUtils;

/**
 * @author jipengfei
 * @version : DbhubContext.java
 */
public class DbhubContext {

    private static final ThreadLocal<ConnectInfo> CONNECT_INFO_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 获取当前线程的ContentContext
     *
     * @return
     */
    public static ConnectInfo getConnectInfo() {
        return CONNECT_INFO_THREAD_LOCAL.get();
    }

    public static MetaSchema getMetaSchema(){
        return getConnectInfo().getDbType().metaSchema();
    }

    /**
     * 设置context
     *
     * @param info
     */
    public static void putContext(ConnectInfo info) {
        ConnectInfo connectInfo = CONNECT_INFO_THREAD_LOCAL.get();
        if (connectInfo == null) {
            CONNECT_INFO_THREAD_LOCAL.set(info);
        }
    }

    /**
     * 设置context
     */
    public static void removeContext() {
        CONNECT_INFO_THREAD_LOCAL.remove();
    }


    public static class ConnectInfo {

        /**
         * 数据连接ID
         */
        private Long dataSourceId;

        /**
         * database
         */
        private String databaseName;

        /**
         * 控制台ID
         */
        private Long consoleId;

        /**
         * 数据库URL
         */
        private String url;

        /**
         * 用户名
         */
        private String user;

        /**
         * 密码
         */
        private String password;

        /**
         * console独立占有连接
         */
        private Boolean consoleOwn = Boolean.FALSE;

        /**
         * 数据库类型
         */
        private DbTypeEnum dbType;

        private Integer port;

        /**
         *
         */
        private String urlWithOutDatabase;

        public void setDatabase(String database) {
            this.databaseName = database;
            if (!ObjectUtils.isEmpty(this.urlWithOutDatabase) && !ObjectUtils.isEmpty(this.databaseName)) {
                this.url = this.urlWithOutDatabase + "/" + database;
            }
        }

        public void setUrl(String url) {
            this.url = url;
            if (this.dbType != DbTypeEnum.MYSQL && this.dbType != DbTypeEnum.POSTGRESQL) {
                return;
            }
            if (!ObjectUtils.isEmpty(url)) {
                //jdbc:postgresql://localhost:5432/postgres
                String[] array = getUrl().split(":");
                if (array.length == 4) {
                    String str = array[3];
                    boolean isDigit = true;
                    StringBuffer sb = new StringBuffer();
                    StringBuffer sb1 = new StringBuffer();
                    for (int i = 0; i < str.length(); i++) {
                        char c = str.charAt(i);
                        if (isDigit == true) {
                            if (!Character.isDigit(c)) {
                                isDigit = false;
                            } else {
                                sb1.append(c);
                            }
                        } else {
                            sb.append(c);
                        }
                    }
                    String s = sb.toString();
                    if (!ObjectUtils.isEmpty(s)) {
                        this.databaseName = s;
                    }
                    this.port = Integer.parseInt(sb1.toString());
                    this.urlWithOutDatabase = array[0] + ":" + array[1] + ":" + array[2] + ":" + port;
                }
            }
        }

        /**
         * Getter method for property <tt>dataSourceId</tt>.
         *
         * @return property value of dataSourceId
         */
        public Long getDataSourceId() {
            return dataSourceId;
        }

        /**
         * Setter method for property <tt>dataSourceId</tt>.
         *
         * @param dataSourceId value to be assigned to property dataSourceId
         */
        public void setDataSourceId(Long dataSourceId) {
            this.dataSourceId = dataSourceId;
        }

        /**
         * Getter method for property <tt>databaseName</tt>.
         *
         * @return property value of databaseName
         */
        public String getDatabaseName() {
            return databaseName;
        }

        /**
         * Setter method for property <tt>databaseName</tt>.
         *
         * @param databaseName value to be assigned to property databaseName
         */
        public void setDatabaseName(String databaseName) {
            this.databaseName = databaseName;
        }

        /**
         * Getter method for property <tt>consoleId</tt>.
         *
         * @return property value of consoleId
         */
        public Long getConsoleId() {
            return consoleId;
        }

        /**
         * Setter method for property <tt>consoleId</tt>.
         *
         * @param consoleId value to be assigned to property consoleId
         */
        public void setConsoleId(Long consoleId) {
            this.consoleId = consoleId;
        }

        /**
         * Getter method for property <tt>url</tt>.
         *
         * @return property value of url
         */
        public String getUrl() {
            return url;
        }

        /**
         * Getter method for property <tt>user</tt>.
         *
         * @return property value of user
         */
        public String getUser() {
            return user;
        }

        /**
         * Setter method for property <tt>user</tt>.
         *
         * @param user value to be assigned to property user
         */
        public void setUser(String user) {
            this.user = user;
        }

        /**
         * Getter method for property <tt>password</tt>.
         *
         * @return property value of password
         */
        public String getPassword() {
            return password;
        }

        /**
         * Setter method for property <tt>password</tt>.
         *
         * @param password value to be assigned to property password
         */
        public void setPassword(String password) {
            this.password = password;
        }

        /**
         * Getter method for property <tt>consoleOwn</tt>.
         *
         * @return property value of consoleOwn
         */
        public Boolean getConsoleOwn() {
            return consoleOwn;
        }

        /**
         * Setter method for property <tt>consoleOwn</tt>.
         *
         * @param consoleOwn value to be assigned to property consoleOwn
         */
        public void setConsoleOwn(Boolean consoleOwn) {
            this.consoleOwn = consoleOwn;
        }

        /**
         * Getter method for property <tt>dbType</tt>.
         *
         * @return property value of dbType
         */
        public DbTypeEnum getDbType() {
            return dbType;
        }

        /**
         * Setter method for property <tt>dbType</tt>.
         *
         * @param dbType value to be assigned to property dbType
         */
        public void setDbType(DbTypeEnum dbType) {
            this.dbType = dbType;
        }

        /**
         * Getter method for property <tt>port</tt>.
         *
         * @return property value of port
         */
        public Integer getPort() {
            return port;
        }

        /**
         * Setter method for property <tt>port</tt>.
         *
         * @param port value to be assigned to property port
         */
        public void setPort(Integer port) {
            this.port = port;
        }

        /**
         * Getter method for property <tt>urlWithOutDatabase</tt>.
         *
         * @return property value of urlWithOutDatabase
         */
        public String getUrlWithOutDatabase() {
            return urlWithOutDatabase;
        }

        /**
         * Setter method for property <tt>urlWithOutDatabase</tt>.
         *
         * @param urlWithOutDatabase value to be assigned to property urlWithOutDatabase
         */
        public void setUrlWithOutDatabase(String urlWithOutDatabase) {
            this.urlWithOutDatabase = urlWithOutDatabase;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {return true;}
            if (!(o instanceof ConnectInfo)) {return false;}
            ConnectInfo that = (ConnectInfo)o;
            return Objects.equals(dataSourceId, that.dataSourceId) && Objects.equals(databaseName,
                that.databaseName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(dataSourceId, databaseName);
        }
    }
}