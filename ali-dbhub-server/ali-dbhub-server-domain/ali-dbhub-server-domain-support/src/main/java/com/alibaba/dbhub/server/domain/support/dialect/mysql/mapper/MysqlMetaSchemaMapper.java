/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect.mysql.mapper;

import com.alibaba.dbhub.server.domain.support.dialect.BaseMapper;
import com.alibaba.dbhub.server.domain.support.model.CreateTableSql;

import org.apache.ibatis.annotations.Param;

/**
 * @author jipengfei
 * @version : MysqlMetaSchemaMapper.java, v 0.1 2022年12月14日 22:27 jipengfei Exp $
 */
public interface MysqlMetaSchemaMapper extends BaseMapper {


    /**
     * 查询建表语句
     *
     * @param databaseName
     * @param tableName
     * @return
     */
    CreateTableSql showCreateTable0(@Param("databaseName") String databaseName,
        @Param("tableName") String tableName);
}