/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect.oracle;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.dbhub.server.domain.support.dialect.MetaSchema;
import com.alibaba.dbhub.server.domain.support.dialect.oracle.mapper.OracleMetaSchemaMapper;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.model.Table;
import com.alibaba.dbhub.server.domain.support.model.TableColumn;
import com.alibaba.dbhub.server.domain.support.model.TableIndex;
import com.alibaba.dbhub.server.domain.support.model.TableIndexColumn;
import com.alibaba.dbhub.server.domain.support.model.TableIndexColumnUnion;
import com.alibaba.dbhub.server.domain.support.sql.DbhubDataSource;
import com.alibaba.dbhub.server.tools.common.util.EasyCollectionUtils;

import com.google.common.collect.Lists;

/**
 * @author jipengfei
 * @version : OracleMetaSchemaSupport.java
 */
public class OracleMetaSchemaSupport implements MetaSchema<Table> {

    private OracleMetaSchemaMapper getMapper() {
        return DbhubDataSource.getInstance().getMapper(OracleMetaSchemaMapper.class);
    }

    @Override
    public DbTypeEnum supportDbType() {
        return DbTypeEnum.ORACLE;
    }

    @Override
    public List<String> showDatabases() {
        return getMapper().showDatabases();
    }

    @Override
    public String showCreateTable(String databaseName, String schemaName, String tableName) {
        return getMapper().showCreateTable(schemaName, tableName);
    }

    @Override
    public void dropTable(String databaseName, String schemaName, String tableName) {
        getMapper().dropTable(tableName);
    }

    @Override
    public int queryTableCount(String databaseName, String schemaName) {
        return getMapper().selectTableCount(databaseName).intValue();
    }

    @Override
    public List<Table> queryTableList(String databaseName, String schemaName, int pageNo, int pageSize) {
        return getMapper().selectTables(databaseName, pageNo <= 1 ? 0 : (pageNo - 1) * pageSize, pageNo * pageSize);
    }

    @Override
    public Table queryTable(String databaseName, String schemaName, String tableName) {
        return null;
    }

    @Override
    public List<? extends TableIndex> queryIndexList(String databaseName, String schemaName, List<String> tableNames) {
        List<TableIndex> dataList = Lists.newArrayList();
        List<TableIndexColumnUnion> list = getMapper().selectTableIndexes(databaseName, tableNames);
        Map<String, List<TableIndexColumnUnion>> tableMap = list.stream().collect(
            Collectors.groupingBy(TableIndexColumnUnion::getTableName));
        for (Map.Entry<String, List<TableIndexColumnUnion>> entry : tableMap.entrySet()) {
            String tableName = entry.getKey();
            Map<String, List<TableIndexColumnUnion>> map = entry.getValue().stream().collect(
                Collectors.groupingBy(TableIndexColumnUnion::getIndexName));
            for (Map.Entry<String, List<TableIndexColumnUnion>> entry1 : tableMap.entrySet()) {
                TableIndexColumnUnion first = entry1.getValue().get(0);
                dataList.add(buildTableIndex(tableName, entry1.getKey(), first.getType(), first.getComment(),
                    entry1.getValue()));
            }
        }
        //按列分组
        return dataList;
    }

    private TableIndex buildTableIndex(String tableName, String indexName, String type, String comment,
        List<TableIndexColumnUnion> indexSubList) {
        return TableIndex.builder()
            .tableName(tableName)
            .name(indexName)
            .type(type)
            .comment(comment)
            .columnList(buildIndexColumn(indexSubList, indexName, tableName))
            .build();
    }

    private List<TableIndexColumn> buildIndexColumn(List<TableIndexColumnUnion> indexSubList, String indexName,
        String tableName) {
        return EasyCollectionUtils.toList(indexSubList, indexColumnUnion -> TableIndexColumn.builder()
            .name(indexColumnUnion.getColumnName())
            .indexName(indexName)
            .tableName(tableName)
            .collation(indexColumnUnion.getCollation())
            .build());

    }

    @Override
    public List<? extends TableColumn> queryColumnList(String databaseName, String schemaName,
        List<String> tableNames) {
        return getMapper().selectColumns(tableNames);
    }
}