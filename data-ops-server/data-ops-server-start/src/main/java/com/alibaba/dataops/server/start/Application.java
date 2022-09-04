package com.alibaba.dataops.server.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author Jiaju Zhuang
 */
@SpringBootApplication(scanBasePackages = {"com.alibaba.dataops.server"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
