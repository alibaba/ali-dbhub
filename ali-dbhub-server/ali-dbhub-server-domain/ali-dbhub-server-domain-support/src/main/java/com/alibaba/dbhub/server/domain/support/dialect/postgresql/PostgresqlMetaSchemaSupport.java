/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect.postgresql;

import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.dbhub.server.domain.support.dialect.BaseMetaSchemaSupport;
import com.alibaba.dbhub.server.domain.support.dialect.MetaSchema;
import com.alibaba.dbhub.server.domain.support.dialect.postgresql.mapper.PostgresqlMetaSchemaMapper;
import com.alibaba.dbhub.server.domain.support.dialect.postgresql.model.PostgresqlColumn;
import com.alibaba.dbhub.server.domain.support.dialect.postgresql.model.PostgresqlTable;
import com.alibaba.dbhub.server.domain.support.dialect.postgresql.model.PostgresqlTableIndex;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.model.Function;
import com.alibaba.dbhub.server.domain.support.model.Procedure;
import com.alibaba.dbhub.server.domain.support.model.Trigger;
import com.alibaba.dbhub.server.domain.support.sql.DbhubDataSource;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import static com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum.POSTGRESQL;

/**
 * @author jipengfei
 * @version : PostgresqlDataBase.java, v 0.1 2022年12月08日 14:48 jipengfei Exp $
 */
@Slf4j
public class PostgresqlMetaSchemaSupport extends BaseMetaSchemaSupport implements MetaSchema {

    @Override
    public DbTypeEnum dbType() {
        return POSTGRESQL;
    }

    @Override
    public List<String> schemas(String databaseName) {
        return getMapper().schemas(databaseName);
    }


    //@Override
    //public String tableDDL(String databaseName, String schemaName, String tableName) {
    //    schemaName = ObjectUtils.isEmpty(schemaName) ? "public" : schemaName;
    //    return getMapper().showCreateTable(schemaName, tableName);
    //}
    //
    //@Override
    //public void dropTable(String databaseName, String schemaName, String tableName) {
    //    schemaName = ObjectUtils.isEmpty(schemaName) ? "public" : schemaName;
    //    getMapper().dropTable(tableName);
    //}


    //@Override
    //public List<PostgresqlTable> tables(String databaseName, String schemaName) {
    //    schemaName = ObjectUtils.isEmpty(schemaName) ? "public" : schemaName;
    //
    //    List<String> names = getMapper().selectTables(schemaName);
    //    if (CollectionUtils.isEmpty(names)) {
    //        return Lists.newArrayList();
    //    } else {
    //        return names.stream().map(name -> PostgresqlTable.builder().name(name).build()).collect(
    //            Collectors.toList());
    //    }
    //}

    //@Override
    //public List<PostgresqlTableIndex> indexes(String databaseName, String schemaName, String tableName) {
    //    schemaName = ObjectUtils.isEmpty(schemaName) ? "public" : schemaName;
    //    return getMapper().selectTableIndexes(schemaName, tableName);
    //}

    //@Override
    //public List<PostgresqlColumn> columns(String databaseName, String schemaName, String tableName) {
    //    schemaName = ObjectUtils.isEmpty(schemaName) ? "public" : schemaName;
    //    return getMapper().selectColumns(schemaName, tableName);
    //}

    @Override
    public PostgresqlMetaSchemaMapper getMapper() {
        return DbhubDataSource.getInstance().getMapper(PostgresqlMetaSchemaMapper.class);
    }

}