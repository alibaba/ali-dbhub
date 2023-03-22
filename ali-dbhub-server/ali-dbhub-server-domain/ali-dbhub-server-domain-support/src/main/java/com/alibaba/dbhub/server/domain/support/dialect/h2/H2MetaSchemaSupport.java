/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect.h2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.dbhub.server.domain.support.dialect.MetaSchema;
import com.alibaba.dbhub.server.domain.support.dialect.h2.mapper.H2MetaSchemaMapper;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.model.Function;
import com.alibaba.dbhub.server.domain.support.model.Procedure;
import com.alibaba.dbhub.server.domain.support.model.ShowDatabaseResult;
import com.alibaba.dbhub.server.domain.support.model.Table;
import com.alibaba.dbhub.server.domain.support.model.TableColumn;
import com.alibaba.dbhub.server.domain.support.model.TableIndex;
import com.alibaba.dbhub.server.domain.support.model.TableIndexColumn;
import com.alibaba.dbhub.server.domain.support.model.Trigger;
import com.alibaba.dbhub.server.domain.support.sql.DbhubDataSource;
import com.alibaba.dbhub.server.tools.common.util.EasyCollectionUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.util.CollectionUtils;

/**
 * @author jipengfei
 * @version : H2MetaSchemaSupport.java
 */
@Slf4j
public class H2MetaSchemaSupport implements MetaSchema<Table> {

    @Override
    public List<String> databases() {
        return EasyCollectionUtils.toList(getMapper().showDatabases(), ShowDatabaseResult::getDatabase);
    }

    @Override
    public List<String> schemas(String databaseName) {
        return null;
    }

    @Override
    public DbTypeEnum dbType() {
        return DbTypeEnum.H2;

    }

    @Override
    public String tableDDL(String databaseName, String schemaName, String tableName) {
        try {
            return getMapper().showCreateTable(databaseName, tableName);
        } catch (PersistenceException e) {
            // 这里有个坑 就是 h2的内存模式无法获取建表语句
            // 报错直接返回空
            // 想办法看看能不能解决
            log.warn("h2查询建表语句失败", e);
            return null;
        }
    }

    @Override
    public void dropTable(String databaseName, String schemaName, String tableName) {
        getMapper().dropTable(databaseName, tableName);
    }

    @Override
    public List<Table> tables(String databaseName, String schemaName) {
        return getMapper().selectTables(databaseName);
    }

    @Override
    public List<Table> views(String databaseName, String schemaName) {
        return null;
    }

    @Override
    public List<Function> functions(String databaseName, String schemaName) {
        return null;
    }

    @Override
    public List<Trigger> triggers(String databaseName, String schemaName) {
        return null;
    }

    @Override
    public List<Procedure> procedures(String databaseName, String schemaName) {
        return null;
    }

    @Override
    public List<TableColumn> columns(String databaseName, String schemaName, String tableName) {
        return getMapper().selectColumns(databaseName, tableName);
    }

    @Override
    public List<TableIndex> indexes(String databaseName, String schemaName,
        String tableName) {
        List<TableIndex> indexList = new ArrayList<>();
        H2MetaSchemaMapper mapper = getMapper();
        List<TableIndex> tableIndexList = mapper.selectTableIndexes(databaseName, tableName);
        if (!CollectionUtils.isEmpty(tableIndexList)) {
            List<TableIndexColumn> columnList = mapper.selectTableIndexColumns(databaseName, tableName);
            Map<String, List<TableIndexColumn>> columnMap = EasyCollectionUtils.stream(columnList)
                .collect(Collectors.groupingBy(TableIndexColumn::getIndexName));
            for (TableIndex tableIndex : tableIndexList) {
                tableIndex.setColumnList(columnMap.get(tableIndex.getName()));
            }
            indexList.addAll(tableIndexList);
        }
        return indexList;
    }

    private H2MetaSchemaMapper getMapper() {
        return DbhubDataSource.getInstance().getMapper(H2MetaSchemaMapper.class);
    }
}