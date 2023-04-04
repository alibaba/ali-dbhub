/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect;

import java.util.List;

import com.alibaba.dbhub.server.domain.support.model.Function;
import com.alibaba.dbhub.server.domain.support.model.Procedure;
import com.alibaba.dbhub.server.domain.support.model.Table;
import com.alibaba.dbhub.server.domain.support.model.TableColumn;
import com.alibaba.dbhub.server.domain.support.model.TableIndex;
import com.alibaba.dbhub.server.domain.support.model.Trigger;
import com.alibaba.dbhub.server.domain.support.sql.DataSource;

/**
 * @author jipengfei
 * @version : BaseMetaSchema.java
 */
public abstract class BaseMetaSchema implements MetaSchema{


    @Override
    public List<String> databases() {
        return DataSource.getInstance().databases();
    }

    @Override
    public List<String> schemas(String databaseName) {
        return DataSource.getInstance().schemas(databaseName,null);
    }

    @Override
    public String tableDDL(String databaseName, String schemaName, String tableName) {
        return null;
    }

    @Override
    public void dropTable(String databaseName, String schemaName, String tableName) {
        String sql = "drop table " + tableName;
        DataSource.getInstance().executeSql(sql, resultSet -> {
            return null;
        });
    }

    @Override
    public List<Table> tables(String databaseName, String schemaName, String tableName) {
        return DataSource.getInstance().tables(databaseName,schemaName,tableName,null);
    }

    @Override
    public List<? extends Table> views(String databaseName, String schemaName) {
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
    public List<? extends TableColumn> columns(String databaseName, String schemaName, String tableName) {
        return DataSource.getInstance().columns(databaseName,schemaName,tableName,null);
    }

    @Override
    public List<? extends TableColumn> columns(String databaseName, String schemaName, String tableName,
        String columnName) {
        return DataSource.getInstance().columns(databaseName,schemaName,tableName,columnName);
    }

    @Override
    public List<? extends TableIndex> indexes(String databaseName, String schemaName, String tableName) {
        return DataSource.getInstance().indexes(databaseName,schemaName,tableName);
    }
}