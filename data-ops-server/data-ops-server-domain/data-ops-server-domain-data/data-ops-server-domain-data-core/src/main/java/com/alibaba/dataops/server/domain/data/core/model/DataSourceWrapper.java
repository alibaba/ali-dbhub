package com.alibaba.dataops.server.domain.data.core.model;

import com.alibaba.dataops.server.domain.data.api.enums.DriverClassEnum;
import com.alibaba.druid.pool.DruidDataSource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 数据源封装
 *
 * @author Jiaju Zhuang
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class DataSourceWrapper {

    /**
     * 数据库类型
     */
    private DriverClassEnum driverClass;

    /**
     * 数据源
     */
    private DruidDataSource druidDataSource;
}
