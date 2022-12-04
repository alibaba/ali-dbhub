package com.alibaba.dbhub.server.domain.support.template;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.dbhub.server.domain.support.converter.TableCoreConverter;
import com.alibaba.dbhub.server.domain.support.dialect.DatabaseSpi;
import com.alibaba.dbhub.server.domain.support.dialect.common.model.SpiTable;
import com.alibaba.dbhub.server.domain.support.dialect.common.param.SpiColumnQueryParam;
import com.alibaba.dbhub.server.domain.support.dialect.common.param.SpiDropParam;
import com.alibaba.dbhub.server.domain.support.dialect.common.param.SpiIndexQueryParam;
import com.alibaba.dbhub.server.domain.support.dialect.common.param.SpiShowCrateTableParam;
import com.alibaba.dbhub.server.domain.support.dialect.common.param.SpiTablePageQueryParam;
import com.alibaba.dbhub.server.domain.support.model.Table;
import com.alibaba.dbhub.server.domain.support.model.TableColumn;
import com.alibaba.dbhub.server.domain.support.model.TableIndex;
import com.alibaba.dbhub.server.domain.support.operations.TableOperations;
import com.alibaba.dbhub.server.domain.support.param.table.DropParam;
import com.alibaba.dbhub.server.domain.support.param.table.ShowCreateTableParam;
import com.alibaba.dbhub.server.domain.support.param.table.TablePageQueryParam;
import com.alibaba.dbhub.server.domain.support.param.table.TableQueryParam;
import com.alibaba.dbhub.server.domain.support.param.table.TableSelector;
import com.alibaba.dbhub.server.domain.support.util.DataCenterUtils;
import com.alibaba.dbhub.server.tools.base.wrapper.result.PageResult;
import com.alibaba.dbhub.server.tools.common.util.EasyCollectionUtils;

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
public class TableTemplate implements TableOperations {

    @Resource
    private TableCoreConverter tableCoreConverter;

    @Override
    public String showCreateTable(ShowCreateTableParam param) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = DataCenterUtils.getDefaultJdbcTemplate(
            param.getDataSourceId());
        DatabaseSpi databaseSpi = DataCenterUtils.getDatabaseSpiByDataSourceId(param.getDataSourceId());

        // 构建查询表信息参数
        SpiShowCrateTableParam spiShowCrateTableParam = tableCoreConverter.param2param(param);
        spiShowCrateTableParam.setNamedParameterJdbcTemplate(namedParameterJdbcTemplate);

        return databaseSpi.showCrateTable(spiShowCrateTableParam);
    }

    @Override
    public void drop(DropParam param) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = DataCenterUtils.getDefaultJdbcTemplate(
            param.getDataSourceId());
        DatabaseSpi databaseSpi = DataCenterUtils.getDatabaseSpiByDataSourceId(param.getDataSourceId());

        // 构建查询表信息参数
        SpiDropParam spiDropParam = tableCoreConverter.param2param(param);
        spiDropParam.setNamedParameterJdbcTemplate(namedParameterJdbcTemplate);

        databaseSpi.drop(spiDropParam);
    }

    @Override
    public Table query(TableQueryParam param, TableSelector selector) {
        return null;
    }

    @Override
    public PageResult<Table> pageQuery(TablePageQueryParam param, TableSelector selector) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = DataCenterUtils.getDefaultJdbcTemplate(
            param.getDataSourceId());
        DatabaseSpi databaseSpi = DataCenterUtils.getDatabaseSpiByDataSourceId(param.getDataSourceId());

        // 构建查询表信息参数
        SpiTablePageQueryParam spiTablePageQueryParam = tableCoreConverter.param2param(param);
        spiTablePageQueryParam.setNamedParameterJdbcTemplate(namedParameterJdbcTemplate);

        // 查询表信息
        PageResult<SpiTable> pageResult = databaseSpi.pageQueryTable(spiTablePageQueryParam);
        List<Table> list = tableCoreConverter.dto2dto(pageResult.getData());

        // 填充数据
        fillData(list, QueryContext.builder()
            .namedParameterJdbcTemplate(namedParameterJdbcTemplate)
            .databaseName(param.getDatabaseName())
            .databaseSpi(databaseSpi)
            .build(), selector);
        return PageResult.of(list, pageResult.getTotal(), param);
    }

    private void fillData(List<Table> list, QueryContext queryContext, TableSelector selector) {
        if (CollectionUtils.isEmpty(list) || selector == null) {
            return;
        }
        // 填充列的信息
        fillColumnList(list, queryContext, selector);
        // 填充索引的信息
        fillIndexList(list, queryContext, selector);
    }

    private void fillIndexList(List<Table> list, QueryContext queryContext, TableSelector selector) {
        if (BooleanUtils.isNotTrue(selector.getIndexList())) {
            return;
        }
        // 查询表结构信息
        List<String> tableNameList = EasyCollectionUtils.toList(list, Table::getName);
        SpiIndexQueryParam spiIndexQueryParam = tableCoreConverter.context2paramIndex(queryContext);
        spiIndexQueryParam.setTableNameList(tableNameList);
        List<TableIndex> tableIndexList = tableCoreConverter.dto2dtoIndex(
            queryContext.getDatabaseSpi().queryListIndex(spiIndexQueryParam));
        Map<String, List<TableIndex>> tableIndexMap = EasyCollectionUtils.stream(tableIndexList)
            .collect(Collectors.groupingBy(TableIndex::getTableName));
        for (Table table : list) {
            table.setIndexList(tableIndexMap.get(table.getName()));
        }
    }

    private void fillColumnList(List<Table> list, QueryContext queryContext, TableSelector selector) {
        if (BooleanUtils.isNotTrue(selector.getColumnList())) {
            return;
        }
        // 查询表结构信息
        List<String> tableNameList = EasyCollectionUtils.toList(list, Table::getName);
        SpiColumnQueryParam spiColumnQueryParam = tableCoreConverter.context2paramColumn(queryContext);
        spiColumnQueryParam.setTableNameList(tableNameList);
        List<TableColumn> tableColumnList = tableCoreConverter.dto2dtoColumn(
            queryContext.getDatabaseSpi().queryListColumn(spiColumnQueryParam));
        Map<String, List<TableColumn>> tableColumnMap = EasyCollectionUtils.stream(tableColumnList)
            .collect(Collectors.groupingBy(TableColumn::getTableName));
        for (Table table : list) {
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
        private DatabaseSpi databaseSpi;
    }
}
