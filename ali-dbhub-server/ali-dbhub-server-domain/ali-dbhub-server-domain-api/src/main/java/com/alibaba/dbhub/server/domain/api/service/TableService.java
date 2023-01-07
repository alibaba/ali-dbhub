package com.alibaba.dbhub.server.domain.api.service;

import com.alibaba.dbhub.server.domain.support.model.Sql;
import com.alibaba.dbhub.server.domain.support.model.Table;
import com.alibaba.dbhub.server.domain.support.param.table.DropParam;
import com.alibaba.dbhub.server.domain.support.param.table.ShowCreateTableParam;
import com.alibaba.dbhub.server.domain.support.param.table.TablePageQueryParam;
import com.alibaba.dbhub.server.domain.support.param.table.TableQueryParam;
import com.alibaba.dbhub.server.domain.support.param.table.TableSelector;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ListResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.PageResult;

/**
 * 数据源管理服务
 *
 * @author moji
 * @version DataSourceCoreService.java, v 0.1 2022年09月23日 15:22 moji Exp $
 * @date 2022/09/23
 */
public interface TableService {

    /**
     * 查询表信息
     *
     * @param param
     * @return
     */
    DataResult<String> showCreateTable(ShowCreateTableParam param);

    /**
     * 删除表
     *
     * @param param
     * @return
     */
    ActionResult drop(DropParam param);

    /**
     * 创建表结构的样例
     *
     * @param dbType
     * @return
     */
    DataResult<String> createTableExample(String dbType);

    /**
     * 修改表结构的样例
     *
     * @param dbType
     * @return
     */
    DataResult<String> alterTableExample(String dbType);

    /**
     * 查询表信息
     *
     * @param param
     * @return
     */
    DataResult<Table> query(TableQueryParam param, TableSelector selector);

    /**
     * 构建sql
     *
     * @param oldTable
     * @param newTable
     * @return
     */
    ListResult<Sql> buildSql(Table oldTable, Table newTable);

    /**
     * 分页查询表信息
     *
     * @param param
     * @return
     */
    PageResult<Table> pageQuery(TablePageQueryParam param, TableSelector selector);
}
