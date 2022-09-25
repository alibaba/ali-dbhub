package com.alibaba.dataops.server.test.domain.data.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.dataops.server.domain.data.api.enums.DriverClassEnum;
import com.alibaba.dataops.server.domain.data.api.param.console.ConsoleCreateParam;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceCreateParam;
import com.alibaba.dataops.server.domain.data.api.param.template.TemplateQueryParam;
import com.alibaba.dataops.server.domain.data.api.param.template.TemplateUpdateParam;
import com.alibaba.dataops.server.domain.data.api.service.ConsoleDataService;
import com.alibaba.dataops.server.domain.data.api.service.DataSourceDataService;
import com.alibaba.dataops.server.domain.data.api.service.JdbcTemplateDataService;
import com.alibaba.dataops.server.test.common.BaseTest;
import com.alibaba.dataops.server.test.domain.data.utils.TestUtils;
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

    private static final long DATA_SOURCE_ID = TestUtils.nextLong();
    private static final long CONSOLE_ID = TestUtils.nextLong();
    private static final String DATA_NAME = "姓名";
    private static final Date DATA_DATE = new Date();
    private static final long DATA_NUMBER = 1L;
    @Resource
    private DataSourceDataService dataSourceDataService;
    @Resource
    private ConsoleDataService consoleDataService;
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
    @Order(2)
    public void createConsole() {
        ConsoleCreateParam consoleCreateParam = new ConsoleCreateParam();
        consoleCreateParam.setDataSourceId(DATA_SOURCE_ID);
        consoleCreateParam.setConsoleId(CONSOLE_ID);
        consoleCreateParam.setDatabaseName("test");
        ActionResult actionResult = consoleDataService.create(consoleCreateParam);
        Assertions.assertTrue(actionResult.success(), "创建控制台失败");
    }

    @Test
    @Order(3)
    public void queryForList() {
        TemplateQueryParam templateQueryParam = new TemplateQueryParam();
        templateQueryParam.setConsoleId(CONSOLE_ID);
        templateQueryParam.setDataSourceId(DATA_SOURCE_ID);
        templateQueryParam.setSql("select * from test_query where id=1;");
        List<Map<String, Object>> dataList = jdbcTemplateDataService.queryForList(templateQueryParam).getData();
        log.info("查询数据返回{}", JSON.toJSONString(dataList));
        Assertions.assertEquals(1, dataList.size(), "查询语句异常");
        Map<String, Object> data1 = dataList.get(0);
        Assertions.assertEquals(DATA_NAME, data1.get("name"), "未查询到姓名");
    }

    @Test
    @Order(4)
    public void insert() {
        TemplateUpdateParam templateUpdateParam = new TemplateUpdateParam();
        templateUpdateParam.setConsoleId(CONSOLE_ID);
        templateUpdateParam.setDataSourceId(DATA_SOURCE_ID);
        templateUpdateParam.setSql(
            "INSERT INTO `test_query` (id,name,date,number) VALUES (9999,'姓名insert','2022-01-02',1234);");
        Integer count = jdbcTemplateDataService.update(templateUpdateParam).getData();
        log.info("查询数据返回:{}", count);

        // 查数据
        TemplateQueryParam templateQueryParam = new TemplateQueryParam();
        templateQueryParam.setConsoleId(CONSOLE_ID);
        templateQueryParam.setDataSourceId(DATA_SOURCE_ID);
        templateQueryParam.setSql("select * from test_query where id=9999;");
        List<Map<String, Object>> dataList = jdbcTemplateDataService.queryForList(templateQueryParam).getData();
        log.info("查询数据返回{}", JSON.toJSONString(dataList));
        Assertions.assertEquals(1, dataList.size(), "查询语句异常");
        Map<String, Object> data1 = dataList.get(0);
        Assertions.assertEquals("姓名insert", data1.get("name"), "未查询到姓名");
    }

    @Test
    @Order(5)
    public void update() {
        TemplateUpdateParam templateUpdateParam = new TemplateUpdateParam();
        templateUpdateParam.setConsoleId(CONSOLE_ID);
        templateUpdateParam.setDataSourceId(DATA_SOURCE_ID);
        templateUpdateParam.setSql(
            "update `test_query` set name='姓名update' where id=9999;");
        Integer count = jdbcTemplateDataService.update(templateUpdateParam).getData();
        log.info("查询数据返回:{}", count);

        // 查数据
        TemplateQueryParam templateQueryParam = new TemplateQueryParam();
        templateQueryParam.setConsoleId(CONSOLE_ID);
        templateQueryParam.setDataSourceId(DATA_SOURCE_ID);
        templateQueryParam.setSql("select * from test_query where id=9999;");
        List<Map<String, Object>> dataList = jdbcTemplateDataService.queryForList(templateQueryParam).getData();
        log.info("查询数据返回{}", JSON.toJSONString(dataList));
        Assertions.assertEquals(1, dataList.size(), "查询语句异常");
        Map<String, Object> data1 = dataList.get(0);
        Assertions.assertEquals("姓名update", data1.get("name"), "未查询到姓名");
    }

    @Test
    @Order(6)
    public void delete() {
        TemplateUpdateParam templateUpdateParam = new TemplateUpdateParam();
        templateUpdateParam.setConsoleId(CONSOLE_ID);
        templateUpdateParam.setDataSourceId(DATA_SOURCE_ID);
        templateUpdateParam.setSql(
            "delete from  `test_query` where id=9999;");
        Integer count = jdbcTemplateDataService.update(templateUpdateParam).getData();
        log.info("查询数据返回:{}", count);

        // 查数据
        TemplateQueryParam templateQueryParam = new TemplateQueryParam();
        templateQueryParam.setConsoleId(CONSOLE_ID);
        templateQueryParam.setDataSourceId(DATA_SOURCE_ID);
        templateQueryParam.setSql("select * from test_query where id=9999;");
        List<Map<String, Object>> dataList = jdbcTemplateDataService.queryForList(templateQueryParam).getData();
        log.info("查询数据返回{}", JSON.toJSONString(dataList));
        Assertions.assertEquals(0, dataList.size(), "查询语句异常");
    }

    @Test
    @Order(6)
    public void errorSql() {
        TemplateUpdateParam templateUpdateParam = new TemplateUpdateParam();
        templateUpdateParam.setConsoleId(CONSOLE_ID);
        templateUpdateParam.setDataSourceId(DATA_SOURCE_ID);
        templateUpdateParam.setSql(
            "delete from1  `test_query` where id=9999;");
        Integer count = jdbcTemplateDataService.update(templateUpdateParam).getData();
        log.info("查询数据返回:{}", count);

        // 查数据
        TemplateQueryParam templateQueryParam = new TemplateQueryParam();
        templateQueryParam.setConsoleId(CONSOLE_ID);
        templateQueryParam.setDataSourceId(DATA_SOURCE_ID);
        templateQueryParam.setSql("select * from test_query where1 id=9999;");
        List<Map<String, Object>> dataList = jdbcTemplateDataService.queryForList(templateQueryParam).getData();
        log.info("查询数据返回{}", JSON.toJSONString(dataList));
        Assertions.assertEquals(1, dataList.size(), "查询语句异常");
        Map<String, Object> data1 = dataList.get(0);
        Assertions.assertEquals("姓名update", data1.get("name"), "未查询到姓名");
    }
}
