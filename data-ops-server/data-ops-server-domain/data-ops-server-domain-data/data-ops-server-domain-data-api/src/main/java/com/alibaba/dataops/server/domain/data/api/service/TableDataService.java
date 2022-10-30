package com.alibaba.dataops.server.domain.data.api.service;

import com.alibaba.dataops.server.domain.data.api.model.TableDTO;
import com.alibaba.dataops.server.domain.data.api.param.table.TablePageQueryParam;
import com.alibaba.dataops.server.domain.data.api.param.table.TableQueryParam;
import com.alibaba.dataops.server.domain.data.api.param.table.TableSelector;
import com.alibaba.dataops.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.PageResult;

/**
 * 表结构服务
 *
 * @author Jiaju Zhuang
 */
public interface TableDataService {

    /**
     * 查询表信息
     *
     * @param param
     * @param selector
     * @return
     */
    DataResult<TableDTO> query(TableQueryParam param, TableSelector selector);

    /**
     * 分页查询表信息
     *
     * @param param
     * @param selector
     * @return
     */
    PageResult<TableDTO> pageQuery(TablePageQueryParam param, TableSelector selector);
}
