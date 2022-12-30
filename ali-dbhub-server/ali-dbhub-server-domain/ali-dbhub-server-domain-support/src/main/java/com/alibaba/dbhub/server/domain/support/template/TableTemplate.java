package com.alibaba.dbhub.server.domain.support.template;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.alibaba.dbhub.server.domain.support.dialect.MetaSchema;
import com.alibaba.dbhub.server.domain.support.enums.CollationEnum;
import com.alibaba.dbhub.server.domain.support.model.Sql;
import com.alibaba.dbhub.server.domain.support.model.Table;
import com.alibaba.dbhub.server.domain.support.model.TableColumn;
import com.alibaba.dbhub.server.domain.support.model.TableIndex;
import com.alibaba.dbhub.server.domain.support.model.TableIndexColumn;
import com.alibaba.dbhub.server.domain.support.operations.TableOperations;
import com.alibaba.dbhub.server.domain.support.param.table.DropParam;
import com.alibaba.dbhub.server.domain.support.param.table.ShowCreateTableParam;
import com.alibaba.dbhub.server.domain.support.param.table.TablePageQueryParam;
import com.alibaba.dbhub.server.domain.support.param.table.TableQueryParam;
import com.alibaba.dbhub.server.domain.support.param.table.TableSelector;
import com.alibaba.dbhub.server.domain.support.sql.DbhubContext;
import com.alibaba.dbhub.server.tools.base.wrapper.result.PageResult;
import com.alibaba.dbhub.server.tools.common.util.EasyCollectionUtils;
import com.alibaba.dbhub.server.tools.common.util.EasyEnumUtils;
import com.alibaba.druid.sql.ast.SQLDataTypeImpl;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLNotNullConstraint;
import com.alibaba.druid.sql.ast.statement.SQLSelectOrderByItem;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlPrimaryKey;
import com.alibaba.druid.sql.dialect.mysql.ast.expr.MySqlCharExpr;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlTableIndex;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
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
    public List<Sql> buildSql(Table oldTable, Table newTable) {
        // 创建表
        if (oldTable == null) {
            MySqlCreateTableStatement mySqlCreateTableStatement = new MySqlCreateTableStatement();
            mySqlCreateTableStatement.setTableName(newTable.getName());
            if (!Objects.isNull(newTable.getComment())) {
                mySqlCreateTableStatement.setComment(new MySqlCharExpr(newTable.getComment()));
            }
            List<TableColumn> columnList = newTable.getColumnList();
            if (!CollectionUtils.isEmpty(columnList)) {
                for (TableColumn tableColumn : columnList) {
                    SQLColumnDefinition sqlColumnDefinition = new SQLColumnDefinition();
                    mySqlCreateTableStatement.addColumn(sqlColumnDefinition);
                    sqlColumnDefinition.setName(tableColumn.getName());
                    sqlColumnDefinition.setDataType(new SQLDataTypeImpl(tableColumn.getColumnType()));
                    if (BooleanUtils.isNotTrue(tableColumn.getNullable())) {
                        sqlColumnDefinition.addConstraint(new SQLNotNullConstraint());
                    }
                    if (!Objects.isNull(tableColumn.getDefaultValue())) {
                        sqlColumnDefinition.setDefaultExpr(new MySqlCharExpr(tableColumn.getDefaultValue()));
                    }
                    sqlColumnDefinition.setAutoIncrement(BooleanUtils.isTrue(tableColumn.getAutoIncrement()));
                    if (!Objects.isNull(tableColumn.getComment())) {
                        sqlColumnDefinition.setComment(tableColumn.getComment());
                    }
                }
                // 主键
                List<TableColumn> primaryKeycolumnList = EasyCollectionUtils.stream(columnList)
                    .filter(tableColumn -> BooleanUtils.isTrue(tableColumn.getPrimaryKey()))
                    .collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(primaryKeycolumnList)) {
                    MySqlPrimaryKey mySqlPrimaryKey = new MySqlPrimaryKey();
                    mySqlCreateTableStatement.getTableElementList().add(mySqlPrimaryKey);
                    for (TableColumn tableColumn : primaryKeycolumnList) {
                        mySqlPrimaryKey.addColumn(new SQLIdentifierExpr(tableColumn.getName()));
                    }
                }
            }

            // 索引
            List<TableIndex> indexList = newTable.getIndexList();
            if (!CollectionUtils.isEmpty(indexList)) {
                for (TableIndex tableIndex : indexList) {
                    MySqlTableIndex mySqlTableIndex = new MySqlTableIndex();
                    mySqlCreateTableStatement.getTableElementList().add(mySqlTableIndex);
                    mySqlTableIndex.setName(tableIndex.getName());
                    if (!CollectionUtils.isEmpty(tableIndex.getColumnList())) {
                        for (TableIndexColumn tableIndexColumn : tableIndex.getColumnList()) {
                            SQLSelectOrderByItem sqlSelectOrderByItem = new SQLSelectOrderByItem();
                            sqlSelectOrderByItem.setExpr(new SQLIdentifierExpr(tableIndexColumn.getName()));
                            CollationEnum collation = EasyEnumUtils.getEnum(CollationEnum.class,
                                tableIndexColumn.getCollation());
                            if (collation != null) {
                                sqlSelectOrderByItem.setType(collation.getSqlOrderingSpecification());
                            }
                            mySqlTableIndex.addColumn(sqlSelectOrderByItem);
                        }
                    }
                }
            }

            return Lists.newArrayList(Sql.builder().sql(mySqlCreateTableStatement.toString() + ";").build());
        }

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
