/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect.mysql.mapper;

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
public interface MysqlMetaSchemaMapper {

    /**
     * 查询Database
     *
     * @return
     */
    List<ShowDatabaseResult> showDatabases();

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
     * @param tableName
     * @param pageSize
     * @param pageSize
     * @return
     */
    List<Table> selectTables(@Param("databaseName") String databaseName, @Param("tableName") String tableName,
        @Param("pageSize") long pageSize, @Param("pageIndex") long pageIndex);

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
    CreateTableSql showCreateTable(@Param("databaseName") String databaseName,
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