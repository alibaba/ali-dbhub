package com.alibaba.dbhub.server.domain.data.core.dialect.common.param;

import javax.validation.constraints.NotNull;

import com.alibaba.dbhub.server.tools.base.wrapper.param.PageQueryParam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * 表分页查询
 *
 * @author Jiaju Zhuang
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class ExecutorTablePageQueryParam extends PageQueryParam {
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
