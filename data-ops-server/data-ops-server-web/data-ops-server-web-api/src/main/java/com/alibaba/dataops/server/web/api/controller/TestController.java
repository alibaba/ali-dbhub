package com.alibaba.dataops.server.web.api.controller;

import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试服务类
 *
 * @author Jiaju Zhuang
 */
@Slf4j
@RestController("test")
public class TestController {

    @GetMapping
    public ActionResult test() {
        log.info("接收到请求");
        return ActionResult.isSuccess();
    }
}
