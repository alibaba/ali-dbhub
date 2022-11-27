package com.alibaba.dbhub.server.domain.data.api.service;

import com.alibaba.dbhub.server.domain.data.api.model.DataSourceConnectDTO;
import com.alibaba.dbhub.server.domain.data.api.param.datasource.DataSourceCloseParam;
import com.alibaba.dbhub.server.domain.data.api.param.datasource.DataSourceCreateParam;
import com.alibaba.dbhub.server.domain.data.api.param.datasource.DataSourceTestParam;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.DataResult;

/**
 * 数据库连接源服务
 *
 * @author Jiaju Zhuang
 */
public interface DataSourceDataService {

    /**
     * 测试数据库连接源
     *
     * @param param
     * @return
     */
    DataResult<DataSourceConnectDTO> test(DataSourceTestParam param);

    /**
     * 创建数据库连接源
     *
     * @param param
     * @return
     */
    DataResult<DataSourceConnectDTO> create(DataSourceCreateParam param);

    /**
     * 创建数据库连接源
     *
     * @param param
     * @return
     */
    ActionResult close(DataSourceCloseParam param);

}
