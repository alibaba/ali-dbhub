package com.alibaba.dbhub.server.domain.api.model;

import lombok.Data;

/**
 * @author moji
 * @version ChatGptConfig.java, v 0.1 2023年05月09日 13:47 moji Exp $
 * @date 2023/05/09
 */
@Data
public class ChatGptConfig {

    /**
     * OpenAi APIKEY
     */
    private String apiKey;

    /**
     * http代理Host
     */
    private String httpProxyHost;

    /**
     * http代理Port
     */
    private String httpProxyPort;
}
