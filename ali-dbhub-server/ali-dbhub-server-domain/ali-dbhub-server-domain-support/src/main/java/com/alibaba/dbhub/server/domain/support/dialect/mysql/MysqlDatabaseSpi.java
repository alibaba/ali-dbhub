package com.alibaba.dbhub.server.domain.support.dialect.mysql;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.dbhub.server.domain.support.dialect.DatabaseSpi;
import com.alibaba.dbhub.server.domain.support.dialect.common.model.SpiExample;
import com.alibaba.dbhub.server.domain.support.dialect.common.model.SpiTable;
import com.alibaba.dbhub.server.domain.support.dialect.common.model.SpiTableColumn;
import com.alibaba.dbhub.server.domain.support.dialect.common.model.SpiTableIndex;
import com.alibaba.dbhub.server.domain.support.dialect.common.model.SpiTableIndexColumn;
import com.alibaba.dbhub.server.domain.support.dialect.common.model.SpiTableIndexColumnUnion;
import com.alibaba.dbhub.server.domain.support.dialect.common.param.SpiColumnQueryParam;
import com.alibaba.dbhub.server.domain.support.dialect.common.param.SpiDropParam;
import com.alibaba.dbhub.server.domain.support.dialect.common.param.SpiIndexQueryParam;
import com.alibaba.dbhub.server.domain.support.dialect.common.param.SpiShowCrateTableParam;
import com.alibaba.dbhub.server.domain.support.dialect.common.param.SpiTablePageQueryParam;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.enums.IndexTypeEnum;
import com.alibaba.dbhub.server.tools.base.enums.YesOrNoEnum;
import com.alibaba.dbhub.server.tools.base.wrapper.result.PageResult;
import com.alibaba.dbhub.server.tools.common.util.EasyCollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * sql执行器
 *
 * @author Jiaju Zhuang
 */
@Component
@Slf4j
public class MysqlDatabaseSpi implements DatabaseSpi {
    private static final SpiExample EXAMPLE=SpiExample.builder()
        .createTable("-- 创建一个表\n"
            + "CREATE TABLE `test` (\n"
            + "\t`id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',\n"
            + "\t-- 创建时间 创建时自动设置当前时间，后续不再变更\n"
            + "\t`gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n"
            + "\t-- 修改时间 创建时自动设置当前时间，修改时自动再次设置为当前时间\n"
            + "\t`gmt_modified` datetime NULL DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT '修改时间',\n"
            + "\t`date` datetime NULL COMMENT '日期',\n"
            + "\t`string` varchar(128) NOT NULL DEFAULT 'Test' COMMENT '字符串',\n"
            + "\tPRIMARY KEY (`id`),\n"
            + "\t-- 创建索引\n"
            + "\tKEY `idx_string` (`string`)\n"
            + ") DEFAULT CHARACTER SET=utf8mb4 COMMENT='测试表';\n"
            + "-- 文档：https://dev.mysql.com/doc/refman/5.7/en/create-table.html\n")
        .alterTable("-- 新增字段\n"
            + "ALTER TABLE `test`\n"
            + "    ADD COLUMN `number` bigint unsigned NULL COMMENT '数字';\n"
            + "-- 新增唯一索引\n"
            + "ALTER TABLE `test`\n"
            + "    ADD UNIQUE INDEX uk_number (number);\n"
            + "-- 删除字段\n"
            + "ALTER TABLE `test`\n"
            + "    DROP COLUMN `number`;\n"
            + "-- 文档: https://dev.mysql.com/doc/refman/5.7/en/alter-table.html")
        .build();

    @Override
    public DbTypeEnum supportDbType() {
        return DbTypeEnum.MYSQL;
    }

    @Override
    public SpiExample example() {
        return EXAMPLE;
    }

    @Override
    public String showCrateTable(SpiShowCrateTableParam param) {
        List<String> createTableList = param.getNamedParameterJdbcTemplate().query(
            "SHOW CREATE TABLE " + param.getDatabaseName() + "." + param.getTableName() + "; ",
            (rs, rowNum) -> rs.getString(2));
        return EasyCollectionUtils.findFirst(createTableList);
    }

    @Override
    public void drop(SpiDropParam param) {
        param.getNamedParameterJdbcTemplate().update(
            "drop TABLE " + param.getDatabaseName() + "." + param.getTableName() + "; ", Collections.EMPTY_MAP);
    }

    @Override
    public PageResult<SpiTable> pageQueryTable(SpiTablePageQueryParam param) {
        // 拼接参数
        Map<String, Object> queryParam = Maps.newHashMap();
        queryParam.put("databaseName", param.getDatabaseName());

        // 拼接sql
        String sql = " select TABLE_NAME \n"
            + "     , TABLE_COMMENT   \n"
            + "        from INFORMATION_SCHEMA.TABLES\n"
            + "        where TABLE_SCHEMA = :databaseName\n";
        if (StringUtils.isNotBlank(param.getTableName())) {
            queryParam.put("tableName", param.getTableName());
            sql += "   AND    TABLE_NAME = :tableName";
        }
        sql += "        order by TABLE_NAME;";
        return PageResult.of(param.getNamedParameterJdbcTemplate().query(sql,
                queryParam,
                (rs, rowNum) -> SpiTable.builder()
                    .name(rs.getString("TABLE_NAME"))
                    .comment(rs.getString("TABLE_COMMENT"))
                    .build()),
            0L, param);
    }

