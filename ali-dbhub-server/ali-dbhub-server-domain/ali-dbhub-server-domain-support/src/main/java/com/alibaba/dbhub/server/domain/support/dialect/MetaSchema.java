/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.alibaba.dbhub.server.domain.support.dialect.common.model.SpiExample;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.model.TableColumn;
import com.alibaba.dbhub.server.domain.support.model.TableIndex;

/**
 * @author jipengfei
 * @version : MetaSchemaManager.java, v 0.1 2022年12月14日 16:26 jipengfei Exp $
 */
public interface MetaSchema<T> {
    /**
     * 支持的数据库类型
     *
     * @return
     */
    DbTypeEnum supportDbType();

    /**
     * 查询所有的DATABASE
     * @return
     */
    List<String> showDatabases();
    /**
     * 展示建表语句
     *
     * @param databaseName
     * @param tableName
     * @return
     */
    String showCreateTable(@NotEmpty String databaseName, String schemaName, @NotEmpty String tableName);

    /**
     * 删除表结构
     *
     * @param databaseName
     * @param tableName
     * @return
     */
    void dropTable(@NotEmpty String databaseName, String schemaName,
        @NotEmpty String tableName);

    /**
     * 查询数据库表的数量
     *
     * @param databaseName
     * @return
     */
    int queryTableCount(@NotEmpty String databaseName, String schemaName);

    /**
     * 分页查询表信息
     *
     * @param databaseName
     * @return
     */
    List<T> queryTableList( @NotEmpty String databaseName,
        String schemaName, int pageNo, int pageSize);

    /**
     * @param databaseName
     * @param tableName
     * @return
     */
    T queryTable( @NotEmpty String databaseName, String schemaName,
        @NotEmpty String tableName);

    /**
     * 查询列的信息
     *
     * @param databaseName
     * @param tableNames
     * @return
     */
    List<? extends TableColumn> queryColumnList( @NotEmpty String databaseName,
        String schemaName, @NotEmpty List<String> tableNames);

    /**
     * 查询列的信息
     *
     * @param databaseName
     * @param tableNames   * @return
     */
    List<? extends TableIndex> queryIndexList( @NotEmpty String databaseName,
        String schemaName, @NotEmpty List<String> tableNames);


}