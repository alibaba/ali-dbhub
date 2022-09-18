package com.alibaba.dataops.server.test.domain.data.service;

import javax.annotation.Resource;

import com.alibaba.dataops.server.domain.data.api.enums.DriverClassEnum;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceCreateParam;
import com.alibaba.dataops.server.domain.data.api.param.template.TemplateQueryParam;
import com.alibaba.dataops.server.domain.data.api.service.DataSourceDataService;
import com.alibaba.dataops.server.domain.data.api.service.JdbcTemplateDataService;
import com.alibaba.dataops.server.test.common.BaseTest;
import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

/**
 * sqlite的data服测试
 *
 * @author Jiaju Zhuang
 */
public class SqliteDataServiceTest extends BaseTest {

    private static final long DATA_SOURCE_ID = 1L;

    @Resource
    private DataSourceDataService dataSourceDataService;
    @Resource
    private JdbcTemplateDataService jdbcTemplateDataService;


    @Test
    @Order(2)
    public void createDataSource() {
        DataSourceCreateParam dataSourceCreateParam = new DataSourceCreateParam();
        dataSourceCreateParam.setDataSourceId(DATA_SOURCE_ID);
        dataSourceCreateParam.setDriverClass(DriverClassEnum.SQLITE.getCode());
        dataSourceCreateParam.setUrl("classpath:sqlite/data-ops-test.sqlite");
        ActionResult actionResult = dataSourceDataService.create(dataSourceCreateParam);
        Assertions.assertTrue(actionResult.success(), "创建数据库连接池失败");
    }

    @Test
    @Order(3)
    public void queryForList() {
        TemplateQueryParam templateQueryParam = new TemplateQueryParam();
        templateQueryParam.setDataSourceId(DATA_SOURCE_ID);
        templateQueryParam.setSql("select * from test where id=1");

        //jdbcTemplateDataService.queryForList("");

        DataSourceCreateParam dataSourceCreateParam = new DataSourceCreateParam();
        dataSourceCreateParam.setDataSourceId(DATA_SOURCE_ID);
        dataSourceCreateParam.setDriverClass(DriverClassEnum.SQLITE.getCode());
        dataSourceCreateParam.setUrl("classpath:sqlite/data-ops-test.sqlite");
        ActionResult actionResult = dataSourceDataService.create(dataSourceCreateParam);
        Assertions.assertTrue(actionResult.success(), "创建数据库连接池失败");
    }

}
