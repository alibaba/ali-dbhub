package com.alibaba.ctoo.opensource.test;

import com.alibaba.ctoo.opensource.TestApplication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 基础测试类
 *
 * @author 是仪
 **/
@SpringBootTest(classes = {TestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public abstract class BaseTest {

}
