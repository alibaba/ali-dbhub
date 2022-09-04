package com.alibaba.ctoo.opensource;

import com.alibaba.dataops.server.start.Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * 本地环境的启动。
 * 主要为了读取本地的一些配置 比如日志输出就和其他环境不一样
 */
@SpringBootApplication
@ComponentScan(
    value = {"com.alibaba.dataops.server"},
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.alibaba.dataops.server.start.Application")
    })
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
