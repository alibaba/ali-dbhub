/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect.sqlite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.dbhub.server.domain.support.dialect.BaseMetaSchemaSupport;
import com.alibaba.dbhub.server.domain.support.dialect.MetaSchema;
import com.alibaba.dbhub.server.domain.support.dialect.sqlite.mapper.SQLiteMetaSchemaMapper;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.model.Table;
import com.alibaba.dbhub.server.domain.support.model.TableColumn;
import com.alibaba.dbhub.server.domain.support.model.TableIndex;
import com.alibaba.dbhub.server.domain.support.model.TableIndexColumn;
import com.alibaba.dbhub.server.domain.support.sql.DbhubDataSource;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jipengfei
 * @version : SqlserverMetaSchemaSupport.java
 */
@Slf4j
public class SQLiteMetaSchemaSupport extends BaseMetaSchemaSupport implements MetaSchema {

    @Override
    public DbTypeEnum dbType() {
        return DbTypeEnum.SQLITE;
    }

    @Override
    public List<String> databases() {
        return Lists.newArrayList("main");
    }

    @Override
    public List<String> schemas(String databaseName) {
        return Lists.newArrayList();
    }

    @Override
    public String tableDDL(String databaseName, String schemaName, String tableName) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = DbhubDataSource.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                "SELECT sql FROM sqlite_master WHERE type='table' AND name='" + tableName + "'");
            while (resultSet.next()) {
                return resultSet.getString("sql");
            }
        } catch (Exception e) {
            log.error("get tables error", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) {
                log.error("close resultSet error", e);
            }
        }
        return null;
    }

    @Override
    public void dropTable(String databaseName, String schemaName, String tableName) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = DbhubDataSource.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("drop table " + tableName);
        } catch (Exception e) {
            log.error("get tables error", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) {
                log.error("close resultSet error", e);
            }
        }
    }

    @Override
    public List<Table> tables(String databaseName, String schemaName, String tableName) {
        List<Table> tables = Lists.newArrayList();
        try {
            Connection connection = DbhubDataSource.getInstance().getConnection();
            ResultSet resultSet = connection.getMetaData().getTables(null, null, null, null);
            while (resultSet.next()) {
                String name = resultSet.getString("TABLE_NAME");
                String remarks = resultSet.getString("REMARKS");
                Table table = new Table();
                table.setName(name);
                table.setComment(remarks);
                tables.add(table);
            }
        } catch (Exception e) {
            log.error("get tables error", e);
        }
        return tables;
    }

    @Override
    public List<TableColumn> columns(String databaseName, String schemaName, String tableName) {
        List<TableColumn> tableColumns = Lists.newArrayList();
        try {
            Connection connection = DbhubDataSource.getInstance().getConnection();
            ResultSet columns = connection.getMetaData().getColumns(null, null, tableName, null);
            List<String> columnDefinitions = new ArrayList<>();
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String columnType = columns.getString("TYPE_NAME");
                int columnSize = columns.getInt("COLUMN_SIZE");
                String remarks = columns.getString("REMARKS");
                String defaultValue = columns.getString("COLUMN_DEF");
                TableColumn tableColumn = new TableColumn();
                tableColumn.setName(columnName);
                tableColumn.setComment(remarks);
                tableColumn.setDefaultValue(defaultValue);
                tableColumn.setDataType(columnType);
                tableColumns.add(tableColumn);
            }
        } catch (Exception e) {
            log.error("get columns error", e);
        }
        return tableColumns;
    }

    @Override
    public List<TableIndex> indexes(String databaseName, String schemaName, String tableName) {
        List<TableIndex> tableIndices = Lists.newArrayList();
        try {
            Connection connection = DbhubDataSource.getInstance().getConnection();
            ResultSet indexes = connection.getMetaData().getIndexInfo(null, null, tableName, false, false);
            Map<String, List<String>> indexMap = new HashMap<>();
            while (indexes.next()) {
                String indexName = indexes.getString("INDEX_NAME");
                String columnName = indexes.getString("COLUMN_NAME");
                if (indexName != null) {
                    if (!indexMap.containsKey(indexName)) {
                        indexMap.put(indexName, new ArrayList<>());
                    }
                    indexMap.get(indexName).add(columnName);
                }
            }
            for (Map.Entry<String, List<String>> entry : indexMap.entrySet()) {
                TableIndex tableIndex = new TableIndex();
                tableIndex.setName(entry.getKey());
                tableIndex.setColumnList(
                    entry.getValue().stream().map(str -> TableIndexColumn.builder().columnName(str).build())
                        .collect(java.util.stream.Collectors.toList()));
                tableIndices.add(tableIndex);
            }

        } catch (Exception e) {
            log.error("get columns error", e);
        }
        return tableIndices;
    }

    @Override
    public SQLiteMetaSchemaMapper getMapper() {
        return DbhubDataSource.getInstance().getMapper(SQLiteMetaSchemaMapper.class);
    }
}