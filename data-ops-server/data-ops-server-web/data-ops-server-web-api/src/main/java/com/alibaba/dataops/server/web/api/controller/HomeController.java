/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dataops.server.web.api.controller;

import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jipengfei
 * @version : HomeController.java, v 0.1 2022年09月18日 14:52 jipengfei Exp $
 */
@RestController
@RequestMapping("/")
@Slf4j
public class HomeController {
    @GetMapping
    public String get() {
        return "success";
    }
}