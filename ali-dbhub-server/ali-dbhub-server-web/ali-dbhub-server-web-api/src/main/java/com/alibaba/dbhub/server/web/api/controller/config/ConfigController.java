/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alibaba.dbhub.server.web.api.controller.config;

import java.util.Objects;

import com.alibaba.dbhub.server.domain.api.model.ChatGptConfig;
import com.alibaba.dbhub.server.domain.api.model.Config;
import com.alibaba.dbhub.server.domain.api.param.DatabaseOperationParam;
import com.alibaba.dbhub.server.domain.api.param.SystemConfigParam;
import com.alibaba.dbhub.server.domain.api.service.ConfigService;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dbhub.server.web.api.aspect.BusinessExceptionAspect;
import com.alibaba.dbhub.server.web.api.aspect.ConnectionInfoAspect;
import com.alibaba.dbhub.server.web.api.controller.config.request.ChatGptSystemConfigRequest;
import com.alibaba.dbhub.server.web.api.controller.config.request.SystemConfigRequest;
import com.alibaba.dbhub.server.web.api.controller.data.source.request.DataSourceBaseRequest;
import com.alibaba.dbhub.server.web.api.util.OpenAIClient;

import org.apache.commons.lang3.StringUtils;
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

    @PostMapping("/system_config")
    public ActionResult systemConfig(@RequestBody SystemConfigRequest request) {
        SystemConfigParam param = SystemConfigParam.builder().code(request.getCode()).content(request.getContent())
            .build();
        configService.createOrUpdate(param);
        if (OpenAIClient.OPENAI_KEY.equals(request.getCode())) {
            OpenAIClient.refresh();
        }
        return ActionResult.isSuccess();
    }

    /**
     * 保存ChatGPT相关配置
     *
     * @param request
     * @return
     */
    @PostMapping("/system_config/chatgpt")
    public ActionResult addChatGptSystemConfig(@RequestBody ChatGptSystemConfigRequest request) {
        SystemConfigParam param = SystemConfigParam.builder().code(OpenAIClient.OPENAI_KEY).content(request.getApiKey())
            .build();
        configService.createOrUpdate(param);
        if (StringUtils.isNotBlank(request.getApiKey())) {
            OpenAIClient.refresh();
        }
        SystemConfigParam httpProxyHostParam = SystemConfigParam.builder().code(OpenAIClient.PROXY_HOST).content(
            request.getHttpProxyHost()).build();
        configService.createOrUpdate(httpProxyHostParam);
        SystemConfigParam httpProxyPortParam = SystemConfigParam.builder().code(OpenAIClient.PROXY_PORT).content(
                request.getHttpProxyPort()).build();
        configService.createOrUpdate(httpProxyPortParam);
        return ActionResult.isSuccess();
    }


    @GetMapping("/system_config/{code}")
    public DataResult<Config> getSystemConfig(@PathVariable("code") String code) {
        DataResult<Config> result = configService.find(code);
        return DataResult.of(result.getData());
    }

    /**
     * 查询ChatGPT相关配置
     *
     * @return
     */
    @GetMapping("/system_config/chatgpt")
    public DataResult<ChatGptConfig> getChatGptSystemConfig() {
        DataResult<Config> apiKey = configService.find(OpenAIClient.OPENAI_KEY);
        DataResult<Config> httpProxyHost = configService.find(OpenAIClient.PROXY_HOST);
        DataResult<Config> httpProxyPort = configService.find(OpenAIClient.PROXY_PORT);
        ChatGptConfig config = new ChatGptConfig();
        config.setApiKey(Objects.nonNull(apiKey.getData()) ? apiKey.getData().getContent() : null);
        config.setHttpProxyHost(Objects.nonNull(httpProxyHost.getData()) ? httpProxyHost.getData().getContent() : null);
        config.setHttpProxyPort(Objects.nonNull(httpProxyPort.getData()) ? httpProxyPort.getData().getContent() : null);
        return DataResult.of(config);
    }
}
