package com.alibaba.dbhub.server.domain.core.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.dbhub.server.domain.support.model.DataSourceConnect;
import com.alibaba.dbhub.server.domain.support.model.Database;
import com.alibaba.dbhub.server.domain.support.model.ExecuteResult;
import com.alibaba.dbhub.server.domain.support.model.Sql;
import com.alibaba.dbhub.server.domain.support.model.Table;
import com.alibaba.dbhub.server.domain.support.operations.ConsoleOperations;
import com.alibaba.dbhub.server.domain.support.operations.DataSourceOperations;
import com.alibaba.dbhub.server.domain.support.operations.DatabaseOperations;
import com.alibaba.dbhub.server.domain.support.operations.JdbcOperations;
import com.alibaba.dbhub.server.domain.support.operations.SqlOperations;
import com.alibaba.dbhub.server.domain.support.operations.TableOperations;
import com.alibaba.dbhub.server.domain.support.param.console.ConsoleCloseParam;
import com.alibaba.dbhub.server.domain.support.param.console.ConsoleCreateParam;
import com.alibaba.dbhub.server.domain.support.param.database.DatabaseQueryAllParam;
import com.alibaba.dbhub.server.domain.support.param.datasource.DataSourceCloseParam;
import com.alibaba.dbhub.server.domain.support.param.datasource.DataSourceCreateParam;
import com.alibaba.dbhub.server.domain.support.param.sql.SqlAnalyseParam;
import com.alibaba.dbhub.server.domain.support.param.table.TablePageQueryParam;
import com.alibaba.dbhub.server.domain.support.param.table.TableQueryParam;
import com.alibaba.dbhub.server.domain.support.param.table.TableSelector;
import com.alibaba.dbhub.server.domain.support.param.template.TemplateExecuteParam;
import com.alibaba.dbhub.server.domain.api.model.DataSourceDTO;
import com.alibaba.dbhub.server.domain.api.param.ConsoleConnectParam;
import com.alibaba.dbhub.server.domain.api.param.DataSourceExecuteParam;
import com.alibaba.dbhub.server.domain.api.param.DataSourceManageCreateParam;
import com.alibaba.dbhub.server.domain.api.param.DataSourcePageQueryParam;
import com.alibaba.dbhub.server.domain.api.param.DataSourceSelector;
import com.alibaba.dbhub.server.domain.api.param.DataSourceTestParam;
import com.alibaba.dbhub.server.domain.api.param.DataSourceUpdateParam;
import com.alibaba.dbhub.server.domain.api.service.DataSourceCoreService;
import com.alibaba.dbhub.server.domain.core.converter.DataSourceCoreConverter;
import com.alibaba.dbhub.server.domain.repository.entity.DataSourceDO;
import com.alibaba.dbhub.server.domain.repository.mapper.DataSourceMapper;
import com.alibaba.dbhub.server.tools.base.excption.BusinessException;
import com.alibaba.dbhub.server.tools.base.excption.DatasourceErrorEnum;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ListResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.PageResult;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author moji
 * @version DataSourceCoreServiceImpl.java, v 0.1 2022年09月23日 15:51 moji Exp $
 * @date 2022/09/23
 */
@Service
public class DataSourceCoreServiceImpl implements DataSourceCoreService {

    @Autowired
    private DataSourceMapper dataSourceMapper;

    @Autowired
    private DataSourceOperations dataSourceOperations;

    @Autowired
    private ConsoleOperations consoleOperations;

    @Autowired
    private JdbcOperations jdbcOperations;

    @Autowired
    private SqlOperations sqlOperations;

    @Autowired
    private DatabaseOperations databaseOperations;

    @Autowired
    private TableOperations tableOperations;

    @Autowired
    private DataSourceCoreConverter dataSourceCoreConverter;

    @Override
    public DataResult<Long> create(DataSourceManageCreateParam param) {
        DataSourceDO dataSourceDO = dataSourceCoreConverter.param2do(param);
        dataSourceDO.setGmtCreate(LocalDateTime.now());
        dataSourceDO.setGmtModified(LocalDateTime.now());
        dataSourceMapper.insert(dataSourceDO);
        return DataResult.of(dataSourceDO.getId());
    }

    @Override
    public ActionResult update(DataSourceUpdateParam param) {
        DataSourceDO dataSourceDO = dataSourceCoreConverter.param2do(param);
        dataSourceDO.setGmtModified(LocalDateTime.now());
        dataSourceMapper.updateById(dataSourceDO);
        return ActionResult.isSuccess();
    }

    @Override
    public ActionResult delete(Long id) {
        dataSourceMapper.deleteById(id);
        return ActionResult.isSuccess();
    }

    @Override
    public DataResult<DataSourceDTO> queryById(Long id) {
        DataSourceDO dataSourceDO = dataSourceMapper.selectById(id);
        return DataResult.of(dataSourceCoreConverter.do2dto(dataSourceDO));
    }

