package com.alibaba.dataops.server.web.api.controller;

import javax.validation.Valid;

import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dataops.server.web.api.request.test.TestGetRequest;
import com.alibaba.fastjson2.JSON;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试服务类
 *
 * @author Jiaju Zhuang
 */
@Slf4j
@RestController("/api/test")
public class TestController {

    /**
     * 测试接口
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
    public ActionResult test(@Valid TestGetRequest request) {
        log.info("接收到请求{}", JSON.toJSONString(request));
        return ActionResult.isSuccess();
    }
}
