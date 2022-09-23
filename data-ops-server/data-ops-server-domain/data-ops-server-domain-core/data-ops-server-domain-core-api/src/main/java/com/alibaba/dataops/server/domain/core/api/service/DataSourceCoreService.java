package com.alibaba.dataops.server.domain.core.api.service;

import com.alibaba.dataops.server.domain.core.api.model.DataSourceDTO;
import com.alibaba.dataops.server.domain.core.api.test.DataSourceCreateParam;
import com.alibaba.dataops.server.domain.core.api.test.DataSourcePageQueryParam;
import com.alibaba.dataops.server.domain.core.api.test.DataSourceUpdateParam;
import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.PageResult;

/**
 * 数据源管理服务
 *
 * @author moji
 * @version DataSourceCoreService.java, v 0.1 2022年09月23日 15:22 moji Exp $
 * @date 2022/09/23
 */
public interface DataSourceCoreService {

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
     * 分页查询数据源列表
     *
     * @param param
     * @return
     */
    PageResult<DataSourceDTO> queryPage(DataSourcePageQueryParam param);
}
