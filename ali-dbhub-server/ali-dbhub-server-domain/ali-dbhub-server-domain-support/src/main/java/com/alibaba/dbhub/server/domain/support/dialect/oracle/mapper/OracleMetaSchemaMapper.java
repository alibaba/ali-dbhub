/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect.oracle.mapper;

import java.util.List;

import com.alibaba.dbhub.server.domain.support.dialect.postgresql.model.PostgresqlColumn;
import com.alibaba.dbhub.server.domain.support.dialect.postgresql.model.PostgresqlTableIndex;
import com.alibaba.dbhub.server.domain.support.model.Table;
import com.alibaba.dbhub.server.domain.support.model.TableColumn;
import com.alibaba.dbhub.server.domain.support.model.TableIndexColumnUnion;

import org.apache.ibatis.annotations.Param;

/**
 * @author jipengfei
 * @version : OracleMetaSchemaMapper.java, v 0.1 2022年12月13日 17:38 jipengfei Exp $
 */
public interface OracleMetaSchemaMapper {

    /**
     * 查询Database
     *
     * @return
     */
    List<String> showDatabases();

    /**
     * 查询所有表中所有列信息
     *
     * @param tableNames
     * @return
     */
    List<TableColumn> selectColumns( @Param("tableNames") List<String> tableNames);

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
     * @param start
     * @param end
     * @return
     */
    List<Table> selectTables(@Param("tableSchema") String tableSchema, @Param("start") long start,
        @Param("end") long end);

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
     * @param databaseName
     * @param tableNames
     * @return
     */
    List<TableIndexColumnUnion> selectTableIndexes(@Param("databaseName") String databaseName,
        @Param("tableNames") List<String> tableNames);
}