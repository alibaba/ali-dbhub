package com.alibaba.dataops.server.web.api.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.alibaba.dataops.server.domain.core.api.service.TestCoreService;
import com.alibaba.dataops.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dataops.server.web.api.converter.TestWebApiConverter;
import com.alibaba.dataops.server.web.api.request.test.TestCreateRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Resource
    private TestCoreService testCoreService;
    @Resource
    private TestWebApiConverter testWebApiConverter;

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
    @PostMapping
    public DataResult<Long> create(@Valid TestCreateRequest request) {
        return testCoreService.create(testWebApiConverter.request2Param(request));
    }
}
