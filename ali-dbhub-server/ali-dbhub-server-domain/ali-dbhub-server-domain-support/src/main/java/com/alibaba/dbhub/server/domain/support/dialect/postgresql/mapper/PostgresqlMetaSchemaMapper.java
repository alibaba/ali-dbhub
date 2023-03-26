/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect.postgresql.mapper;

import java.util.List;

import com.alibaba.dbhub.server.domain.support.dialect.BaseMapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author jipengfei
 * @version : PostgresqlMetaSchemaMapper.java, v 0.1 2022年12月13日 17:38 jipengfei Exp $
 */
public interface PostgresqlMetaSchemaMapper extends BaseMapper {

    /**
     * 查询表空间
     * @param databaseName
     * @return
     */
    List<String> schemas(@Param("databaseName") String databaseName);
}