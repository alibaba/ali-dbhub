package com.alibaba.dataops.server.domain.data.core.util;

import java.sql.Connection;
import java.util.Map;

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
     * 数据连接
     */
    public static final Map<Long, Connection> CONNECTION_CACHE = Maps.newConcurrentMap();
}
