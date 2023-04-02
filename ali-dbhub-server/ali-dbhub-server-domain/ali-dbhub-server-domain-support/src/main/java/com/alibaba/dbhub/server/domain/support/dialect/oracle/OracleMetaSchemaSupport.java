/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect.oracle;

import com.alibaba.dbhub.server.domain.support.dialect.BaseMetaSchemaSupport;
import com.alibaba.dbhub.server.domain.support.dialect.MetaSchema;
import com.alibaba.dbhub.server.domain.support.dialect.oracle.mapper.OracleMetaSchemaMapper;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.sql.DbhubDataSource;

/**
 * @author jipengfei
 * @version : OracleMetaSchemaSupport.java
 */
public class OracleMetaSchemaSupport extends BaseMetaSchemaSupport implements MetaSchema {

    @Override
    public OracleMetaSchemaMapper getMapper() {
        return DbhubDataSource.getInstance().getMapper(OracleMetaSchemaMapper.class);
    }

    @Override
    public DbTypeEnum dbType() {
        return DbTypeEnum.ORACLE;
    }


}