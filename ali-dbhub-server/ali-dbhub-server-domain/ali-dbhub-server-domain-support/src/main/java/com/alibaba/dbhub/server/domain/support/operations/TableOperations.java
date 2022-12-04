package com.alibaba.dbhub.server.domain.support.operations;

import com.alibaba.dbhub.server.domain.support.model.Table;
import com.alibaba.dbhub.server.domain.support.param.table.ShowCreateTableParam;
import com.alibaba.dbhub.server.domain.support.param.table.TablePageQueryParam;
import com.alibaba.dbhub.server.domain.support.param.table.TableQueryParam;
import com.alibaba.dbhub.server.domain.support.param.table.TableSelector;
import com.alibaba.dbhub.server.tools.base.wrapper.result.PageResult;

/**
 * 表结构服务
 *
 * @author Jiaju Zhuang
 */
public interface TableOperations {

    /**
     * 查询表信息
     *
     * @param param
     * @return
     */
    String showCreateTable(ShowCreateTableParam param);

    /**
     * 查询表信息
     *
     * @param param
     * @param selector
     * @return
     */
    Table query(TableQueryParam param, TableSelector selector);

    /**
     * 分页查询表信息
     *
     * @param param
     * @param selector
     * @return
     */
    PageResult<Table> pageQuery(TablePageQueryParam param, TableSelector selector);
}
