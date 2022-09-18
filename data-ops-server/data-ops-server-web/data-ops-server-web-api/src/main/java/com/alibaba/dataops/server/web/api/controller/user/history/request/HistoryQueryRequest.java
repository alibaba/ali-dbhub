package com.alibaba.dataops.server.web.api.controller.user.history.request;

import com.alibaba.dataops.server.tools.base.wrapper.request.PageQueryRequest;

import lombok.Data;

/**
 * @author moji
 * @version DdlCreateRequest.java, v 0.1 2022年09月18日 11:13 moji Exp $
 * @date 2022/09/18
 */
@Data
public class HistoryQueryRequest extends PageQueryRequest {

    /**
     * 模糊词搜索
     */
    private String searchKey;
}
