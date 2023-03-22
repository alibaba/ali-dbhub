/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect.postgresql.mapper;

import java.util.List;

import com.alibaba.dbhub.server.domain.support.dialect.postgresql.model.PostgresqlColumn;
import com.alibaba.dbhub.server.domain.support.dialect.postgresql.model.PostgresqlTableIndex;

import org.apache.ibatis.annotations.Param;

/**
 * @author jipengfei
 * @version : PostgresqlMetaSchemaMapper.java, v 0.1 2022年12月13日 17:38 jipengfei Exp $
 */
public interface PostgresqlMetaSchemaMapper {

    /**
     * 查询Database
     * @return database
     */
    List<String> showDatabases();
    /**
     * 查询所有表中所有列信息
     *
     * @param tableSchema
     * @param tableName
     * @return
     */
    List<PostgresqlColumn> selectColumns(@Param("tableSchema") String tableSchema, @Param("tableName") String tableName);

    /**
     * 删除表
     *
     * @param tableName
     */
    void dropTable(@Param("tableName") String tableName);

    /**
     * 查询所有的表
     *
     * @param tableSchema
     * @return
     */
    List<String> selectTables(@Param("tableSchema") String tableSchema);

    /**
     * @param tableSchema
     * @return
     */
    Long selectTableCount(@Param("tableSchema") String tableSchema);

    /**
     * 查询建表语句
     *
     * @param tableSchema
     * @param tableName
     * @return
     */
    String showCreateTable(@Param("tableSchema") String tableSchema, @Param("tableName") String tableName);

    /**
     * 查询表索引信息
     *
     * @param tableSchema
     * @param tableName
     * @return
     */
    List<PostgresqlTableIndex> selectTableIndexes(@Param("tableSchema") String tableSchema,
        @Param("tableName") String tableName);
}