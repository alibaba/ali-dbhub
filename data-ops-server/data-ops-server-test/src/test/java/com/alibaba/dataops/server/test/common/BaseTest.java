package com.alibaba.dataops.server.test.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * 基础测试类
 *
 * @author Jiaju Zhuang
 **/
@SpringBootApplication
@ComponentScan(
    value = {"com.alibaba.dataops.server"},
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.alibaba.dataops.server.start.Application")
    })
@MapperScan("com.alibaba.dataops.server.domain.*.repository.mapper")
@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public abstract class BaseTest {

}
