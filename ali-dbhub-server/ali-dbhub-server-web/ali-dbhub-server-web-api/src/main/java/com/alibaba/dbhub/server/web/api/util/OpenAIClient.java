/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alibaba.dbhub.server.web.api.util;

import com.alibaba.dbhub.server.domain.api.model.Config;
import com.alibaba.dbhub.server.domain.api.service.ConfigService;

import com.google.common.collect.Lists;
import com.unfbx.chatgpt.OpenAiStreamClient;
import com.unfbx.chatgpt.constant.OpenAIConst;

/**
 * @author jipengfei
 * @version : OpenAIClient.java
 */
public class OpenAIClient {

    public static final String OPENAI_KEY = "chatgpt.apiKey";

    private static OpenAiStreamClient OPEN_AI_STREAM_CLIENT;
    private static String apiKey;

    public static OpenAiStreamClient getInstance() {
        if (OPEN_AI_STREAM_CLIENT != null) {
            return OPEN_AI_STREAM_CLIENT;
        } else {
            return singleton();
        }
    }

    private static OpenAiStreamClient singleton() {
        if (OPEN_AI_STREAM_CLIENT == null) {
            synchronized (OpenAIClient.class) {
                if (OPEN_AI_STREAM_CLIENT == null) {
                    refresh();
                }
            }
        }
        return OPEN_AI_STREAM_CLIENT;
    }

    public static void refresh() {
        String apikey = null;
        ConfigService configService = ApplicationContextUtil.getBean(ConfigService.class);
        Config config = configService.find(OPENAI_KEY).getData();
        if (config != null) {
            apikey = config.getContent();
        } else {
            apikey = ApplicationContextUtil.getProperty(OPENAI_KEY);
        }
        OPEN_AI_STREAM_CLIENT = OpenAiStreamClient.builder().apiHost(OpenAIConst.OPENAI_HOST).apiKey(
            Lists.newArrayList(apikey)).build();
        apiKey = apikey;
    }
}