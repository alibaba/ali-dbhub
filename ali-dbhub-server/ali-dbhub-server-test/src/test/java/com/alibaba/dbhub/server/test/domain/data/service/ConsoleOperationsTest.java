package com.alibaba.dbhub.server.test.domain.data.service;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.operations.ConsoleOperations;
import com.alibaba.dbhub.server.domain.support.operations.DataSourceOperations;
import com.alibaba.dbhub.server.domain.support.param.console.ConsoleCloseParam;
import com.alibaba.dbhub.server.domain.support.param.console.ConsoleCreateParam;
import com.alibaba.dbhub.server.domain.support.param.datasource.DataSourceCloseParam;
import com.alibaba.dbhub.server.domain.support.param.datasource.DataSourceCreateParam;
import com.alibaba.dbhub.server.test.common.BaseTest;
import com.alibaba.dbhub.server.test.domain.data.service.dialect.DialectProperties;
import com.alibaba.dbhub.server.test.domain.data.utils.TestUtils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 数据源测试
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class ConsoleOperationsTest extends BaseTest {
    @Resource
    private DataSourceOperations dataSourceOperations;
    @Resource
    private ConsoleOperations consoleOperations;
    @Autowired
    private List<DialectProperties> dialectPropertiesList;

    @Test
    @Order(1)
    public void createAndClose() {
        for (DialectProperties dialectProperties : dialectPropertiesList) {
            DbTypeEnum dbTypeEnum = dialectProperties.getDbType();
            Long dataSourceId = TestUtils.nextLong();
            Long consoleId = TestUtils.nextLong();
            TestUtils.buildContext(dialectProperties, dataSourceId, consoleId);

            DataSourceCreateParam dataSourceCreateParam = new DataSourceCreateParam();
            dataSourceCreateParam.setDataSourceId(dataSourceId);
            dataSourceCreateParam.setDbType(dbTypeEnum.getCode());
            dataSourceCreateParam.setUrl(dialectProperties.getUrl());
            dataSourceCreateParam.setUsername(dialectProperties.getUsername());
            dataSourceCreateParam.setPassword(dialectProperties.getPassword());
            dataSourceOperations.create(dataSourceCreateParam);

            // 创建
            ConsoleCreateParam consoleCreateParam = new ConsoleCreateParam();
            consoleCreateParam.setDataSourceId(dataSourceId);
            consoleCreateParam.setConsoleId(consoleId);
            consoleCreateParam.setDatabaseName(dialectProperties.getDatabaseName());
            consoleOperations.create(consoleCreateParam);

            // 关闭
            ConsoleCloseParam consoleCloseParam = new ConsoleCloseParam();
            consoleCloseParam.setDataSourceId(dataSourceId);
            consoleCloseParam.setConsoleId(consoleId);
            consoleOperations.close(consoleCloseParam);
            TestUtils.remove();
        }
    }

    @Test
    @Order(2)
    public void createAfterDataSourceClose() {
        for (DialectProperties dialectProperties : dialectPropertiesList) {
            DbTypeEnum dbTypeEnum = dialectProperties.getDbType();
            Long dataSourceId = TestUtils.nextLong();
            Long consoleId = TestUtils.nextLong();
            TestUtils.buildContext(dialectProperties, dataSourceId, consoleId);

            DataSourceCreateParam dataSourceCreateParam = new DataSourceCreateParam();
            dataSourceCreateParam.setDataSourceId(dataSourceId);
            dataSourceCreateParam.setDbType(dbTypeEnum.getCode());
            dataSourceCreateParam.setUrl(dialectProperties.getUrl());
            dataSourceCreateParam.setUsername(dialectProperties.getUsername());
            dataSourceCreateParam.setPassword(dialectProperties.getPassword());
            dataSourceOperations.create(dataSourceCreateParam);

            DataSourceCloseParam dataSourceCloseParam = new DataSourceCloseParam();
            dataSourceCloseParam.setDataSourceId(dataSourceId);
            dataSourceOperations.close(dataSourceCloseParam);

            TestUtils.remove();
        }
    }

    @Test
    @Order(3)
    public void closeDataSourceAfterCreateConsole() {
        for (DialectProperties dialectProperties : dialectPropertiesList) {
            DbTypeEnum dbTypeEnum = dialectProperties.getDbType();
            Long dataSourceId = TestUtils.nextLong();
            Long consoleId = TestUtils.nextLong();
            TestUtils.buildContext(dialectProperties, dataSourceId, consoleId);
            DataSourceCreateParam dataSourceCreateParam = new DataSourceCreateParam();
            dataSourceCreateParam.setDataSourceId(dataSourceId);
            dataSourceCreateParam.setDbType(dbTypeEnum.getCode());
            dataSourceCreateParam.setUrl(dialectProperties.getUrl());
            dataSourceCreateParam.setUsername(dialectProperties.getUsername());
            dataSourceCreateParam.setPassword(dialectProperties.getPassword());
            dataSourceOperations.create(dataSourceCreateParam);

            // 创建控制台
            ConsoleCreateParam consoleCreateParam = new ConsoleCreateParam();
            consoleCreateParam.setDataSourceId(dataSourceId);
            consoleCreateParam.setConsoleId(consoleId);
            consoleCreateParam.setDatabaseName(dialectProperties.getDatabaseName());
            consoleOperations.create(consoleCreateParam);

            DataSourceCloseParam dataSourceCloseParam = new DataSourceCloseParam();
            dataSourceCloseParam.setDataSourceId(dataSourceId);
            dataSourceOperations.close(dataSourceCloseParam);
            TestUtils.remove();
        }
    }

}
