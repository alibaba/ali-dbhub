package com.alibaba.dbhub.server.domain.support.dialect.common.param;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * 建表语句
 *
 * @author Jiaju Zhuang
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SpiShowCrateTableParam {
    /**
     * jdbc执行器
     */
    @NotNull
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 对应的连接数据库名称
     */
    @NotNull
    private String databaseName;

    /**
     * 表名字列表
     */
    @NotNull
    private String tableName;
}
