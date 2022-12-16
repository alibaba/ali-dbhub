package com.alibaba.dbhub.server.domain.support.dialect;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.alibaba.dbhub.server.domain.support.dialect.common.model.SpiExample;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.model.Table;
import com.alibaba.dbhub.server.domain.support.model.TableColumn;
import com.alibaba.dbhub.server.domain.support.model.TableIndex;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * sql执行器
 *
 * @author 是仪
 */
public interface DatabaseMetaSchema {

    /**
     * 支持的数据库类型
     *
     * @return
     */
    DbTypeEnum supportDbType();

    /**
     * 样例
     *
     * @return
     */
    SpiExample example();

    /**
     * 展示建表语句
     *
     * @param jdbcTemplate
     * @param databaseName
     * @param tableName
     * @return
     */
    String showCreateTable(@NotNull NamedParameterJdbcTemplate jdbcTemplate, @NotEmpty String databaseName,
        @NotEmpty String tableName);

    /**
     * 删除表结构
     *
     * @param jdbcTemplate
     * @param databaseName
     * @param tableName
     * @return
     */
    void dropTable(@NotNull NamedParameterJdbcTemplate jdbcTemplate, @NotEmpty String databaseName,
        @NotEmpty String tableName);

    /**
     * 查询数据库表的数量
     *
     * @param jdbcTemplate
     * @param databaseName
     * @return
     */
    int queryTableCount(@NotNull NamedParameterJdbcTemplate jdbcTemplate, @NotEmpty String databaseName);

    /**
     * 分页查询表信息
     *
     * @param jdbcTemplate
     * @param databaseName
     * @param tableName
     * @return
     */
    List<Table> queryTableList(@NotNull NamedParameterJdbcTemplate jdbcTemplate, @NotEmpty String databaseName,
        String tableName, int pageNo, int pageSize);


    /**
     *
     *
     * @param jdbcTemplate
     * @param databaseName
     * @param tableName
     * @return
     */
    <T extends Table>  Table queryTableMeta(@NotNull NamedParameterJdbcTemplate jdbcTemplate, @NotEmpty String databaseName, @NotEmpty String tableName);

    /**
     * 查询列的信息
     *
     * @param jdbcTemplate
     * @param databaseName
     * @param tableNames
     * @return
     */
    List<TableColumn> queryColumnList(@NotNull NamedParameterJdbcTemplate jdbcTemplate,
        @NotEmpty String databaseName,
        @NotEmpty List<String> tableNames);

    /**
     * 查询列的信息
     *
     * @param jdbcTemplate
     * @param databaseName
     * @param tableNames   * @return
     */
    List<TableIndex> queryIndexList(@NotNull NamedParameterJdbcTemplate jdbcTemplate, @NotEmpty String databaseName,
        @NotEmpty List<String> tableNames);
}
