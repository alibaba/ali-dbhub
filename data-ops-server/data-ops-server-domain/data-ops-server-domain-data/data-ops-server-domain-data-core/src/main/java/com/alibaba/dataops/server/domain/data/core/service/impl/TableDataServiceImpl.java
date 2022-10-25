package com.alibaba.dataops.server.domain.data.core.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.dataops.server.domain.data.api.model.TableColumnDTO;
import com.alibaba.dataops.server.domain.data.api.model.TableDTO;
import com.alibaba.dataops.server.domain.data.api.model.TableIndexDTO;
import com.alibaba.dataops.server.domain.data.api.param.table.TablePageQueryParam;
import com.alibaba.dataops.server.domain.data.api.param.table.TableQueryParam;
import com.alibaba.dataops.server.domain.data.api.param.table.TableSelector;
import com.alibaba.dataops.server.domain.data.api.service.TableDataService;
import com.alibaba.dataops.server.domain.data.core.converter.TableCoreConverter;
import com.alibaba.dataops.server.domain.data.core.dialect.SqlExecutor;
import com.alibaba.dataops.server.domain.data.core.dialect.common.model.ExecutorTableDTO;
import com.alibaba.dataops.server.domain.data.core.dialect.common.param.ExecutorColumnQueryParam;
import com.alibaba.dataops.server.domain.data.core.dialect.common.param.ExecutorIndexQueryParam;
import com.alibaba.dataops.server.domain.data.core.dialect.common.param.ExecutorTablePageQueryParam;
import com.alibaba.dataops.server.domain.data.core.util.DataCenterUtils;
import com.alibaba.dataops.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.PageResult;
import com.alibaba.dataops.server.tools.common.util.EasyCollectionUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 表结构服务
 *
 * @author Jiaju Zhuang
 */
@Service
@Slf4j
public class TableDataServiceImpl implements TableDataService {

    @Resource
    private TableCoreConverter tableCoreConverter;

    @Override
    public DataResult<TableDTO> query(TableQueryParam param, TableSelector selector) {
        return null;
    }

    @Override
    public PageResult<TableDTO> pageQuery(TablePageQueryParam param, TableSelector selector) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = DataCenterUtils.getDefaultJdbcTemplate(
            param.getDataSourceId());
        SqlExecutor sqlExecutor = DataCenterUtils.getSqlExecutorByDataSourceId(param.getDataSourceId());

        // 构建查询表信息参数
        ExecutorTablePageQueryParam executorTablePageQueryParam = tableCoreConverter.param2param(param);
        executorTablePageQueryParam.setNamedParameterJdbcTemplate(namedParameterJdbcTemplate);

        // 查询表信息
        PageResult<ExecutorTableDTO> pageResult = sqlExecutor.pageQueryTable(executorTablePageQueryParam);
        List<TableDTO> list = tableCoreConverter.dto2dto(pageResult.getData());

        // 填充数据
        fillData(list, QueryContext.builder()
            .namedParameterJdbcTemplate(namedParameterJdbcTemplate)
            .databaseName(param.getDatabaseName())
            .sqlExecutor(sqlExecutor)
            .build(), selector);
        return PageResult.of(list, pageResult.getTotal(), param);
    }

    private void fillData(List<TableDTO> list, QueryContext queryContext, TableSelector selector) {
        if (CollectionUtils.isEmpty(list) || selector == null) {
            return;
        }
        // 填充列的信息
        fillColumnList(list, queryContext, selector);
        // 填充索引的信息
        fillIndexList(list, queryContext, selector);
    }

    private void fillIndexList(List<TableDTO> list, QueryContext queryContext, TableSelector selector) {
        if (BooleanUtils.isNotTrue(selector.getIndexList())) {
            return;
        }
        // 查询表结构信息
        List<String> tableNameList = EasyCollectionUtils.toList(list, TableDTO::getName);
        ExecutorIndexQueryParam executorIndexQueryParam = tableCoreConverter.context2paramIndex(queryContext);
        executorIndexQueryParam.setTableNameList(tableNameList);
        List<TableIndexDTO> tableIndexList = tableCoreConverter.dto2dtoIndex(
            queryContext.getSqlExecutor().queryListIndex(executorIndexQueryParam).getData());
        Map<String, List<TableIndexDTO>> tableIndexMap = EasyCollectionUtils.stream(tableIndexList)
            .collect(Collectors.groupingBy(TableIndexDTO::getTableName));
        for (TableDTO table : list) {
            table.setIndexList(tableIndexMap.get(table.getName()));
        }
    }

    private void fillColumnList(List<TableDTO> list, QueryContext queryContext, TableSelector selector) {
        if (BooleanUtils.isNotTrue(selector.getColumnList())) {
            return;
        }
        // 查询表结构信息
        List<String> tableNameList = EasyCollectionUtils.toList(list, TableDTO::getName);
        ExecutorColumnQueryParam executorColumnQueryParam = tableCoreConverter.context2paramColumn(queryContext);
        executorColumnQueryParam.setTableNameList(tableNameList);
        List<TableColumnDTO> tableColumnList = tableCoreConverter.dto2dtoColumn(
            queryContext.getSqlExecutor().queryListColumn(executorColumnQueryParam).getData());
        Map<String, List<TableColumnDTO>> tableColumnMap = EasyCollectionUtils.stream(tableColumnList)
            .collect(Collectors.groupingBy(TableColumnDTO::getTableName));
        for (TableDTO table : list) {
            table.setColumnList(tableColumnMap.get(table.getName()));
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    @ToString
    public static class QueryContext {
        private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
        private String databaseName;
        private SqlExecutor sqlExecutor;
    }
}
