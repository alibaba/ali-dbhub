package com.alibaba.dataops.server.domain.support.dialect.mysql;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.dataops.server.domain.support.dialect.DatabaseSpi;
import com.alibaba.dataops.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dataops.server.domain.support.enums.IndexTypeEnum;
import com.alibaba.dataops.server.domain.support.dialect.common.model.SpiTableColumn;
import com.alibaba.dataops.server.domain.support.dialect.common.model.SpiTable;
import com.alibaba.dataops.server.domain.support.dialect.common.model.SpiTableIndexColumn;
import com.alibaba.dataops.server.domain.support.dialect.common.model.SpiTableIndexColumnUnion;
import com.alibaba.dataops.server.domain.support.dialect.common.model.SpiTableIndex;
import com.alibaba.dataops.server.domain.support.dialect.common.param.SpiColumnQueryParam;
import com.alibaba.dataops.server.domain.support.dialect.common.param.SpiIndexQueryParam;
import com.alibaba.dataops.server.domain.support.dialect.common.param.SpiTablePageQueryParam;
import com.alibaba.dbhub.server.tools.base.enums.YesOrNoEnum;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ListResult;
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

    @Override
    public DbTypeEnum supportDbType() {
        return DbTypeEnum.MYSQL;
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
    public ListResult<SpiTableColumn> queryListColumn(SpiColumnQueryParam param) {
        // 拼接参数
        Map<String, Object> queryParam = Maps.newHashMap();
        queryParam.put("databaseName", param.getDatabaseName());
        queryParam.put("tableNameList", param.getTableNameList());

        return ListResult.of(param.getNamedParameterJdbcTemplate().query(
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
            }));
    }

    @Override
    public ListResult<SpiTableIndex> queryListIndex(SpiIndexQueryParam param) {
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
        return ListResult.of(dataList);
    }

}
