package com.alibaba.dbhub.server.domain.core.api.service;

import javax.validation.constraints.NotNull;

import com.alibaba.dbhub.server.domain.core.api.model.DataSourceDTO;
import com.alibaba.dbhub.server.domain.core.api.param.ConsoleConnectParam;
import com.alibaba.dbhub.server.domain.core.api.param.DataSourceManageCreateParam;
import com.alibaba.dbhub.server.domain.core.api.param.DataSourceExecuteParam;
import com.alibaba.dbhub.server.domain.core.api.param.DataSourcePageQueryParam;
import com.alibaba.dbhub.server.domain.core.api.param.DataSourceSelector;
import com.alibaba.dbhub.server.domain.core.api.param.DataSourceTestParam;
import com.alibaba.dbhub.server.domain.core.api.param.DataSourceUpdateParam;
import com.alibaba.dbhub.server.domain.data.api.model.DatabaseDTO;
import com.alibaba.dbhub.server.domain.data.api.model.ExecuteResultDTO;
import com.alibaba.dbhub.server.domain.data.api.model.TableDTO;
import com.alibaba.dbhub.server.domain.data.api.param.console.ConsoleCloseParam;
import com.alibaba.dbhub.server.domain.data.api.param.table.TablePageQueryParam;
import com.alibaba.dbhub.server.domain.data.api.param.table.TableQueryParam;
import com.alibaba.dbhub.server.domain.data.api.param.table.TableSelector;
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
public interface DataSourceCoreService {

    /**
     * 创建数据源连接
     *
     * @param param
     * @return
     */
    DataResult<Long> create(DataSourceManageCreateParam param);

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
     * 根据id查询数据源连接详情
     *
     * @param id
     * @return
     */
    DataResult<DataSourceDTO> queryById(@NotNull Long id);

    /**
     * 克隆连接
     *
     * @param id
     * @return
     */
    DataResult<Long> copyById(@NotNull Long id);

    /**
     * 分页查询数据源列表
     *
     * @param param
     * @param selector
     * @return
     */
    PageResult<DataSourceDTO> queryPage(DataSourcePageQueryParam param, DataSourceSelector selector);

    /**
     * 测试连接
     *
     * @param param
     * @return
     */
    ActionResult test(DataSourceTestParam param);

    /**
     * 连接数据源
     *
     * @param id
     * @return
     */
    ListResult<DatabaseDTO> attach(Long id);

    /**
     * 关闭数据源连接
     *
     * @param id
     * @return
     */
    ActionResult close(Long id);

    /**
     * 创建console链接
     *
     * @param param
     * @return
     */
    ActionResult createConsole(ConsoleConnectParam param);

    /**
     * 关闭连接
     *
     * @param param
     * @return
     */
    ActionResult closeConsole(ConsoleCloseParam param);

    /**
     * 数据源执行
     *
     * @param param
     * @return
     */
    ListResult<ExecuteResultDTO> execute(DataSourceExecuteParam param);

    /**
     * 查询表信息
     *
     * @param param
     * @return
     */
    DataResult<TableDTO> query(TableQueryParam param, TableSelector selector);

    /**
     * 分页查询表信息
     *
     * @param param
     * @return
     */
    PageResult<TableDTO> pageQuery(TablePageQueryParam param, TableSelector selector);
}
