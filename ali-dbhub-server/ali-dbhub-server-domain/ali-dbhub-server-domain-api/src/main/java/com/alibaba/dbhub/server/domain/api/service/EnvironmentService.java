package com.alibaba.dbhub.server.domain.api.service;

import javax.validation.constraints.NotNull;

import com.alibaba.dbhub.server.domain.api.model.Environment;
import com.alibaba.dbhub.server.domain.api.param.DataSourceCreateParam;
import com.alibaba.dbhub.server.domain.api.param.DataSourceUpdateParam;
import com.alibaba.dbhub.server.domain.api.param.environment.EnvironmentQueryParam;
import com.alibaba.dbhub.server.domain.api.param.environment.EnvironmentSelector;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ListResult;

/**
 * 环境服务
 *
 * @author 是仪
 */
public interface EnvironmentService {

    /**
     * 创建数据源连接
     *
     * @param param
     * @return
     */
    DataResult<Long> create(DataSourceCreateParam param);

    /**
     * 更新数据源连接
     *
     * @param param
     * @return
     */
    ActionResult update(DataSourceUpdateParam param);

    /**
     * 删除数据源连接
     *
     * @param id
     * @return
     */
    ActionResult delete(@NotNull Long id);

    /**
     * 查询列表
     *
     * @param param
     * @return
     */
    ListResult<Environment> queryList(EnvironmentQueryParam param, EnvironmentSelector selector);
}
