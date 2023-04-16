/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alibaba.dbhub.server.web.api.controller.environment;

import javax.annotation.Resource;

import com.alibaba.dbhub.server.domain.api.service.EnvironmentService;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ListResult;
import com.alibaba.dbhub.server.web.api.controller.environment.converter.EnvironmentAdminConverter;
import com.alibaba.dbhub.server.web.api.controller.environment.request.EnvironmentListRequest;
import com.alibaba.dbhub.server.web.api.controller.environment.vo.EnvironmentListVO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 环境管理
 *
 * @author 是仪
 */
@RequestMapping("/api/admin/environment")
@RestController
public class EnvironmentController {

    @Resource
    private EnvironmentService environmentService;
    @Resource
    private EnvironmentAdminConverter environmentAdminConverter;

    /**
     * 返回全部环境列表
     * 不分页
     *
     * @param request
     * @return
     */
    @GetMapping("/list")
    public ListResult<EnvironmentListVO> list(EnvironmentListRequest request) {
        return environmentService.queryList(environmentAdminConverter.request2param(request), null)
            .map(environmentAdminConverter::dto2vo);
    }
}