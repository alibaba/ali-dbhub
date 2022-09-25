package com.alibaba.dataops.server.domain.data.api.service;

import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceCloseParam;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceCreateParam;
import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;

/**
 * 数据库连接源服务
 *
 * @author Jiaju Zhuang
 */
public interface DataSourceDataService {

    /**
     * 创建数据库连接源
     *
     * @param param
     * @return
     */
    ActionResult create(DataSourceCreateParam param);

    /**
     * 创建数据库连接源
     *
     * @param param
     * @return
     */
    ActionResult close(DataSourceCloseParam param);

}
