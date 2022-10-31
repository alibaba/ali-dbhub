package com.alibaba.dataops.server.test.domain.data.service;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dataops.server.domain.data.api.enums.DbTypeEnum;
import com.alibaba.dataops.server.domain.data.api.param.console.ConsoleCloseParam;
import com.alibaba.dataops.server.domain.data.api.param.console.ConsoleCreateParam;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceCloseParam;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceCreateParam;
import com.alibaba.dataops.server.domain.data.api.service.ConsoleDataService;
import com.alibaba.dataops.server.domain.data.api.service.DataSourceDataService;
import com.alibaba.dataops.server.domain.data.core.util.DataCenterUtils;
import com.alibaba.dataops.server.test.common.BaseTest;
import com.alibaba.dataops.server.test.domain.data.service.dialect.DialectProperties;
import com.alibaba.dataops.server.test.domain.data.utils.TestUtils;
import com.alibaba.dataops.server.tools.base.excption.BusinessException;
import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dataops.server.tools.common.enums.ErrorEnum;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 数据源测试
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class ConsoleDataServiceTest  extends BaseTest {
    @Resource
    private DataSourceDataService dataSourceDataService;
    @Resource
    private ConsoleDataService consoleDataService;
    @Autowired
    private List<DialectProperties> dialectPropertiesList;

    @Test
    @Order(1)
    public void createAndClose() {
        for (DialectProperties dialectProperties : dialectPropertiesList) {
            DbTypeEnum dbTypeEnum = dialectProperties.getDbType();
            Long dataSourceId = TestUtils.nextLong();
            Long consoleId = TestUtils.nextLong();

            DataSourceCreateParam dataSourceCreateParam = new DataSourceCreateParam();
            dataSourceCreateParam.setDataSourceId(dataSourceId);
            dataSourceCreateParam.setDbType(dbTypeEnum.getCode());
            dataSourceCreateParam.setUrl(dialectProperties.getUrl());
            dataSourceCreateParam.setUsername(dialectProperties.getUsername());
            dataSourceCreateParam.setPassword(dialectProperties.getPassword());
            dataSourceDataService.create(dataSourceCreateParam);

            // 创建
            ConsoleCreateParam consoleCreateParam = new ConsoleCreateParam();
            consoleCreateParam.setDataSourceId(dataSourceId);
            consoleCreateParam.setConsoleId(consoleId);
            consoleCreateParam.setDatabaseName(dialectProperties.getDatabaseName());
            ActionResult actionResult = consoleDataService.create(consoleCreateParam);
            Assertions.assertTrue(actionResult.success(), "创建控制台失败");
            Assertions.assertTrue(DataCenterUtils.JDBC_TEMPLATE_CACHE.get(dataSourceId).containsKey(consoleId),
                "创建控制台失败");

            // 关闭
            ConsoleCloseParam consoleCloseParam = new ConsoleCloseParam();
            consoleCloseParam.setDataSourceId(dataSourceId);
            consoleCloseParam.setConsoleId(consoleId);
            actionResult = consoleDataService.close(consoleCloseParam);
            Assertions.assertTrue(actionResult.success(), "创建控制台失败");
            Assertions.assertFalse(DataCenterUtils.JDBC_TEMPLATE_CACHE.get(dataSourceId).containsKey(consoleId),
                "创建控制台失败");
        }
    }

    @Test
    @Order(2)
    public void createAfterDataSourceClose() {
        for (DialectProperties dialectProperties : dialectPropertiesList) {
            DbTypeEnum dbTypeEnum = dialectProperties.getDbType();
            Long dataSourceId = TestUtils.nextLong();
            Long consoleId = TestUtils.nextLong();

            DataSourceCreateParam dataSourceCreateParam = new DataSourceCreateParam();
            dataSourceCreateParam.setDataSourceId(dataSourceId);
            dataSourceCreateParam.setDbType(dbTypeEnum.getCode());
            dataSourceCreateParam.setUrl(dialectProperties.getUrl());
            dataSourceCreateParam.setUsername(dialectProperties.getUsername());
            dataSourceCreateParam.setPassword(dialectProperties.getPassword());
            dataSourceDataService.create(dataSourceCreateParam);

            DataSourceCloseParam dataSourceCloseParam = new DataSourceCloseParam();
            dataSourceCloseParam.setDataSourceId(dataSourceId);
            dataSourceDataService.close(dataSourceCloseParam);

            // 无法执行sql了
            BusinessException businessException = Assertions.assertThrows(BusinessException.class, () -> {
                // 无法创建控制台
                // 创建控制台
                ConsoleCreateParam consoleCreateParam = new ConsoleCreateParam();
                consoleCreateParam.setDataSourceId(dataSourceId);
                consoleCreateParam.setConsoleId(consoleId);
                consoleCreateParam.setDatabaseName(dialectProperties.getDatabaseName());
                consoleDataService.create(consoleCreateParam);
            }, "关闭连接池失败");
            Assertions.assertEquals(ErrorEnum.DATA_SOURCE_NOT_FOUND.getCode(), businessException.getCode(),
                "关闭连接池失败");
        }
    }

    @Test
    @Order(3)
    public void closeDataSourceAfterCreateConsole() {
        for (DialectProperties dialectProperties : dialectPropertiesList) {
            DbTypeEnum dbTypeEnum = dialectProperties.getDbType();
            Long dataSourceId = TestUtils.nextLong();
            Long consoleId = TestUtils.nextLong();

            DataSourceCreateParam dataSourceCreateParam = new DataSourceCreateParam();
            dataSourceCreateParam.setDataSourceId(dataSourceId);
            dataSourceCreateParam.setDbType(dbTypeEnum.getCode());
            dataSourceCreateParam.setUrl(dialectProperties.getUrl());
            dataSourceCreateParam.setUsername(dialectProperties.getUsername());
            dataSourceCreateParam.setPassword(dialectProperties.getPassword());
            dataSourceDataService.create(dataSourceCreateParam);

            // 创建控制台
            ConsoleCreateParam consoleCreateParam = new ConsoleCreateParam();
            consoleCreateParam.setDataSourceId(dataSourceId);
            consoleCreateParam.setConsoleId(consoleId);
            consoleCreateParam.setDatabaseName(dialectProperties.getDatabaseName());
            consoleDataService.create(consoleCreateParam);

            DataSourceCloseParam dataSourceCloseParam = new DataSourceCloseParam();
            dataSourceCloseParam.setDataSourceId(dataSourceId);
            ActionResult actionResult = dataSourceDataService.close(dataSourceCloseParam);
            Assertions.assertTrue(actionResult.success(), "关闭数据库连接池失败");
            Assertions.assertFalse(DataCenterUtils.DATA_SOURCE_CACHE.containsKey(dataSourceId), "关闭连接池失败");
        }
    }

}
