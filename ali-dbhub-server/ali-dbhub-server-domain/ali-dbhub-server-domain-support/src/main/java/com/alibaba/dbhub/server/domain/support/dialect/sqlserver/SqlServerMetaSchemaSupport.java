/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect.sqlserver;

import com.alibaba.dbhub.server.domain.support.dialect.BaseMetaSchemaSupport;
import com.alibaba.dbhub.server.domain.support.dialect.MetaSchema;
import com.alibaba.dbhub.server.domain.support.dialect.sqlserver.mapper.SqlServerMetaSchemaMapper;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.sql.DbhubDataSource;

/**
 * @author jipengfei
 * @version : SqlserverMetaSchemaSupport.java
 */
public class SqlServerMetaSchemaSupport extends BaseMetaSchemaSupport implements MetaSchema {

    @Override
    public DbTypeEnum dbType() {
        return DbTypeEnum.SQLSERVER;
    }

    @Override
    public SqlServerMetaSchemaMapper getMapper() {
        return DbhubDataSource.getInstance().getMapper(SqlServerMetaSchemaMapper.class);
    }
    @Override
    public String tableDDL(String databaseName, String schemaName, String tableName) {
        try {
            getMapper().createDDLFunction(schemaName);
        }catch (Exception e) {
        }
        return getMapper().showCreateTable(databaseName, schemaName, tableName);
    }
}