/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.model.Function;
import com.alibaba.dbhub.server.domain.support.model.Procedure;
import com.alibaba.dbhub.server.domain.support.model.Table;
import com.alibaba.dbhub.server.domain.support.model.TableColumn;
import com.alibaba.dbhub.server.domain.support.model.TableIndex;
import com.alibaba.dbhub.server.domain.support.model.Trigger;

/**
 * @author jipengfei
 * @version : MetaSchemaManager.java, v 0.1 2022年12月14日 16:26 jipengfei Exp $
 */
public interface MetaSchema<T extends BaseMapper> {
    /**
     * 支持的数据库类型
     *
     * @return
     */
    DbTypeEnum dbType();

    /**
     * 查询所有的DATABASE
     *
     * @return
     */
    List<String> databases();

    /**
     * 查询 DB 下schemas
     * @param databaseName
     * @return
     */
    List<String> schemas(String databaseName);

    /**
     * 展示建表语句
     *
     * @param databaseName
     * @param tableName
     * @return
     */
    String tableDDL(@NotEmpty String databaseName, String schemaName, @NotEmpty String tableName);

    /**
     * 删除表结构
     *
     * @param databaseName
     * @param tableName
     * @return
     */
    void dropTable(@NotEmpty String databaseName, String schemaName, @NotEmpty String tableName);

    /**
     * 分页查询表信息
     *
     * @param databaseName
     * @return
     */
    List<Table> tables(@NotEmpty String databaseName, String schemaName,String tableName);

    /**
     * 查询所有视图
     *
     * @param databaseName
     * @param schemaName
     * @return
     */
    List<? extends Table> views(@NotEmpty String databaseName, String schemaName);

    /**
     * 查询所有的函数
     *
     * @param databaseName
     * @param schemaName
     * @return
     */
    List<Function> functions(@NotEmpty String databaseName, String schemaName);

    /**
     * 查询所有触发器
     *
     * @param databaseName
     * @param schemaName
     * @return
     */
    List<Trigger> triggers(@NotEmpty String databaseName, String schemaName);

    /**
     * 查询所有存储过程
     *
     * @param databaseName
     * @param schemaName
     * @return
     */
    List<Procedure> procedures(@NotEmpty String databaseName, String schemaName);

    /**
     * 查询列的信息
     *
     * @param databaseName
     * @param tableName
     * @return
     */
    List<? extends TableColumn> columns(@NotEmpty String databaseName, String schemaName,
        @NotEmpty String tableName);

    /**
     * 查询列的信息
     *
     * @param databaseName
     * @param tableName    * @return
     */
    List<? extends TableIndex> indexes(@NotEmpty String databaseName, String schemaName, @NotEmpty String tableName);



    T  getMapper();

}