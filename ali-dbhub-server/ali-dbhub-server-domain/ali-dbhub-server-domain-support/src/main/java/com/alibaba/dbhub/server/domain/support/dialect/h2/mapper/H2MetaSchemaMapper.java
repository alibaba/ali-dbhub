/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect.h2.mapper;

import java.util.List;

import com.alibaba.dbhub.server.domain.support.model.Table;
import com.alibaba.dbhub.server.domain.support.model.TableColumn;
import com.alibaba.dbhub.server.domain.support.model.TableIndex;
import com.alibaba.dbhub.server.domain.support.model.TableIndexColumn;

import org.apache.ibatis.annotations.Param;

/**
 * @author jipengfei
 * @version : H2MetaSchemaMapper.java, v 0.1 2022年12月14日 22:28 jipengfei Exp $
 */
public interface H2MetaSchemaMapper {

    /**
     * 查询Database
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
     * @param pageSize
     * @param pageSize
     * @return
     */
    List<Table> selectTables(@Param("databaseName") String databaseName, @Param("pageSize") long pageSize,
        @Param("pageIndex") long pageIndex);

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
    String showCreateTable(@Param("databaseName") String databaseName, @Param("tableName") String tableName);

    /**
     * 查询表索引信息
     *
     * @param databaseName
     * @param tableName
     * @return
     */
    List<TableIndexColumn> selectTableIndexColumns(@Param("databaseName") String databaseName, @Param("tableName") String tableName);


    /**
     * 查询表索引信息
     *
     * @param databaseName
     * @param tableName
     * @return
     */
    List<TableIndex> selectTableIndexes(@Param("databaseName") String databaseName, @Param("tableName") String tableName);
}