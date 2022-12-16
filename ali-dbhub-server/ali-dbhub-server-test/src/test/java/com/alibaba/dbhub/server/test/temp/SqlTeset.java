package com.alibaba.dbhub.server.test.temp;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.model.DataSourceConnect;
import com.alibaba.dbhub.server.domain.support.model.ExecuteResult;
import com.alibaba.dbhub.server.domain.support.model.Sql;
import com.alibaba.dbhub.server.domain.support.operations.ConsoleOperations;
import com.alibaba.dbhub.server.domain.support.operations.DataSourceOperations;
import com.alibaba.dbhub.server.domain.support.operations.ExampleOperations;
import com.alibaba.dbhub.server.domain.support.operations.JdbcOperations;
import com.alibaba.dbhub.server.domain.support.operations.SqlOperations;
import com.alibaba.dbhub.server.domain.support.param.console.ConsoleCreateParam;
import com.alibaba.dbhub.server.domain.support.param.datasource.DataSourceCreateParam;
import com.alibaba.dbhub.server.domain.support.param.sql.SqlAnalyseParam;
import com.alibaba.dbhub.server.domain.support.param.template.TemplateExecuteParam;
import com.alibaba.dbhub.server.test.common.BaseTest;
import com.alibaba.dbhub.server.test.domain.data.service.dialect.MysqlDialectProperties;
import com.alibaba.fastjson2.JSON;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class SqlTeset extends BaseTest {

    @Resource
    private ExampleOperations exampleOperations;
    @Resource
    private SqlOperations sqlOperations;
    @Resource
    private MysqlDialectProperties mysqlDialectProperties;
    @Resource
    private DataSourceOperations dataSourceOperations;
    @Resource
    private ConsoleOperations consoleOperations;
    @Resource
    private JdbcOperations jdbcOperations;

    @Test
    public void test() {
        // 创建
        DataSourceCreateParam dataSourceCreateParam = new DataSourceCreateParam();
        dataSourceCreateParam.setDataSourceId(1L);
        dataSourceCreateParam.setDbType(DbTypeEnum.MYSQL.getCode());
        dataSourceCreateParam.setUrl(mysqlDialectProperties.getUrl());
        dataSourceCreateParam.setUsername(mysqlDialectProperties.getUsername());
        dataSourceCreateParam.setPassword(mysqlDialectProperties.getPassword());
        DataSourceConnect dataSourceConnect = dataSourceOperations.create(dataSourceCreateParam);

        String createTable = exampleOperations.createTable(DbTypeEnum.MYSQL);
        log.info("sql1：{}", createTable);
        SqlAnalyseParam sqlAnalyseParam = new SqlAnalyseParam();
        sqlAnalyseParam.setDataSourceId(1L);
        sqlAnalyseParam.setSql(createTable);
        List<Sql> sqlList = sqlOperations.analyse(sqlAnalyseParam);
        log.info("sql：{}", JSON.toJSONString(sqlList));

        // 创建控制台
        ConsoleCreateParam consoleCreateParam = new ConsoleCreateParam();
        consoleCreateParam.setDataSourceId(1L);
        consoleCreateParam.setConsoleId(1L);
        consoleCreateParam.setDatabaseName(mysqlDialectProperties.getDatabaseName());
        consoleOperations.create(consoleCreateParam);

        // 删除
        TemplateExecuteParam templateQueryParam = new TemplateExecuteParam();
        templateQueryParam.setConsoleId(1L);
        templateQueryParam.setDataSourceId(1L);
        templateQueryParam.setSql("drop table test;");
        ExecuteResult executeResult = jdbcOperations.execute(templateQueryParam);
        log.info("result:{}", JSON.toJSONString(executeResult));

        // 创建表结构
        templateQueryParam = new TemplateExecuteParam();
        templateQueryParam.setConsoleId(1L);
        templateQueryParam.setDataSourceId(1L);
        templateQueryParam.setSql(sqlList.get(0).getSql());
        executeResult = jdbcOperations.execute(templateQueryParam);
        log.info("result:{}", JSON.toJSONString(executeResult));
    }
}
