package com.alibaba.dataops.server.domain.support.dialect.common.param;

import javax.validation.constraints.NotNull;

import com.alibaba.dbhub.server.tools.base.wrapper.param.PageQueryParam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * 表分页查询
 *
 * @author Jiaju Zhuang
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SpiTablePageQueryParam extends PageQueryParam {
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
     * 表名
     */
    private String tableName;
}
