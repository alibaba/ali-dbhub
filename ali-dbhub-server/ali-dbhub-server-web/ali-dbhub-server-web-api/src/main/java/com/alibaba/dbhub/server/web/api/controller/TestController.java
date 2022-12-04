package com.alibaba.dbhub.server.web.api.controller;

import com.alibaba.dbhub.server.tools.base.wrapper.result.ActionResult;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试服务类
 *
 * @author Jiaju Zhuang
 */
@RestController
@RequestMapping("/api/test")
@Slf4j
public class TestController {

    /**
     * 创建测试
     *
     * 1.0.0
     * <ul>
     *     <li>新增接口</li>
     * </ul>
     *
     * @return 无
     * @tag 1.0.0
     */
    @GetMapping
    public ActionResult get() {
        return ActionResult.isSuccess();
    }
}
