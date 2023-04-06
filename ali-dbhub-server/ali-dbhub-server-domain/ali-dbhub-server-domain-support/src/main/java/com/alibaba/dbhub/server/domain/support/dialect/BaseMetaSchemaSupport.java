///**
// * alibaba.com Inc.
// * Copyright (c) 2004-2023 All Rights Reserved.
// */
//package com.alibaba.dbhub.server.domain.support.dialect;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import javax.validation.constraints.NotEmpty;
//
//import com.alibaba.dbhub.server.domain.support.model.Function;
//import com.alibaba.dbhub.server.domain.support.model.Procedure;
//import com.alibaba.dbhub.server.domain.support.model.TableColumn;
//import com.alibaba.dbhub.server.domain.support.model.TableIndex;
//import com.alibaba.dbhub.server.domain.support.model.TableIndexColumn;
//import com.alibaba.dbhub.server.domain.support.model.Trigger;
//import com.alibaba.dbhub.server.domain.support.sql.DataSource;
//
//import com.google.common.collect.Lists;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * @author jipengfei
// * @version : BaseMetaSchemaSupport.java
// */
//@Slf4j
//public abstract class BaseMetaSchemaSupport implements MetaSchema{
//
//    @Override
//    public List<String> databases() {
//        return getMapper().showDatabases();
//    }
//
//    @Override
//    public List<String> schemas(String databaseName) {
//        return null;
//    }
//
//    @Override
//    public String tableDDL(String databaseName, String schemaName, String tableName) {
//        return getMapper().showCreateTable(databaseName,schemaName, tableName);
//    }
//
//    @Override
//    public void dropTable(String databaseName, String schemaName, String tableName) {
//        getMapper().dropTable(databaseName, tableName);
//    }
//
//    @Override
//    public List tables(@NotEmpty String databaseName, String schemaName,String tableName) {
//        return getMapper().selectTables(databaseName, schemaName,tableName);
//    }
//
//    @Override
//    public List views(String databaseName, String schemaName) {
//        return null;
//    }
//
//    @Override
//    public List<Function> functions(String databaseName, String schemaName) {
//        return null;
//    }
//
//    @Override
//    public List<Trigger> triggers(String databaseName, String schemaName) {
//        return null;
//    }
//
//    @Override
//    public List<Procedure> procedures(String databaseName, String schemaName) {
//        return null;
//    }
//
//    @Override
//    public List<? extends TableColumn> columns(String databaseName, String schemaName, String tableName) {
//        return getMapper().selectColumns(databaseName,schemaName, tableName);
//    }
//
//    @Override
//    public List<? extends TableColumn> columns(String databaseName, String schemaName,String tableName, String columnName) {
//        List<TableColumn> tableColumns = Lists.newArrayList();
//        try {
//            Connection connection = DataSource.getInstance().getConnection();
//            ResultSet columns = connection.getMetaData().getColumns(databaseName, schemaName, tableName, columnName);
//            List<String> columnDefinitions = new ArrayList<>();
//            while (columns.next()) {
//                String cname = columns.getString("COLUMN_NAME");
//                String columnType = columns.getString("TYPE_NAME");
//                int columnSize = columns.getInt("COLUMN_SIZE");
//                String remarks = columns.getString("REMARKS");
//                String defaultValue = columns.getString("COLUMN_DEF");
//                String tname = columns.getString("TABLE_NAME");
//                TableColumn tableColumn = new TableColumn();
//                tableColumn.setName(cname);
//                tableColumn.setComment(remarks);
//                tableColumn.setDefaultValue(defaultValue);
//                //tableColumn.setDataType(columnType);
//                tableColumn.setTableName(tname);
//                tableColumns.add(tableColumn);
//            }
//        } catch (Exception e) {
//            log.error("get columns error", e);
//        }
//        return tableColumns;
//    }
//
//    @Override
//    public List<TableIndex> indexes(String databaseName, String schemaName, String tableName) {
//        List<TableIndex> dataList = Lists.newArrayList();
//        List<TableIndexColumn> list = getMapper().selectTableIndexes(databaseName, schemaName,tableName);
//        Map<String, List<TableIndexColumn>> map = list.stream().collect(
//            Collectors.groupingBy(TableIndexColumn::getIndexName));
//        for (Map.Entry<String, List<TableIndexColumn>> entry1 : map.entrySet()) {
//            TableIndexColumn first = entry1.getValue().get(0);
//            dataList.add(buildTableIndex(tableName, entry1.getKey(), first.getType(), first.getComment(),
//                entry1.getValue()));
//        }
//        return dataList;
//    }
//
//    private TableIndex buildTableIndex(String tableName, String indexName, String type, String comment,
//        List<TableIndexColumn> indexSubList) {
//        return TableIndex.builder()
//            .tableName(tableName)
//            .name(indexName)
//            .type(type)
//            .comment(comment)
//            .columnList(indexSubList)
//            .build();
//    }
//
//
//}