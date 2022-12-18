package com.alibaba.dbhub.server.test.domain.data.service;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.model.DataSourceConnect;
import com.alibaba.dbhub.server.domain.support.operations.DataSourceOperations;
import com.alibaba.dbhub.server.domain.support.param.datasource.DataSourceCloseParam;
import com.alibaba.dbhub.server.domain.support.param.datasource.DataSourceCreateParam;
import com.alibaba.dbhub.server.domain.support.param.datasource.DataSourceTestParam;
import com.alibaba.dbhub.server.domain.support.util.DataCenterUtils;
import com.alibaba.dbhub.server.test.common.BaseTest;
import com.alibaba.dbhub.server.test.domain.data.service.dialect.DialectProperties;
import com.alibaba.dbhub.server.test.domain.data.utils.TestUtils;
import com.alibaba.fastjson2.JSON;

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
public class DataSourceOperationsTest extends BaseTest {
    @Resource
    private DataSourceOperations dataSourceOperations;
    @Autowired
    private List<DialectProperties> dialectPropertiesList;

    @Test
    @Order(1)
    public void createAndClose() {
        for (DialectProperties dialectProperties : dialectPropertiesList) {
            DbTypeEnum dbTypeEnum = dialectProperties.getDbType();
            Long dataSourceId = TestUtils.nextLong();

            // 创建
            DataSourceCreateParam dataSourceCreateParam = new DataSourceCreateParam();
            dataSourceCreateParam.setDataSourceId(dataSourceId);
            dataSourceCreateParam.setDbType(dbTypeEnum.getCode());
            dataSourceCreateParam.setUrl(dialectProperties.getUrl());
            dataSourceCreateParam.setUsername(dialectProperties.getUsername());
            dataSourceCreateParam.setPassword(dialectProperties.getPassword());
            DataSourceConnect dataSourceConnect = dataSourceOperations.create(dataSourceCreateParam);
            Assertions.assertTrue(dataSourceConnect.getSuccess(), "创建数据库连接池失败");
            Assertions.assertTrue(DataCenterUtils.JDBC_ACCESSOR_MAP.containsKey(dataSourceId), "创建数据库连接池失败");

            // 关闭
            DataSourceCloseParam dataSourceCloseParam = new DataSourceCloseParam();
            dataSourceCloseParam.setDataSourceId(dataSourceId);
            dataSourceOperations.close(dataSourceCloseParam);

            Assertions.assertFalse(DataCenterUtils.JDBC_ACCESSOR_MAP.containsKey(dataSourceId), "关闭连接池失败");
        }
    }

    @Test
    @Order(2)
    public void test() {
        for (DialectProperties dialectProperties : dialectPropertiesList) {
            DbTypeEnum dbTypeEnum = dialectProperties.getDbType();

            // 创建
            DataSourceTestParam dataSourceTestParam = new DataSourceTestParam();
            dataSourceTestParam.setDbType(dbTypeEnum.getCode());
            dataSourceTestParam.setUrl(dialectProperties.getErrorUrl());
            dataSourceTestParam.setUsername(dialectProperties.getUsername());
            dataSourceTestParam.setPassword(dialectProperties.getPassword());
            DataSourceConnect dataSourceConnect = dataSourceOperations.test(dataSourceTestParam);
            log.info("创建数据库返回:{}", JSON.toJSONString(dataSourceConnect));
            Assertions.assertFalse(dataSourceConnect.getSuccess(), "创建数据库失败错误");
            Assertions.assertNotNull(dataSourceConnect.getMessage(), "创建数据库失败错误");
        }
    }

}
