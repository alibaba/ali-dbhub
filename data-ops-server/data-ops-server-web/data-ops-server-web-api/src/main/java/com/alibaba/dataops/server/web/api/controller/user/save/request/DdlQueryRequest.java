package com.alibaba.dataops.server.web.api.controller.user.save.request;

import javax.validation.constraints.NotNull;

import com.alibaba.dataops.server.tools.base.wrapper.request.PageQueryRequest;

import lombok.Data;

/**
 * @author moji
 * @version DdlCreateRequest.java, v 0.1 2022年09月18日 11:13 moji Exp $
 * @date 2022/09/18
 */
@Data
public class DdlQueryRequest extends PageQueryRequest {

    /**
     * 数据源id
     */
    private Long dataSourceId;

    /**
     * DB名称
     */
    private String databaseName;

    /**
     * 模糊搜索词
     */
    private String searchKey;
}