    @Override
    public List<SpiTableColumn> queryListColumn(SpiColumnQueryParam param) {
        // 拼接参数
        Map<String, Object> queryParam = Maps.newHashMap();
        queryParam.put("databaseName", param.getDatabaseName());
        queryParam.put("tableNameList", param.getTableNameList());

        return param.getNamedParameterJdbcTemplate().query(
            "SELECT COLUMN_NAME            ,\n"
                + "       TABLE_NAME             ,\n"
                + "       DATA_TYPE             ,\n"
                + "       COLUMN_TYPE           ,\n"
                + "       IS_NULLABLE         ,\n"
                + "       COLUMN_DEFAULT        , \n"
                + "       EXTRA         ,\n"
                + "       COLUMN_COMMENT         \n"
                + "FROM INFORMATION_SCHEMA.COLUMNS\n"
                + "WHERE TABLE_NAME in (:tableNameList)\n"
                + "  AND TABLE_SCHEMA = :databaseName\n"
                + "ORDER BY ORDINAL_POSITION;",
            queryParam,
            (rs, rowNum) -> {
                SpiTableColumn column = SpiTableColumn.builder()
                    .name(rs.getString("COLUMN_NAME"))
                    .tableName(rs.getString("TABLE_NAME"))
                    .defaultValue(rs.getString("COLUMN_DEFAULT"))
                    .dataType(rs.getString("DATA_TYPE"))
                    .columnType(rs.getString("COLUMN_TYPE"))
                    .comment(rs.getString("COLUMN_COMMENT"))
                    .build();

                if (YesOrNoEnum.YES.getCode().equalsIgnoreCase(rs.getString("IS_NULLABLE"))) {
                    column.setNullable(YesOrNoEnum.YES.getCode());
                } else {
                    column.setNullable(YesOrNoEnum.NO.getCode());
                }

                String extra = rs.getString("EXTRA");
                if ("auto_increment".equalsIgnoreCase(extra)) {
                    column.setAutoIncrement(YesOrNoEnum.YES.getCode());
                } else {
                    column.setAutoIncrement(YesOrNoEnum.NO.getCode());
                }
                return column;
            });
    }

    @Override
    public List<SpiTableIndex> queryListIndex(SpiIndexQueryParam param) {
        // 拼接参数
        Map<String, Object> queryParam = Maps.newHashMap();
        queryParam.put("databaseName", param.getDatabaseName());
        queryParam.put("tableNameList", param.getTableNameList());

        List<SpiTableIndexColumnUnion> indexList = param.getNamedParameterJdbcTemplate().query(
            "SELECT INDEX_NAME            ,\n"
                + "       TABLE_NAME             ,\n"
                + "       COLUMN_NAME             ,\n"
                + "       COLLATION             ,\n"
                + "       SEQ_IN_INDEX             ,\n"
                + "       NON_UNIQUE             ,\n"
                + "       INDEX_COMMENT         \n"
                + "FROM INFORMATION_SCHEMA.STATISTICS\n"
                + "WHERE TABLE_NAME in (:tableNameList)\n"
                + "  AND INDEX_SCHEMA = :databaseName\n"
                + "  AND TABLE_SCHEMA = :databaseName"
                + "        order by SEQ_IN_INDEX",

            queryParam,
            (rs, rowNum) -> {
                SpiTableIndexColumnUnion index = SpiTableIndexColumnUnion.builder()
                    .indexName(rs.getString("INDEX_NAME"))
                    .tableName(rs.getString("TABLE_NAME"))
                    .comment(rs.getString("INDEX_COMMENT"))
                    .columnName(rs.getString("COLUMN_NAME"))
                    .ordinalPosition(rs.getLong("SEQ_IN_INDEX"))
                    .build();

                if ("PRIMARY".equalsIgnoreCase(rs.getString("INDEX_NAME"))) {
                    index.setType(IndexTypeEnum.PRIMARY_KEY.getCode());
                } else {
                    if ("1".equalsIgnoreCase(rs.getString("NON_UNIQUE"))) {
                        index.setType(IndexTypeEnum.NORMAL.getCode());
                    } else {
                        index.setType(IndexTypeEnum.UNIQUE.getCode());
                    }
                }

                if (MysqlCollationEnum.DESC.getCode().equalsIgnoreCase(rs.getString("COLLATION"))) {
                    index.setCollation(MysqlCollationEnum.DESC.getCollation().getCode());
                } else {
                    index.setCollation(MysqlCollationEnum.ASC.getCollation().getCode());
                }
                return index;
            });

        // 分组数据
        Map<String, Map<String, List<SpiTableIndexColumnUnion>>> indexMap = EasyCollectionUtils.stream(
                indexList)
            .sorted(Comparator.comparing(SpiTableIndexColumnUnion::getOrdinalPosition))
            .collect(Collectors.groupingBy(SpiTableIndexColumnUnion::getTableName,
                Collectors.groupingBy(SpiTableIndexColumnUnion::getIndexName)));
        List<SpiTableIndex> dataList = Lists.newArrayList();
        indexMap.forEach((tableName, indexSubMap) -> indexSubMap.forEach((indexName, indexSubList) -> {
            // 拼接表对象
            SpiTableIndexColumnUnion indexColumnUnionFirst = indexSubList.get(0);
            dataList.add(SpiTableIndex.builder()
                .tableName(tableName)
                .name(indexName)
                .type(indexColumnUnionFirst.getType())
                .comment(indexColumnUnionFirst.getComment())
                .columnList(
                    EasyCollectionUtils.toList(indexSubList, indexColumnUnion -> SpiTableIndexColumn.builder()
                        .name(indexColumnUnion.getColumnName())
                        .indexName(indexName)
                        .tableName(tableName)
                        .collation(indexColumnUnion.getCollation())
                        .build()))
                .build());

        }));
        return dataList;
    }

}
