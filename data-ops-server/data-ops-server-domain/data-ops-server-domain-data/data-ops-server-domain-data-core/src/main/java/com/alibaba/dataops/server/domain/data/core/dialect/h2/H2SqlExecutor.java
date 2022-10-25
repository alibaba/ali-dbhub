package com.alibaba.dataops.server.domain.data.core.dialect.h2;

import java.util.Map;

import com.alibaba.dataops.server.domain.data.api.enums.DbTypeEnum;
import com.alibaba.dataops.server.domain.data.core.dialect.ExecutorColumnQueryParam;
import com.alibaba.dataops.server.domain.data.core.dialect.ExecutorIndexQueryParam;
import com.alibaba.dataops.server.domain.data.core.dialect.ExecutorTableColumnDTO;
import com.alibaba.dataops.server.domain.data.core.dialect.ExecutorTableDTO;
import com.alibaba.dataops.server.domain.data.core.dialect.ExecutorTableIndexDTO;
import com.alibaba.dataops.server.domain.data.core.dialect.ExecutorTablePageQueryParam;
import com.alibaba.dataops.server.domain.data.core.dialect.SqlExecutor;
import com.alibaba.dataops.server.tools.base.enums.YesOrNoEnum;
import com.alibaba.dataops.server.tools.base.wrapper.result.ListResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.PageResult;
import com.alibaba.dataops.server.tools.common.util.EasyEnumUtils;
import com.alibaba.dataops.server.tools.common.util.EasyOptionalUtils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * h2的sql执行器
 *
 * @author Jiaju Zhuang
 */
@Component
@Slf4j
public class H2SqlExecutor implements SqlExecutor {

    @Override
    public DbTypeEnum supportDbType() {
        return DbTypeEnum.H2;
    }

    @Override
    public PageResult<ExecutorTableDTO> pageQueryTable(ExecutorTablePageQueryParam param) {
        // 拼接参数
        Map<String, Object> queryParam = Maps.newHashMap();
        queryParam.put("databaseName", param.getDatabaseName());

        // 拼接sql
        String sql = " select TABLE_NAME \n"
            + "     , REMARKS   \n"
            + "        from INFORMATION_SCHEMA.TABLES\n"
            + "        where TABLE_SCHEMA = :databaseName\n";
        if (StringUtils.isNotBlank(param.getTableName())) {
            queryParam.put("tableName", param.getTableName());
            sql += "       TABLE_NAME = :tableName";
        }
        sql += "        order by TABLE_NAME;";
        return PageResult.of(param.getNamedParameterJdbcTemplate().query(sql,
                queryParam,
                (rs, rowNum) -> ExecutorTableDTO.builder()
                    .name(rs.getString("TABLE_NAME"))
                    .comment(rs.getString("REMARKS"))
                    .build()),
            0L, param);
    }

    @Override
    public ListResult<ExecutorTableColumnDTO> queryListColumn(ExecutorColumnQueryParam param) {
        // 拼接参数
        Map<String, Object> queryParam = Maps.newHashMap();
        queryParam.put("databaseName", param.getDatabaseName());
        queryParam.put("tableNameList", param.getTableNameList());

        return ListResult.of(param.getNamedParameterJdbcTemplate().query(
            "SELECT COLUMN_NAME            ,\n"
                + "       TABLE_NAME             ,\n"
                + "       DATA_TYPE             ,\n"
                + "       IS_NULLABLE         ,\n"
                + "       COLUMN_DEFAULT        , \n"
                + "       IS_IDENTITY         ,\n"
                + "       NUMERIC_PRECISION          ,\n"
                + "       NUMERIC_SCALE          ,\n"
                + "       CHARACTER_MAXIMUM_LENGTH ,\n"
                + "       REMARKS         \n"
                + "FROM INFORMATION_SCHEMA.COLUMNS\n"
                + "WHERE TABLE_NAME in (:tableNameList)\n"
                + "  AND TABLE_SCHEMA = :databaseName\n"
                + "ORDER BY ORDINAL_POSITION;",
            queryParam,
            (rs, rowNum) -> {
                ExecutorTableColumnDTO column = ExecutorTableColumnDTO.builder()
                    .name(rs.getString("COLUMN_NAME"))
                    .tableName(rs.getString("TABLE_NAME"))
                    .defaultValue(rs.getString("COLUMN_DEFAULT"))
                    .numericPrecision(EasyOptionalUtils.mapTo(rs.getString("NUMERIC_PRECISION"), Integer::parseInt))
                    .numericScale(EasyOptionalUtils.mapTo(rs.getString("NUMERIC_SCALE"), Integer::parseInt))
                    .comment(rs.getString("REMARKS"))
                    .characterMaximumLength(
                        EasyOptionalUtils.mapTo(rs.getString("CHARACTER_MAXIMUM_LENGTH"), Integer::parseInt))
                    .build();
                H2ColumnTypeEnum columnType = EasyEnumUtils.getEnum(H2ColumnTypeEnum.class, rs.getString("DATA_TYPE"));
                if (columnType != null) {
                    column.setType(columnType.getColumnType().getCode());
                }
                YesOrNoEnum nullable = EasyEnumUtils.getEnum(YesOrNoEnum.class, rs.getString("IS_NULLABLE"));
                if (nullable != null) {
                    column.setNullable(nullable.getCode());
                }
                YesOrNoEnum autoIncrement = EasyEnumUtils.getEnum(YesOrNoEnum.class, rs.getString("IS_IDENTITY"));
                if (nullable != null) {
                    column.setAutoIncrement(autoIncrement.getCode());
                }
                return column;
            }));
    }

    @Override
    public ListResult<ExecutorTableIndexDTO> queryListIndex(ExecutorIndexQueryParam param) {
        // 拼接参数
        Map<String, Object> queryParam = Maps.newHashMap();
        queryParam.put("databaseName", param.getDatabaseName());
        queryParam.put("tableNameList", param.getTableNameList());

        return ListResult.of(param.getNamedParameterJdbcTemplate().query(
            "SELECT INDEX_TYPE_NAME            ,\n"
                + "       TABLE_NAME             ,\n"
                + "       INDEX_NAME             ,\n"
                + "       REMARKS         \n"
                + "FROM INFORMATION_SCHEMA.INDEXES\n"
                + "WHERE TABLE_NAME in (:tableNameList)\n"
                + "  AND TABLE_SCHEMA = :databaseName;",
            queryParam,
            (rs, rowNum) -> {
                ExecutorTableIndexDTO index = ExecutorTableIndexDTO.builder()
                    .name(rs.getString("INDEX_NAME"))
                    .tableName(rs.getString("TABLE_NAME"))
                    .comment(rs.getString("REMARKS"))
                    .build();
                H2IndexTypeEnum indexType = EasyEnumUtils.getEnum(H2IndexTypeEnum.class,
                    rs.getString("INDEX_TYPE_NAME"));
                if (indexType != null) {
                    index.setType(indexType.getIndexType().getCode());
                }
                return index;
            }));
    }

}
