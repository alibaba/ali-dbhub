/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect.mysql;

import javax.validation.constraints.NotEmpty;

import com.alibaba.dbhub.server.domain.support.dialect.BaseMetaSchemaSupport;
import com.alibaba.dbhub.server.domain.support.dialect.MetaSchema;
import com.alibaba.dbhub.server.domain.support.dialect.mysql.mapper.MysqlMetaSchemaMapper;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.sql.DbhubDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jipengfei
 * @version : MysqlMetaSchemaSupport.java, v 0.1 2022年12月14日 22:44 jipengfei Exp $
 */
@Slf4j
public class MysqlMetaSchemaSupport  extends BaseMetaSchemaSupport implements MetaSchema {


    @Override
    public DbTypeEnum dbType() {
        return DbTypeEnum.MYSQL;
    }

    @Override
    public String tableDDL(@NotEmpty String databaseName, String schemaName, @NotEmpty String tableName){
        return getMapper().showCreateTable0(databaseName,tableName).getSql();
    }



    @Override
    public MysqlMetaSchemaMapper getMapper() {
        return DbhubDataSource.getInstance().getMapper(MysqlMetaSchemaMapper.class);
    }
}