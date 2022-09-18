package com.alibaba.dataops.server.test.domain.data.service;

import javax.annotation.Resource;

import com.alibaba.dataops.server.domain.data.api.service.DataSourceDataService;
import com.alibaba.dataops.server.domain.data.api.service.JdbcTemplateDataService;
import com.alibaba.dataops.server.test.common.BaseTest;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

/**
 * sqlite的data服测试
 *
 * @author Jiaju Zhuang
 */
public class SqliteDataServiceTest extends BaseTest {

    @Resource
    private DataSourceDataService dataSourceDataService;
    @Resource
    private JdbcTemplateDataService jdbcTemplateDataService;

    @Test
    @Order(1)
    public void prepareData() {

    }

}
