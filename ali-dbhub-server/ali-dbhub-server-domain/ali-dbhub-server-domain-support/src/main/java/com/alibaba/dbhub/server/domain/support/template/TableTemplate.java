package com.alibaba.dbhub.server.domain.support.template;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.dbhub.server.domain.support.dialect.MetaSchema;
import com.alibaba.dbhub.server.domain.support.model.Table;
import com.alibaba.dbhub.server.domain.support.model.TableColumn;
import com.alibaba.dbhub.server.domain.support.model.TableIndex;
import com.alibaba.dbhub.server.domain.support.operations.TableOperations;
import com.alibaba.dbhub.server.domain.support.param.table.DropParam;
import com.alibaba.dbhub.server.domain.support.param.table.ShowCreateTableParam;
import com.alibaba.dbhub.server.domain.support.param.table.TablePageQueryParam;
import com.alibaba.dbhub.server.domain.support.param.table.TableQueryParam;
import com.alibaba.dbhub.server.domain.support.param.table.TableSelector;
import com.alibaba.dbhub.server.domain.support.sql.DbhubContext;
import com.alibaba.dbhub.server.tools.base.wrapper.result.PageResult;
import com.alibaba.dbhub.server.tools.common.util.EasyCollectionUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 表结构服务
 *
 * @author Jiaju Zhuang
 */
@Service
@Slf4j
public class TableTemplate implements TableOperations {

    @Override
    public String showCreateTable(ShowCreateTableParam param) {
        MetaSchema metaSchema = DbhubContext.getConnectInfo().getDbType().metaSchema();
        return metaSchema.showCreateTable(param.getDatabaseName(), null, param.getTableName());
    }

    @Override
    public void drop(DropParam param) {
        MetaSchema metaSchema = DbhubContext.getConnectInfo().getDbType().metaSchema();
        metaSchema.dropTable(param.getDatabaseName(), null, param.getTableName());
    }

    @Override
    public Table query(TableQueryParam param, TableSelector selector) {
        return null;
    }

    @Override
    public PageResult<Table> pageQuery(TablePageQueryParam param, TableSelector selector) {
        MetaSchema metaSchema = DbhubContext.getMetaSchema();
        List<Table> list = metaSchema.queryTableList(param.getDatabaseName(), param.getTableName(), param.getPageNo(),
            param.getPageSize());
        if (CollectionUtils.isEmpty(list)) {
            return PageResult.of(list, 0L, param);
        }
        List<String> tableNameList = EasyCollectionUtils.toList(list, Table::getName);
        List<TableColumn> tableColumnList = metaSchema.queryColumnList(param.getDatabaseName(), null, tableNameList);
        List<TableIndex> tableIndexList = metaSchema.queryIndexList(param.getDatabaseName(), null, tableNameList);

        Map<String, List<TableIndex>> tableIndexMap = EasyCollectionUtils.stream(tableIndexList).collect(
            Collectors.groupingBy(TableIndex::getTableName));
        Map<String, List<TableColumn>> tableColumnMap = EasyCollectionUtils.stream(tableColumnList).collect(
            Collectors.groupingBy(TableColumn::getTableName));

        list.stream().forEach(table -> {
            table.setColumnList(tableColumnMap.get(table.getName()));
            table.setIndexList(tableIndexMap.get(table.getName()));
        });

        return PageResult.of(list, 100L, param);
    }

}
