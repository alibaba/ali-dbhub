/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect.oracle;

import java.util.Collection;
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
import com.alibaba.dbhub.server.tools.common.util.EasyCollectionUtils;

import com.google.common.collect.Lists;
import org.apache.ibatis.session.SqlSession;

/**
 * @author jipengfei
 * @version : OracleMetaSchemaSupport.java
 */
public class OracleMetaSchemaSupport implements MetaSchema<Table> {

    private SqlSession sqlSession;

    public OracleMetaSchemaSupport(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private OracleMetaSchemaMapper getMapper() {
        return sqlSession.getMapper(OracleMetaSchemaMapper.class);
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
        return getMapper().showCreateTable(schemaName,tableName);
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
        return getMapper().selectTables(databaseName,pageNo<=1?0:(pageNo-1)*pageSize, pageSize*pageSize);
    }

    @Override
    public Table queryTable(String databaseName, String schemaName, String tableName) {
        return null;
    }

    @Override
    public List<? extends TableIndex> queryIndexList(String databaseName, String schemaName, List<String> tableNames) {
        List<TableIndex> dataList = Lists.newArrayList();
        for (String tableName : tableNames) {
            List<TableIndexColumnUnion> list = getMapper().selectTableIndexes(databaseName, tableName);
            //按列分组
            Map<String, List<TableIndexColumnUnion>> map = list.stream().collect(
                Collectors.groupingBy(TableIndexColumnUnion::getIndexName));

            //组装索引
            map.forEach((indexName, indexSubList) -> {
                TableIndexColumnUnion indexColumnUnionFirst = indexSubList.get(0);
                dataList.add(
                    TableIndex.builder()
                        .tableName(tableName)
                        .name(indexName)
                        .type(indexColumnUnionFirst.getType())
                        .comment(indexColumnUnionFirst.getComment())
                        .columnList(buildIndexColumn(indexSubList, indexName, tableName))
                        .build());
            });
        }

        return dataList;
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
    public List<? extends TableColumn> queryColumnList(String databaseName, String schemaName, List<String> tableNames) {
        return tableNames.stream().map(tableName -> getMapper().selectColumns( tableName))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }
}