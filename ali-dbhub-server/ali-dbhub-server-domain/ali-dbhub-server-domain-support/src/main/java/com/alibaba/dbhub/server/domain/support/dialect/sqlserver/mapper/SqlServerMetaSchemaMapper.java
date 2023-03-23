/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect.sqlserver.mapper;

import java.util.List;

import com.alibaba.dbhub.server.domain.support.model.CreateTableSql;
import com.alibaba.dbhub.server.domain.support.model.ShowDatabaseResult;
import com.alibaba.dbhub.server.domain.support.model.Table;
import com.alibaba.dbhub.server.domain.support.model.TableColumn;
import com.alibaba.dbhub.server.domain.support.model.TableIndexColumnUnion;

import org.apache.ibatis.annotations.Param;

/**
 * @author jipengfei
 * @version : MysqlMetaSchemaMapper.java, v 0.1 2022年12月14日 22:27 jipengfei Exp $
 */
public interface SqlServerMetaSchemaMapper {

    /**
     * 查询Database
     *
     * @return
     */
    List<String> showDatabases();

    /**
     * 查询所有表中所有列信息
     *
     * @param databaseName
     * @param tableName
     * @return
     */
    List<TableColumn> selectColumns(@Param("databaseName") String databaseName, @Param("tableName") String tableName);

    /**
     * 删除表
     *
     * @param databaseName
     * @param tableName
     */
    void dropTable(@Param("databaseName") String databaseName, @Param("tableName") String tableName);

    /**
     * 查询所有的表
     *
     * @param databaseName
     * @param schemaName
     * @return
     */
    List<Table> selectTables(@Param("databaseName") String databaseName, @Param("schemaName") String schemaName);

    /**
     * @param databaseName
     * @return
     */
    Long selectTableCount(@Param("databaseName") String databaseName);

    /**
     * 查询建表语句
     *
     * @param databaseName
     * @param tableName
     * @return
     */
    String showCreateTable(@Param("databaseName") String databaseName,
        @Param("tableName") String tableName);

    /**
     * 查询表索引信息
     *
     * @param databaseName
     * @param tableName
     * @return
     */
    List<TableIndexColumnUnion> selectTableIndexes(@Param("databaseName") String databaseName,
        @Param("tableName") String tableName);
}