/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect.h2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.dbhub.server.domain.support.dialect.MetaSchema;
import com.alibaba.dbhub.server.domain.support.dialect.common.model.SpiExample;
import com.alibaba.dbhub.server.domain.support.dialect.h2.mapper.H2MetaSchemaMapper;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.model.Table;
import com.alibaba.dbhub.server.domain.support.model.TableColumn;
import com.alibaba.dbhub.server.domain.support.model.TableIndex;
import com.alibaba.dbhub.server.domain.support.model.TableIndexColumn;
import com.alibaba.dbhub.server.tools.common.util.EasyCollectionUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import static com.alibaba.dbhub.server.domain.support.dialect.common.SQLKeyConst.H2_ALTER_TABLE_SIMPLE;
import static com.alibaba.dbhub.server.domain.support.dialect.common.SQLKeyConst.H2_CREATE_TABLE_SIMPLE;

/**
 * @author jipengfei
 * @version : H2MetaSchemaSupport.java
 */
@Slf4j
public class H2MetaSchemaSupport implements MetaSchema<Table> {

    private SqlSession sqlSession;

    public H2MetaSchemaSupport(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public DbTypeEnum supportDbType() {
        return DbTypeEnum.H2;

    }

    @Override
    public String showCreateTable( String databaseName, String schemaName, String tableName) {
        return getMapper().showCreateTable(databaseName, tableName);
    }

    @Override
    public void dropTable( String databaseName, String schemaName, String tableName) {
        getMapper().dropTable(databaseName, tableName);
    }

    @Override
    public int queryTableCount( String databaseName, String schemaName) {
        return getMapper().selectTableCount(databaseName).intValue();
    }

    @Override
    public List<Table> queryTableList( String databaseName, String schemaName, int pageNo,
        int pageSize) {
        return getMapper().selectTables(databaseName, pageSize, pageNo <= 1 ? 0 : (pageNo - 1) * pageSize);
    }

    @Override
    public Table queryTable( String databaseName, String schemaName, String tableName) {
        return null;
    }

    @Override
    public List<TableColumn> queryColumnList( String databaseName, String schemaName,
        List<String> tableNames) {
        return tableNames.stream().map(tableName -> getMapper().selectColumns(databaseName, tableName))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    @Override
    public List<TableIndex> queryIndexList( String databaseName, String schemaName,
        List<String> tableNames) {
        List<TableIndex> indexList = new ArrayList<>();
        H2MetaSchemaMapper mapper = getMapper();
        for (String tableName : tableNames) {
            List<TableIndex> tableIndexList = mapper.selectTableIndexes(databaseName, tableName);
            if (!CollectionUtils.isEmpty(tableIndexList)) {
                List<TableIndexColumn> columnList = mapper.selectTableIndexColumns(databaseName, tableName);
                Map<String, List<TableIndexColumn>> columnMap = EasyCollectionUtils.stream(columnList)
                    .collect(Collectors.groupingBy(TableIndexColumn::getIndexName));
                for (TableIndex tableIndex : indexList) {
                    tableIndex.setColumnList(columnMap.get(tableIndex.getName()));
                }
                indexList.addAll(tableIndexList);
            }
        }
        return indexList;
    }

    private H2MetaSchemaMapper getMapper() {
        return sqlSession.getMapper(H2MetaSchemaMapper.class);
    }
}