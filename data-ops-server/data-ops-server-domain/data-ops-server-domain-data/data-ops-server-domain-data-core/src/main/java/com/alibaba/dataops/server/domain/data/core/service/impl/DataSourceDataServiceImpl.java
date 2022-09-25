package com.alibaba.dataops.server.domain.data.core.service.impl;

import com.alibaba.dataops.server.domain.data.api.enums.DriverClassEnum;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceCloseParam;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceCreateParam;
import com.alibaba.dataops.server.domain.data.api.service.DataSourceDataService;
import com.alibaba.dataops.server.domain.data.core.util.DataCenterUtils;
import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dataops.server.tools.common.util.EasyEnumUtils;
import com.alibaba.druid.pool.DruidDataSource;

import lombok.extern.slf4j.Slf4j;
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
        log.info("创建数据库连接源:{}", param.getDataSourceId());
        Long dataSourceId = param.getDataSourceId();
        // 尝试先关闭连接源
        close(DataSourceCloseParam.builder().dataSourceId(dataSourceId).build());

        DriverClassEnum driverClass = EasyEnumUtils.getEnum(DriverClassEnum.class, param.getDriverClass());
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driverClass.getClassName());
        druidDataSource.setUrl(param.getUrl());
        druidDataSource.setPassword(param.getPassword());
        druidDataSource.setUrl(param.getUrl());
        // 不设置最大连接数
        druidDataSource.setMaxActive(999);
        druidDataSource.setInitialSize(0);
        // 放入缓存
        DataCenterUtils.DATA_SOURCE_CACHE.put(dataSourceId, druidDataSource);
        return ActionResult.isSuccess();
    }

    @Override
    public ActionResult close(DataSourceCloseParam param) {
        DruidDataSource druidDataSource = DataCenterUtils.DATA_SOURCE_CACHE.remove(param.getDataSourceId());
        if (druidDataSource == null) {
            log.info("数据库连接源:{}不需要关闭", param.getDataSourceId());
            return ActionResult.isSuccess();
        }
        druidDataSource.close();
        return ActionResult.isSuccess();
    }
}
