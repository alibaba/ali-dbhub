package com.alibaba.dbhub.server.domain.support.operations;

import com.alibaba.dbhub.server.domain.support.model.DataSourceConnect;
import com.alibaba.dbhub.server.domain.support.param.datasource.DataSourceCloseParam;
import com.alibaba.dbhub.server.domain.support.param.datasource.DataSourceCreateParam;
import com.alibaba.dbhub.server.domain.support.param.datasource.DataSourceTestParam;

/**
 * 数据库连接源服务
 *
 * @author Jiaju Zhuang
 */
public interface DataSourceOperations {

    /**
     * 测试数据库连接源
     *
     * @param param
     * @return
     */
    DataSourceConnect test(DataSourceTestParam param);

    /**
     * 创建数据库连接源
     *
     * @param param
     * @return
     */
    DataSourceConnect create(DataSourceCreateParam param);

    /**
     * 创建数据库连接源
     *
     * @param param
     * @return
     */
    void close(DataSourceCloseParam param);

}
