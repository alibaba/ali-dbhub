package com.alibaba.dbhub.server.domain.core.api.param;

import com.alibaba.dbhub.server.tools.base.wrapper.param.PageQueryParam;

import lombok.Data;

/**
 * @author moji
 * @version UserSavedDdlPageQueryParam.java, v 0.1 2022年09月25日 14:05 moji Exp $
 * @date 2022/09/25
 */
@Data
public class UserSavedDdlPageQueryParam extends PageQueryParam {

    /**
     * 数据源连接ID
     */
    private Long dataSourceId;

    /**
     * db名称
     */
    private String databaseName;

    /**
     * ddl语句状态:DRAFT/RELEASE
     */
    private String status;

    /**
     * 搜索关键词
     */
    private String searchKey;
}
