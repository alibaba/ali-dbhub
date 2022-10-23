package com.alibaba.dataops.server.domain.data.api.service;

import com.alibaba.dataops.server.domain.data.api.model.DatabaseDTO;
import com.alibaba.dataops.server.domain.data.api.model.TableBriefDTO;
import com.alibaba.dataops.server.domain.data.api.model.TableDTO;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceCloseParam;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceCreateParam;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceDescTableParam;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceShowCreateTableParam;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceShowDatabasesParam;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceShowTablesParam;
import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.ListResult;

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

    /**
     * 查询数据库列表
     *
     * @param param
     * @return
     */
    ListResult<DatabaseDTO> showDatabases(DataSourceShowDatabasesParam param);

    /**
     * 查询表列表
     *
     * @param param
     * @return
     */
    ListResult<TableBriefDTO> showTables(DataSourceShowTablesParam param);

    /**
     * 查询表详情
     *
     * @param param
     * @return
     */
    DataResult<TableDTO> descTable(DataSourceDescTableParam param);

    /**
     * 查询建表语句
     *
     * @param param
     * @return
     */
    ActionResult showCreateTable(DataSourceShowCreateTableParam param);

}
