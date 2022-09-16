package com.alibaba.dataops.server.web.api.controller.redis.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author moji
 * @version ConnectionQueryRequest.java, v 0.1 2022年09月16日 14:23 moji Exp $
 * @date 2022/09/16
 */
@Data
public class ValueUpdateRequest {

    /**
     * 连接id
     */
    @NotNull
    private Long connectionId;

    /**
     * DB名称
     */
    @NotNull
    private String databaseName;

    /**
     * key名称
     */
    @NotNull
    private String key;

    /**
     * 原始key值
     */
    @NotNull
    private Object originalValue;

    /**
     * 更新后key值
     */
    @NotNull
    private Object updateValue;

}