    @Override
    public DataResult<Long> copyById(Long id) {
        DataSourceDO dataSourceDO = dataSourceMapper.selectById(id);
        dataSourceDO.setId(null);
        String alias = dataSourceDO.getAlias() + "Copy";
        dataSourceDO.setAlias(alias);
        dataSourceDO.setGmtCreate(LocalDateTime.now());
        dataSourceDO.setGmtModified(LocalDateTime.now());
        dataSourceMapper.insert(dataSourceDO);
        return DataResult.of(dataSourceDO.getId());
    }

    @Override
    public PageResult<DataSourceDTO> queryPage(DataSourcePageQueryParam param, DataSourceSelector selector) {
        QueryWrapper<DataSourceDO> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(param.getSearchKey())) {
            queryWrapper.like("alias", param.getSearchKey());
        }
        Integer start = param.getPageNo();
        Integer offset = param.getPageSize();
        Page<DataSourceDO> page = new Page<>(start, offset);
        IPage<DataSourceDO> iPage = dataSourceMapper.selectPage(page, queryWrapper);
        List<DataSourceDTO> dataSourceDTOS = dataSourceCoreConverter.do2dto(iPage.getRecords());
        return PageResult.of(dataSourceDTOS, iPage.getTotal(), param);
    }

    @Override
    public ActionResult test(DataSourceTestParam param) {
        com.alibaba.dbhub.server.domain.support.param.datasource.DataSourceTestParam dataSourceTestParam
            = dataSourceCoreConverter.param2param(param);
        DataSourceConnect dataSourceConnect = dataSourceOperations.test(dataSourceTestParam);
        if (BooleanUtils.isNotTrue(dataSourceConnect.getSuccess())) {
            throw new BusinessException(DatasourceErrorEnum.DATASOURCE_TEST_ERROR);
        }
        return ActionResult.isSuccess();
    }

    @Override
    public ListResult<Database> attach(Long id) {
        DataSourceDO dataSourceDO = dataSourceMapper.selectById(id);
        DataSourceCreateParam param = dataSourceCoreConverter.do2param(dataSourceDO);
        DataSourceConnect dataSourceConnect = dataSourceOperations.create(param);
        if (BooleanUtils.isNotTrue(dataSourceConnect.getSuccess())) {
            throw new BusinessException(DatasourceErrorEnum.DATASOURCE_CONNECT_ERROR);
        }

        // 查询database
        DatabaseQueryAllParam queryAllParam = new DatabaseQueryAllParam();
        queryAllParam.setDataSourceId(id);
        return ListResult.of(databaseOperations.queryAll(queryAllParam));
    }

    @Override
    public ActionResult close(Long id) {
        DataSourceCloseParam closeParam = new DataSourceCloseParam();
        closeParam.setDataSourceId(id);
        dataSourceOperations.close(closeParam);
        return ActionResult.isSuccess();
    }

    @Override
    public ActionResult createConsole(ConsoleConnectParam param) {
        ConsoleCreateParam createParam = dataSourceCoreConverter.param2consoleParam(param);
        consoleOperations.create(createParam);
        return ActionResult.isSuccess();
    }

    @Override
    public ActionResult closeConsole(ConsoleCloseParam param) {
        consoleOperations.close(param);
        return ActionResult.isSuccess();
    }

    @Override
    public ListResult<ExecuteResult> execute(DataSourceExecuteParam param) {
        if (StringUtils.isBlank(param.getSql())) {
            return ListResult.empty();
        }

        // 解析sql
        SqlAnalyseParam sqlAnalyseParam = new SqlAnalyseParam();
        sqlAnalyseParam.setDataSourceId(param.getDataSourceId());
        sqlAnalyseParam.setSql(param.getSql());
        List<Sql> sqlList = sqlOperations.analyse(sqlAnalyseParam);
        if (CollectionUtils.isEmpty(sqlList)) {
            throw new BusinessException(DatasourceErrorEnum.SQL_ANALYSIS_ERROR);
        }

        List<ExecuteResult> result = new ArrayList<>();
        // 执行sql
        for (Sql sql : sqlList) {
            TemplateExecuteParam templateQueryParam = new TemplateExecuteParam();
            templateQueryParam.setConsoleId(param.getConsoleId());
            templateQueryParam.setDataSourceId(param.getDataSourceId());
            templateQueryParam.setSql(sql.getSql());
            ExecuteResult executeResult = jdbcOperations.execute(templateQueryParam);
            result.add(executeResult);
        }

        return ListResult.of(result);
    }

    @Override
    public DataResult<Table> query(TableQueryParam param, TableSelector selector) {
        return DataResult.of(tableOperations.query(param, selector));
    }

    @Override
    public PageResult<Table> pageQuery(TablePageQueryParam param, TableSelector selector) {
        return tableOperations.pageQuery(param, selector);
    }
}
