/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect.mysql;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.dbhub.server.domain.support.dialect.MetaSchema;
import com.alibaba.dbhub.server.domain.support.dialect.mysql.mapper.MysqlMetaSchemaMapper;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.model.CreateTableSql;
import com.alibaba.dbhub.server.domain.support.model.Table;
import com.alibaba.dbhub.server.domain.support.model.TableColumn;
import com.alibaba.dbhub.server.domain.support.model.TableIndex;
import com.alibaba.dbhub.server.domain.support.model.TableIndexColumn;
import com.alibaba.dbhub.server.domain.support.model.TableIndexColumnUnion;
import com.alibaba.dbhub.server.tools.common.util.EasyCollectionUtils;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

/**
 * @author jipengfei
 * @version : MysqlMetaSchemaSupport.java, v 0.1 2022年12月14日 22:44 jipengfei Exp $
 */
@Slf4j
public class MysqlMetaSchemaSupport implements MetaSchema<Table> {
    private SqlSession sqlSession;

    @Override
    public List<String> showDatabases() {
        return getMapper().showDatabases().stream().map(r->r.getDatabase()).collect(Collectors.toList());
    }

    public MysqlMetaSchemaSupport(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
    @Override
    public DbTypeEnum supportDbType() {
        return DbTypeEnum.MYSQL;
    }

    @Override
    public String showCreateTable(String databaseName, String schemaName, String tableName) {
     CreateTableSql createTable =   getMapper().showCreateTable(databaseName, tableName);
        return createTable.getSql();
    }

    @Override
    public void dropTable(String databaseName, String schemaName, String tableName) {
        getMapper().dropTable(databaseName, tableName);
    }

    @Override
    public int queryTableCount(String databaseName, String schemaName) {
        return getMapper().selectTableCount(databaseName).intValue();
    }

    @Override
    public List queryTableList(String databaseName, String tableName, int pageNo,
        int pageSize) {
        return getMapper().selectTables(databaseName,tableName, pageSize, pageNo <= 1 ? 0 : (pageNo - 1) * pageSize);
    }

    @Override
    public Table queryTable(String databaseName, String schemaName, String tableName) {
        return null;
    }

    @Override
    public List<TableIndex> queryIndexList(String databaseName, String schemaName,
        List<String> tableNames) {
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
    public List<TableColumn> queryColumnList(String databaseName, String schemaName,
        List<String> tableNames) {
        return tableNames.stream().map(tableName -> getMapper().selectColumns(databaseName, tableName))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    private MysqlMetaSchemaMapper getMapper() {
        return sqlSession.getMapper(MysqlMetaSchemaMapper.class);
    }
}