package com.alibaba.dbhub.server.test.domain.data.service;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dbhub.server.domain.support.operations.ExampleOperations;
import com.alibaba.dbhub.server.test.common.BaseTest;
import com.alibaba.dbhub.server.test.domain.data.service.dialect.DialectProperties;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 样例
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class ExampleOperationsTest extends BaseTest {

    @Resource
    private ExampleOperations exampleOperations;
    @Autowired
    private List<DialectProperties> dialectPropertiesList;

    @Test
    @Order(1)
    public void example() {
        for (DialectProperties dialectProperties : dialectPropertiesList) {
            String createTable = exampleOperations.createTable(dialectProperties.getDbType());
            log.info("返回建表语句:{}", createTable);
            Assertions.assertNotNull(createTable, "查询样例失败");
            String alterTable = exampleOperations.alterTable(dialectProperties.getDbType());
            log.info("返回建修改表语句:{}", alterTable);
            Assertions.assertNotNull(alterTable, "查询样例失败");
        }
    }

}
