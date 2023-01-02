/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect.postgresql;

import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.dbhub.server.domain.support.dialect.MetaSchema;
import com.alibaba.dbhub.server.domain.support.dialect.postgresql.mapper.PostgresqlMetaSchemaMapper;
import com.alibaba.dbhub.server.domain.support.dialect.postgresql.model.PostgresqlColumn;
import com.alibaba.dbhub.server.domain.support.dialect.postgresql.model.PostgresqlTable;
import com.alibaba.dbhub.server.domain.support.dialect.postgresql.model.PostgresqlTableIndex;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
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
public class PostgresqlMetaSchemaSupport implements MetaSchema<PostgresqlTable> {

    @Override
    public DbTypeEnum supportDbType() {
        return POSTGRESQL;
    }

    @Override
    public List<String> showDatabases() {
        return getMapper().showDatabases();
    }

    @Override
    public String showCreateTable(String databaseName, String schemaName, String tableName) {
        schemaName = ObjectUtils.isEmpty(schemaName) ? "public" : schemaName;
        return getMapper().showCreateTable(schemaName, tableName);
    }

    @Override
    public void dropTable(String databaseName, String schemaName, String tableName) {
        schemaName = ObjectUtils.isEmpty(schemaName) ? "public" : schemaName;
        getMapper().dropTable(tableName);
    }

    @Override
    public int queryTableCount(String databaseName, String schemaName) {
        schemaName = ObjectUtils.isEmpty(schemaName) ? "public" : schemaName;
        return getMapper().selectTableCount(schemaName).intValue();
    }

    @Override
    public List<PostgresqlTable> queryTableList(String databaseName, String schemaName,
        int pageNo,
        int pageSize) {
        schemaName = ObjectUtils.isEmpty(schemaName) ? "public" : schemaName;

        List<String> names = getMapper().selectTables(schemaName, pageSize,
            pageNo <= 1 ? 0 : (pageNo - 1) * pageSize);
        if (CollectionUtils.isEmpty(names)) {
            return Lists.newArrayList();
        } else {
            return names.stream().map(name -> PostgresqlTable.builder().name(name).build()).collect(
                Collectors.toList());
        }
    }

    @Override
    public PostgresqlTable queryTable(String databaseName, String schemaName, String tableName) {
        //List<PostgresqlColumn> columns = getMapper().selectColumns(schemaName, tableName,
        //    schemaName + "." + tableName);
        return null;
    }

    @Override
    public List<PostgresqlTableIndex> queryIndexList(String databaseName, String schemaName,
        List<String> tableNames) {
        schemaName = ObjectUtils.isEmpty(schemaName) ? "public" : schemaName;
        return getMapper().selectTableIndexes(schemaName, tableNames);
    }

    @Override
    public List<PostgresqlColumn> queryColumnList(String databaseName, String schemaName,
        List<String> tableNames) {
        schemaName = ObjectUtils.isEmpty(schemaName) ? "public" : schemaName;
        return getMapper().selectColumns(schemaName, tableNames);
    }

    private PostgresqlMetaSchemaMapper getMapper() {
        return DbhubDataSource.getInstance().getMapper(PostgresqlMetaSchemaMapper.class);
    }

}