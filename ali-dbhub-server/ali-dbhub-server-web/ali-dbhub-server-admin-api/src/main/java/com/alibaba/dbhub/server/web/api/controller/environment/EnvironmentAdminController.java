/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alibaba.dbhub.server.web.api.controller.environment;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.alibaba.dbhub.server.domain.api.service.EnvironmentService;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ListResult;
import com.alibaba.dbhub.server.web.api.controller.environment.converter.EnvironmentAdminConverter;
import com.alibaba.dbhub.server.web.api.controller.environment.request.EnvironmentCreateRequest;
import com.alibaba.dbhub.server.web.api.controller.environment.request.EnvironmentListRequest;
import com.alibaba.dbhub.server.web.api.controller.environment.request.EnvironmentUpdateRequest;
import com.alibaba.dbhub.server.web.api.controller.environment.vo.EnvironmentGetVO;
import com.alibaba.dbhub.server.web.api.controller.environment.vo.EnvironmentListVO;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理系统/环境管理
 *
 * @author Jiaju Zhuang
 */
@RequestMapping("/api/admin/environment")
@RestController
public class EnvironmentAdminController {

    @Resource
    private EnvironmentService environmentService;
    @Resource
    private EnvironmentAdminConverter environmentAdminConverter;

    /**
     * 返回全部环境列表
     * 不分页
     * <h4>1.1.0</h4>
     * <ul>
     *     <li>新增接口</li>
     * </ul>
     *
     * @param request
     * @return
     * @version 1.1.0
     */
    @GetMapping("/list")
    public ListResult<EnvironmentListVO> list(EnvironmentListRequest request) {
        return environmentService.queryList(environmentAdminConverter.request2param(request), null)
            .map(environmentAdminConverter::dto2vo);
    }

    /**
     * 环境详情
     * <h4>1.1.0</h4>
     * <ul>
     *     <li>新增接口</li>
     * </ul>
     *
     * @param id
     * @return
     * @version 1.1.0
     */
    @GetMapping("{id}")
    public DataResult<EnvironmentGetVO> get(@PathVariable("id") Long id) {
        return null;
    }

    /**
     * 新建环境
     * <h4>1.1.0</h4>
     * <ul>
     *     <li>新增接口</li>
     * </ul>
     *
     * @param request
     * @return
     * @version 1.1.0
     */
    @PostMapping
    public DataResult<Long> create(@Valid @RequestBody EnvironmentCreateRequest request) {
        return null;
    }

    /**
     * 修改环境
     * <h4>1.1.0</h4>
     * <ul>
     *     <li>新增接口</li>
     * </ul>
     *
     * @param request
     * @return
     * @version 1.1.0
     */
    @PutMapping
    public ActionResult update(@Valid @RequestBody EnvironmentUpdateRequest request) {
        return null;
    }

    /**
     * 删除环境
     * <h4>1.1.0</h4>
     * <ul>
     *     <li>新增接口</li>
     * </ul>
     *
     * @param id
     * @return
     * @version 1.1.0
     */
    @DeleteMapping("{id}")
    public ActionResult delete(@PathVariable("id") Long id) {
        return null;
    }

}