package com.alibaba.dataops.server.domain.data.core.util;

import java.util.Map;

import com.alibaba.dataops.server.domain.data.core.model.JdbcDataTemplate;
import com.alibaba.druid.pool.DruidDataSource;

import com.google.common.collect.Maps;

/**
 * 用来存储连接等数据
 *
 * @author 是仪
 */
public class DataCenterUtils {

    /**
     * 数据源
     */
    public static final Map<Long, DruidDataSource> DATA_SOURCE_CACHE = Maps.newConcurrentMap();

    /**
     * 执行模板
     */
    public static final Map<Long, JdbcDataTemplate> JDBC_TEMPLATE_CACHE = Maps.newConcurrentMap();
}
