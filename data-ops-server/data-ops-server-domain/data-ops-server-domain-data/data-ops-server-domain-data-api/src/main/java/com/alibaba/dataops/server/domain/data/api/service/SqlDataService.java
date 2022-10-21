package com.alibaba.dataops.server.domain.data.api.service;

import com.alibaba.dataops.server.domain.data.api.model.SqlDTO;
import com.alibaba.dataops.server.domain.data.api.param.sql.SqlAnalyseParam;
import com.alibaba.dataops.server.tools.base.wrapper.result.ListResult;

/**
 * Sql服务
 *
 * @author Jiaju Zhuang
 */
public interface SqlDataService {

    /**
     * 将一个复杂的sql 解析成多个简单的sql
     *
     * @param param
     * @return
     */
    ListResult<SqlDTO> analyse(SqlAnalyseParam param);
}
