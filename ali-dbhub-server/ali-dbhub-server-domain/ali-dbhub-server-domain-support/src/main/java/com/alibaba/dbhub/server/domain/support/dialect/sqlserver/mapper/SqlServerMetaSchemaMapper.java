/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect.sqlserver.mapper;

import java.util.List;

import com.alibaba.dbhub.server.domain.support.dialect.BaseMapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author jipengfei
 * @version : MysqlMetaSchemaMapper.java, v 0.1 2022年12月14日 22:27 jipengfei Exp $
 */
public interface SqlServerMetaSchemaMapper extends BaseMapper {

    /**
     * 删除DDL函数
     * @param tableSchema
     */
    void dropDDLFunction(@Param("tableSchema") String tableSchema);

    /**
     * 创建ddl函数
     * @param tableSchema
     */
    void createDDLFunction(@Param("tableSchema") String tableSchema);


    /**
     * 查询表空间
     * @param databaseName
     * @return
     */
    List<String> schemas(@Param("databaseName") String databaseName);
}