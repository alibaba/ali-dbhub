package com.alibaba.dataops.server.test.domain.data.service;

import javax.annotation.Resource;

import com.alibaba.dataops.server.domain.data.api.enums.DbTypeEnum;
import com.alibaba.dataops.server.domain.data.api.param.console.ConsoleCreateParam;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceCreateParam;
import com.alibaba.dataops.server.domain.data.api.param.template.TemplateExecuteParam;
import com.alibaba.dataops.server.domain.data.api.service.ConsoleDataService;
import com.alibaba.dataops.server.domain.data.api.service.DataSourceDataService;
import com.alibaba.dataops.server.domain.data.api.service.JdbcTemplateDataService;
import com.alibaba.dataops.server.test.common.BaseTest;
import com.alibaba.dataops.server.test.domain.data.utils.TestUtils;
import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.druid.pool.DruidDataSource;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializer;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;

/**
 * h2的事务测试
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class H2DataServiceTransactionTest extends BaseTest {
    private static final String URL = "jdbc:h2:mem:data-ops-test;MODE=MYSQL";

    private static final long DATA_SOURCE_ID = TestUtils.nextLong();
    private static final long CONSOLE_ID = TestUtils.nextLong();
    private static final long CONSOLE2_ID = TestUtils.nextLong();
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
        databaseInitializationSettings.setSchemaLocations(Lists.newArrayList("classpath:h2/init_transaction.sql"));
        DataSourceScriptDatabaseInitializer dataSourceScriptDatabaseInitializer
            = new DataSourceScriptDatabaseInitializer(druidDataSource, databaseInitializationSettings);
        dataSourceScriptDatabaseInitializer.initializeDatabase();
    }

    @Test
    @Order(2)
    public void testTransaction() {
        // 创建数据库
        DataSourceCreateParam dataSourceCreateParam = new DataSourceCreateParam();
        dataSourceCreateParam.setDataSourceId(DATA_SOURCE_ID);
        dataSourceCreateParam.setDriverClass(DbTypeEnum.H2.getCode());
        dataSourceCreateParam.setUrl(URL);
        ActionResult actionResult = dataSourceDataService.create(dataSourceCreateParam);
        Assertions.assertTrue(actionResult.success(), "创建数据库连接池失败");

        // 创建第一个链接
        ConsoleCreateParam consoleCreateParam = new ConsoleCreateParam();
        consoleCreateParam.setDataSourceId(DATA_SOURCE_ID);
        consoleCreateParam.setConsoleId(CONSOLE_ID);
        consoleCreateParam.setDatabaseName("test");
        actionResult = consoleDataService.create(consoleCreateParam);
        Assertions.assertTrue(actionResult.success(), "创建控制台失败");

        // 创建第2个链接
        consoleCreateParam = new ConsoleCreateParam();
        consoleCreateParam.setDataSourceId(DATA_SOURCE_ID);
        consoleCreateParam.setConsoleId(CONSOLE2_ID);
        consoleCreateParam.setDatabaseName("test");
        actionResult = consoleDataService.create(consoleCreateParam);
        Assertions.assertTrue(actionResult.success(), "创建控制台失败");

        // 关闭自动提交
        TemplateExecuteParam templateExecuteParam = new TemplateExecuteParam();
        templateExecuteParam.setConsoleId(CONSOLE_ID);
        templateExecuteParam.setDataSourceId(DATA_SOURCE_ID);
        templateExecuteParam.setSql("set autocommit = false;");
        jdbcTemplateDataService.execute(templateExecuteParam);

        // 查询是否自动提交
        //TemplateQueryParam templateQueryParam = new TemplateQueryParam();
        //templateQueryParam.setConsoleId(CONSOLE_ID);
        //templateQueryParam.setDataSourceId(DATA_SOURCE_ID);
        //templateQueryParam.setSql("SHOW VARIABLES LIKE 'autocommit';");
        //List<Map<String, Object>> dataList = jdbcTemplateDataService.queryForList(templateQueryParam).getData();
        //log.info("查询自动提交返回：{}", dataList);
    }

}
