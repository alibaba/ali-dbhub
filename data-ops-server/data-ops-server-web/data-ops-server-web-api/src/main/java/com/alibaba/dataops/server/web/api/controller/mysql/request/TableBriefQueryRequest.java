package com.alibaba.dataops.server.web.api.controller.mysql.request;

import javax.validation.constraints.NotNull;

import com.alibaba.dataops.server.tools.base.wrapper.request.PageQueryRequest;

import lombok.Data;

/**
 * @author moji
 * @version ConnectionQueryRequest.java, v 0.1 2022年09月16日 14:23 moji Exp $
 * @date 2022/09/16
 */
@Data
public class TableBriefQueryRequest extends PageQueryRequest {

    /**
     * 数据源id
     */
    @NotNull
    private Long dataSourceId;

    /**
     * DB名称
     */
    @NotNull
    private String databaseName;

    /**
     * 模糊搜索词
     */
    private String searchKey;
}
