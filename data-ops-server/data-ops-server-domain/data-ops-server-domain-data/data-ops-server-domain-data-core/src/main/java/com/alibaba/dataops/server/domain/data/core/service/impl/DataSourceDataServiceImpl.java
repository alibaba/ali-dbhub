package com.alibaba.dataops.server.domain.data.core.service.impl;

import com.alibaba.dataops.server.domain.data.api.enums.DriverClassEnum;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceCreateParam;
import com.alibaba.dataops.server.domain.data.api.service.DataSourceDataService;
import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dataops.server.tools.common.util.EasyEnumUtils;
import com.alibaba.druid.pool.DruidDataSource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 数据库连接源服务
 *
 * @author Jiaju Zhuang
 */
@Service
@Slf4j
public class DataSourceDataServiceImpl implements DataSourceDataService {

    @Override
    public ActionResult create(DataSourceCreateParam param) {
        DriverClassEnum driverClass = EasyEnumUtils.getEnum(DriverClassEnum.class, param.getDriverClass());
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driverClass.getClassName());
        druidDataSource.setUrl(param.getUrl());
        druidDataSource.setPassword(param.getPassword());
        druidDataSource.setUrl(param.getUrl());
        JdbcTemplateDataServiceImpl.JDBC_TEMPLATE_MAP.put(param.getDataSourceId(), new JdbcTemplate(druidDataSource));
        return ActionResult.isSuccess();
    }
}
