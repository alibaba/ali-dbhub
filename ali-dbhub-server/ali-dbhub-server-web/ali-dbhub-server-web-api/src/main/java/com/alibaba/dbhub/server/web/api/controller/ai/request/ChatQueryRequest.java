package com.alibaba.dbhub.server.web.api.controller.ai.request;

import com.alibaba.dbhub.server.web.api.controller.data.source.request.DataSourceBaseRequest;

import lombok.Data;

/**
 * chat查询入参
 *
 * @author moji
 * @version ChatQueryRequest.java, v 0.1 2023年04月02日 13:28 moji Exp $
 * @date 2023/04/02
 */
@Data
public class ChatQueryRequest extends DataSourceBaseRequest {

    /**
     * 输入消息
     */
    private String message;
}
