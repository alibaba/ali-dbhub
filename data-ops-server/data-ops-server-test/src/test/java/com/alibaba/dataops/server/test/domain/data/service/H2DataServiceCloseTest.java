package com.alibaba.dataops.server.test.domain.data.service;

import javax.annotation.Resource;

import com.alibaba.dataops.server.domain.data.api.enums.DbTypeEnum;
import com.alibaba.dataops.server.domain.data.api.param.console.ConsoleCreateParam;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceCloseParam;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceCreateParam;
import com.alibaba.dataops.server.domain.data.api.param.template.TemplateQueryParam;
import com.alibaba.dataops.server.domain.data.api.service.ConsoleDataService;
import com.alibaba.dataops.server.domain.data.api.service.DataSourceDataService;
import com.alibaba.dataops.server.domain.data.api.service.JdbcTemplateDataService;
import com.alibaba.dataops.server.domain.data.core.util.DataCenterUtils;
import com.alibaba.dataops.server.test.common.BaseTest;
import com.alibaba.dataops.server.test.domain.data.utils.TestUtils;
import com.alibaba.dataops.server.tools.base.excption.BusinessException;
import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dataops.server.tools.common.enums.ErrorEnum;
import com.alibaba.druid.pool.DruidDataSource;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializer;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;

/**
 * h2的data服测试
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class H2DataServiceCloseTest extends BaseTest {
    private static final String URL = "jdbc:h2:mem:data-ops-test;MODE=MYSQL";

    private static final long DATA_SOURCE_ID = TestUtils.nextLong();
    private static final long CONSOLE_ID = TestUtils.nextLong();
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
        druidDataSource.setDriverClassName(DbTypeEnum.H2.getClassName());
        druidDataSource.setUrl(URL);

        // 初始化数据库
        DatabaseInitializationSettings databaseInitializationSettings = new DatabaseInitializationSettings();
        databaseInitializationSettings.setSchemaLocations(Lists.newArrayList("classpath:h2/init_close.sql"));
        DataSourceScriptDatabaseInitializer dataSourceScriptDatabaseInitializer
            = new DataSourceScriptDatabaseInitializer(druidDataSource, databaseInitializationSettings);
        dataSourceScriptDatabaseInitializer.initializeDatabase();
    }

    @Test
    @Order(2)
    public void closeDataSource() {
        DataSourceCreateParam dataSourceCreateParam = new DataSourceCreateParam();
        dataSourceCreateParam.setDataSourceId(DATA_SOURCE_ID);
        dataSourceCreateParam.setDbType(DbTypeEnum.H2.getCode());
        dataSourceCreateParam.setUrl(URL);
        ActionResult actionResult = dataSourceDataService.create(dataSourceCreateParam);
        Assertions.assertTrue(actionResult.success(), "创建数据库连接池失败");

        actionResult = dataSourceDataService.close(DataSourceCloseParam.builder().dataSourceId(DATA_SOURCE_ID).build());
        Assertions.assertTrue(actionResult.success(), "关闭连接池失败");
        Assertions.assertFalse(DataCenterUtils.DATA_SOURCE_CACHE.containsKey(DATA_SOURCE_ID), "关闭连接池失败");

        // 已经无法创建控制台了
        BusinessException businessException = Assertions.assertThrows(BusinessException.class, () -> {
            // 无法创建控制台
            ConsoleCreateParam consoleCreateParam = new ConsoleCreateParam();
            consoleCreateParam.setDataSourceId(DATA_SOURCE_ID);
            consoleCreateParam.setConsoleId(CONSOLE_ID);
            consoleCreateParam.setDatabaseName("test");
            consoleDataService.create(consoleCreateParam);
        }, "关闭连接池失败");
        Assertions.assertEquals(ErrorEnum.DATA_SOURCE_NOT_FOUND.getCode(), businessException.getCode(),
            "关闭连接池失败");
    }

    @Test
    @Order(3)
    public void closeDataSourceAfterCrateConsole() {
        DataSourceCreateParam dataSourceCreateParam = new DataSourceCreateParam();
        dataSourceCreateParam.setDataSourceId(DATA_SOURCE_ID);
        dataSourceCreateParam.setDbType(DbTypeEnum.H2.getCode());
        dataSourceCreateParam.setUrl(URL);
        ActionResult actionResult = dataSourceDataService.create(dataSourceCreateParam);
        Assertions.assertTrue(actionResult.success(), "创建数据库连接池失败");

        ConsoleCreateParam consoleCreateParam = new ConsoleCreateParam();
        consoleCreateParam.setDataSourceId(DATA_SOURCE_ID);
        consoleCreateParam.setConsoleId(CONSOLE_ID);
        consoleCreateParam.setDatabaseName("test");
        actionResult = consoleDataService.create(consoleCreateParam);
        Assertions.assertTrue(actionResult.success(), "创建控制台失败");

        actionResult = dataSourceDataService.close(DataSourceCloseParam.builder().dataSourceId(DATA_SOURCE_ID).build());
        Assertions.assertTrue(actionResult.success(), "关闭连接池失败");
        Assertions.assertFalse(DataCenterUtils.DATA_SOURCE_CACHE.containsKey(DATA_SOURCE_ID), "关闭连接池失败");

        // 无法执行sql了
        BusinessException businessException = Assertions.assertThrows(BusinessException.class, () -> {
            // 无法创建控制台
            TemplateQueryParam templateQueryParam = new TemplateQueryParam();
            templateQueryParam.setConsoleId(CONSOLE_ID);
            templateQueryParam.setDataSourceId(DATA_SOURCE_ID);
            templateQueryParam.setSql("select * from test_close where id=1;");
            jdbcTemplateDataService.queryForList(templateQueryParam);
        }, "关闭连接池失败");
        Assertions.assertEquals(ErrorEnum.CONSOLE_NOT_FOUND.getCode(), businessException.getCode(), "关闭连接池失败");
    }

}
