/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect.sqllite;

import com.alibaba.dbhub.server.domain.support.dialect.BaseMetaSchemaSupport;
import com.alibaba.dbhub.server.domain.support.dialect.MetaSchema;
import com.alibaba.dbhub.server.domain.support.dialect.sqllite.mapper.SQLLiteMetaSchemaMapper;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.sql.DbhubDataSource;

/**
 * @author jipengfei
 * @version : SqlserverMetaSchemaSupport.java
 */
public class SQLLiteMetaSchemaSupport extends BaseMetaSchemaSupport implements MetaSchema {
    @Override
    public DbTypeEnum dbType() {
        return DbTypeEnum.SQLSERVER;
    }


    @Override
    public SQLLiteMetaSchemaMapper getMapper() {
        return DbhubDataSource.getInstance().getMapper(SQLLiteMetaSchemaMapper.class);
    }
}