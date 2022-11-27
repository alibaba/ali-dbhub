/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.web.api.controller;

import com.alibaba.dbhub.server.tools.common.config.AliDBHubProperties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jipengfei
 * @version : HomeController.java, v 0.1 2022年09月18日 14:52 jipengfei Exp $
 */
@RestController
@RequestMapping("/api/system")
@Slf4j
public class SystemController {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AliDBHubProperties AliDBHubProperties;

    /**
     * 检测是否成功
     *
     * @return
     */
    @GetMapping
    public String get() {
        return "success";
    }

    /**
     * 获取当前版本号
     *
     * @return
     */
    @GetMapping("/get_version")
    public String getVersion() {
        return AliDBHubProperties.getVersion();
    }

    /**
     * 退出服务
     */
    @PostMapping("/stop")
    public String stop() {
        SpringApplication.exit(applicationContext);
        return "ok";
    }
}
