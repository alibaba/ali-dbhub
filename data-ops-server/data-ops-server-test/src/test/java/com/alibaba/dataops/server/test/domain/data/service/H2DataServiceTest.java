package com.alibaba.dataops.server.test.domain.data.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.dataops.server.domain.data.api.enums.DriverClassEnum;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceCreateParam;
import com.alibaba.dataops.server.domain.data.api.param.template.TemplateQueryParam;
import com.alibaba.dataops.server.domain.data.api.service.DataSourceDataService;
import com.alibaba.dataops.server.domain.data.api.service.JdbcTemplateDataService;
import com.alibaba.dataops.server.test.common.BaseTest;
import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson2.JSON;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializer;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;

/**
 * sqlite的data服测试
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class H2DataServiceTest extends BaseTest {
    private static final String URL = "jdbc:h2:mem:data-ops-test;MODE=MYSQL";

    private static final long DATA_SOURCE_ID = 1L;

    private static final String DATA_NAME = "姓名";
    private static final Date DATA_DATE = new Date();
    private static final long DATA_NUMBER = 1L;
    @Resource
    private DataSourceDataService dataSourceDataService;
    @Resource
    private JdbcTemplateDataService jdbcTemplateDataService;

    @Test
    @Order(1)
    public void prepareData() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(DriverClassEnum.H2.getClassName());
        druidDataSource.setUrl(URL);

        // 初始化数据库
        DatabaseInitializationSettings databaseInitializationSettings = new DatabaseInitializationSettings();
        databaseInitializationSettings.setSchemaLocations(Lists.newArrayList("classpath:h2/init.sql"));
        DataSourceScriptDatabaseInitializer dataSourceScriptDatabaseInitializer
            = new DataSourceScriptDatabaseInitializer(druidDataSource, databaseInitializationSettings);
        dataSourceScriptDatabaseInitializer.initializeDatabase();
    }

    @Test
    @Order(2)
    public void createDataSource() {
        DataSourceCreateParam dataSourceCreateParam = new DataSourceCreateParam();
        dataSourceCreateParam.setDataSourceId(DATA_SOURCE_ID);
        dataSourceCreateParam.setDriverClass(DriverClassEnum.H2.getCode());
        dataSourceCreateParam.setUrl(URL);
        ActionResult actionResult = dataSourceDataService.create(dataSourceCreateParam);
        Assertions.assertTrue(actionResult.success(), "创建数据库连接池失败");
    }

    @Test
    @Order(3)
    public void queryForList() {
        TemplateQueryParam templateQueryParam = new TemplateQueryParam();
        templateQueryParam.setDataSourceId(DATA_SOURCE_ID);
        templateQueryParam.setSql("select * from test where id=1");
        List<Map<String, Object>> dataList = jdbcTemplateDataService.queryForList(templateQueryParam).getData();
        log.info("查询数据返回{}", JSON.toJSONString(dataList));
        Assertions.assertEquals(1, dataList.size(), "查询语句异常");
        Map<String, Object> data1 = dataList.get(0);
        Assertions.assertEquals(DATA_NAME, data1.get("name"), "未查询到姓名");

    }

}
