///**
// * Alipay.com Inc.
// * Copyright (c) 2004-2022 All Rights Reserved.
// */
//package com.alibaba.dbhub.server.test.domain.data.service.dialect;
//
//import java.util.Date;
//
//import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
//
//import org.springframework.stereotype.Component;
//
///**
// * @author jipengfei
// * @version : PgDialectProperties.java, v 0.1 2022年12月13日 21:48 jipengfei Exp $
// */
//@Component
//public class PgDialectProperties implements DialectProperties {
//
//    @Override
//    public DbTypeEnum getDbType() {
//        return DbTypeEnum.POSTGRESQL;
//    }
//
//    @Override
//    public String getUrl() {
//        return "jdbc:postgresql://localhost:5432/test";
//    }
//
//    @Override
//    public String getErrorUrl() {
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return "ali_dbhub_test";
//    }
//
//    @Override
//    public String getPassword() {
//        return "ali_dbhub";
//    }
//
//    @Override
//    public String getDatabaseName() {
//        return null;
//    }
//
//    @Override
//    public String getCrateTableSql(String tableName) {
//        return null;
//    }
//
//    @Override
//    public String getDropTableSql(String tableName) {
//        return null;
//    }
//
//    @Override
//    public String getInsertSql(String tableName, Date date, Long number, String string) {
//        return null;
//    }
//
//    @Override
//    public String getSelectSqlById(String tableName, Long id) {
//        return null;
//    }
//
//    @Override
//    public String getTableNotFoundSqlById(String tableName) {
//        return null;
//    }
//
//    @Override
//    public String toCase(String string) {
//        return null;
//    }
//}