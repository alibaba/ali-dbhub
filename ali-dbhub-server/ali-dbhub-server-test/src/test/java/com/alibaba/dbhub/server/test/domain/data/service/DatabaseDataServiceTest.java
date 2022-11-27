package com.alibaba.dbhub.server.test.domain.data.service;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dbhub.server.domain.data.api.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.data.api.model.DatabaseDTO;
import com.alibaba.dbhub.server.domain.data.api.param.database.DatabaseQueryAllParam;
import com.alibaba.dbhub.server.domain.data.api.param.datasource.DataSourceCreateParam;
import com.alibaba.dbhub.server.domain.data.api.service.DataSourceDataService;
import com.alibaba.dbhub.server.domain.data.api.service.DatabaseDataService;
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
 * 数据库测试
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class DatabaseDataServiceTest extends BaseTest {
    @Resource
    private DataSourceDataService dataSourceDataService;
    @Autowired
    private List<DialectProperties> dialectPropertiesList;
    @Resource
    private DatabaseDataService databaseDataService;

    @Test
    @Order(1)
    public void queryAll() {
        for (DialectProperties dialectProperties : dialectPropertiesList) {
            DbTypeEnum dbTypeEnum = dialectProperties.getDbType();
            Long dataSourceId = TestUtils.nextLong();

            DataSourceCreateParam dataSourceCreateParam = new DataSourceCreateParam();
            dataSourceCreateParam.setDataSourceId(dataSourceId);
            dataSourceCreateParam.setDbType(dbTypeEnum.getCode());
            dataSourceCreateParam.setUrl(dialectProperties.getUrl());
            dataSourceCreateParam.setUsername(dialectProperties.getUsername());
            dataSourceCreateParam.setPassword(dialectProperties.getPassword());
            dataSourceDataService.create(dataSourceCreateParam);

            DatabaseQueryAllParam databaseQueryAllParam = new DatabaseQueryAllParam();
            databaseQueryAllParam.setDataSourceId(dataSourceId);
            List<DatabaseDTO> databaseList = databaseDataService.queryAll(databaseQueryAllParam).getData();
            log.info("查询数据库返回:{}", JSON.toJSONString(databaseList));

            DatabaseDTO databaseDTO = databaseList.stream()
                .filter(database -> dialectProperties.getDatabaseName().equals(database.getName()))
                .findFirst()
                .orElse(null);
            Assertions.assertNotNull(databaseDTO, "查询数据库失败");
        }
    }

}
