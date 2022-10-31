package com.alibaba.dataops.server.domain.data.core.dialect.mysq;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.dataops.server.domain.data.api.enums.DbTypeEnum;
import com.alibaba.dataops.server.domain.data.core.dialect.common.model.ExecutorTableIndexDTO;
import com.alibaba.dataops.server.domain.data.core.dialect.SqlExecutor;
import com.alibaba.dataops.server.domain.data.core.dialect.common.model.ExecutorTableColumnDTO;
import com.alibaba.dataops.server.domain.data.core.dialect.common.model.ExecutorTableDTO;
import com.alibaba.dataops.server.domain.data.core.dialect.common.model.ExecutorTableIndexColumnDTO;
import com.alibaba.dataops.server.domain.data.core.dialect.common.param.ExecutorColumnQueryParam;
import com.alibaba.dataops.server.domain.data.core.dialect.common.param.ExecutorIndexQueryParam;
import com.alibaba.dataops.server.domain.data.core.dialect.common.param.ExecutorTablePageQueryParam;
import com.alibaba.dataops.server.tools.base.enums.YesOrNoEnum;
import com.alibaba.dataops.server.tools.base.wrapper.result.ListResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.PageResult;
import com.alibaba.dataops.server.tools.common.util.EasyCollectionUtils;
import com.alibaba.dataops.server.tools.common.util.EasyEnumUtils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * sql执行器
 *
 * @author Jiaju Zhuang
 */
@Component
@Slf4j
public class MysqlSqlExecutor implements SqlExecutor {

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
                (rs, rowNum) -> ExecutorTableDTO.builder()
                    .name(rs.getString("TABLE_NAME"))
                    .comment(rs.getString("TABLE_COMMENT"))
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
                ExecutorTableColumnDTO column = ExecutorTableColumnDTO.builder()
                    .name(rs.getString("COLUMN_NAME"))
                    .tableName(rs.getString("TABLE_NAME"))
                    .defaultValue(rs.getString("COLUMN_DEFAULT"))
                    .dataType(rs.getString("DATA_TYPE"))
                    .columnType(rs.getString("COLUMN_TYPE"))
                    .comment(rs.getString("COLUMN_COMMENT"))
                    .build();

                YesOrNoEnum nullable = EasyEnumUtils.getEnum(YesOrNoEnum.class, rs.getString("IS_NULLABLE"));
                if (nullable != null) {
                    column.setNullable(nullable.getCode());
                }
                String extra = rs.getString("EXTRA");
                if ("auto_increment".equals(extra)) {
                    column.setAutoIncrement(YesOrNoEnum.YES.getCode());
                } else {
                    column.setAutoIncrement(YesOrNoEnum.NO.getCode());
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

        List<ExecutorTableIndexDTO> indexList = param.getNamedParameterJdbcTemplate().query(
            "SELECT INDEX_TYPE_NAME            ,\n"
                + "       TABLE_NAME             ,\n"
                + "       INDEX_NAME             ,\n"
                + "       REMARKS         \n"
                + "FROM INFORMATION_SCHEMA.INDEXES\n"
                + "WHERE TABLE_NAME in (:tableNameList)\n"
                + "  AND INDEX_SCHEMA = :databaseName\n"
                + "  AND TABLE_SCHEMA = :databaseName"
                + "        order by INDEX_NAME",

            queryParam,
            (rs, rowNum) -> {
                ExecutorTableIndexDTO index = ExecutorTableIndexDTO.builder()
                    .name(rs.getString("INDEX_NAME"))
                    .tableName(rs.getString("TABLE_NAME"))
                    .comment(rs.getString("REMARKS"))
                    .build();
                MysqlIndexTypeEnum indexType = EasyEnumUtils.getEnum(MysqlIndexTypeEnum.class,
                    rs.getString("INDEX_TYPE_NAME"));
                if (indexType != null) {
                    index.setType(indexType.getIndexType().getCode());
                }
                return index;
            });
        if (CollectionUtils.isEmpty(indexList)) {
            return ListResult.of(indexList);
        }

        // 重新查询所有的表信息
        List<ExecutorTableIndexColumnDTO> columnList = param.getNamedParameterJdbcTemplate().query(
            "SELECT TABLE_NAME,\n"
                + "            INDEX_NAME,\n"
                + "            COLUMN_NAME,\n"
                + "            ORDERING_SPECIFICATION\n"
                + "        FROM INFORMATION_SCHEMA.INDEX_COLUMNS\n"
                + "WHERE TABLE_NAME in (:tableNameList)\n"
                + "  AND INDEX_SCHEMA = :databaseName\n"
                + "  AND TABLE_SCHEMA = :databaseName \n"
                + "        order by ORDINAL_POSITION",
            queryParam,
            (rs, rowNum) -> {
                ExecutorTableIndexColumnDTO column = ExecutorTableIndexColumnDTO.builder()
                    .name(rs.getString("COLUMN_NAME"))
                    .tableName(rs.getString("TABLE_NAME"))
                    .indexName(rs.getString("INDEX_NAME"))
                    .build();
                MysqlCollationEnum collation = EasyEnumUtils.getEnum(MysqlCollationEnum.class,
                    rs.getString("ORDERING_SPECIFICATION"));
                if (collation != null) {
                    column.setCollation(collation.getCollation().getCode());
                }
                return column;
            });

        Map<String, Map<String, List<ExecutorTableIndexColumnDTO>>> columnMap = EasyCollectionUtils.stream(columnList)
            .collect(Collectors.groupingBy(ExecutorTableIndexColumnDTO::getTableName,
                Collectors.groupingBy(ExecutorTableIndexColumnDTO::getIndexName)));
        for (ExecutorTableIndexDTO executorTableIndex : indexList) {
            Map<String, List<ExecutorTableIndexColumnDTO>> map = columnMap.get(executorTableIndex.getTableName());
            if (map == null) {
                continue;
            }
            executorTableIndex.setColumnList(map.get(executorTableIndex.getName()));
        }
        return ListResult.of(indexList);
    }

}
