package com.alibaba.dataops.server.domain.data.api.service;

import java.util.Map;

import com.alibaba.dataops.server.tools.base.wrapper.result.ListResult;

/**
 * jdbc服务
 *
 * @author Jiaju Zhuang
 */
public interface JdbcTemplateDataService {

    /**
     * 根据sql查询数据
     *
     * @param sql
     * @return
     */
    ListResult<Map<String, Object>> queryForList(String sql);

}
