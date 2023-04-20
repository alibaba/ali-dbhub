/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alibaba.dbhub.server.web.api.controller.config;

import com.alibaba.dbhub.server.domain.api.model.Config;
import com.alibaba.dbhub.server.domain.api.param.DatabaseOperationParam;
import com.alibaba.dbhub.server.domain.api.param.SystemConfigParam;
import com.alibaba.dbhub.server.domain.api.service.ConfigService;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dbhub.server.web.api.aspect.BusinessExceptionAspect;
import com.alibaba.dbhub.server.web.api.aspect.ConnectionInfoAspect;
import com.alibaba.dbhub.server.web.api.controller.config.request.SystemConfigRequest;
import com.alibaba.dbhub.server.web.api.controller.data.source.request.DataSourceBaseRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jipengfei
 * @version : ConfigController.java
 */
@BusinessExceptionAspect
@ConnectionInfoAspect
@RequestMapping("/api/config")
@RestController
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @GetMapping("/system_config")
    public ActionResult systemConfig(@RequestBody SystemConfigRequest request) {
        SystemConfigParam param = SystemConfigParam.builder().code(request.getCode()).content(request.getContent())
            .build();
        return configService.createOrUpdate(param);
    }

    @GetMapping("/system_config/{code}")
    public DataResult<Config> getSystemConfig(@PathVariable("code") String code) {
        DataResult<Config> result = configService.find(code);
        return DataResult.of(result.getData());
    }
}