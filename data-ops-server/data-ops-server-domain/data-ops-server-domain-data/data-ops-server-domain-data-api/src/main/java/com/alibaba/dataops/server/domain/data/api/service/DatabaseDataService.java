package com.alibaba.dataops.server.domain.data.api.service;

import com.alibaba.dataops.server.domain.data.api.model.DatabaseDTO;
import com.alibaba.dataops.server.domain.data.api.param.database.DatabaseQueryAllParam;
import com.alibaba.dataops.server.tools.base.wrapper.result.ListResult;

/**
 * 数据库连接源服务
 *
 * @author Jiaju Zhuang
 */
public interface DatabaseDataService {

    /**
     * 查询全部数据库
     *
     * @param param
     * @return
     */
    ListResult<DatabaseDTO> queryAll(DatabaseQueryAllParam param);

}
