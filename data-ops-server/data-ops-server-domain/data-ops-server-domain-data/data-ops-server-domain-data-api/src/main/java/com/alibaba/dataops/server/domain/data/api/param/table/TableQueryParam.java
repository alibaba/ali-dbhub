package com.alibaba.dataops.server.domain.data.api.param.table;

import javax.validation.constraints.NotNull;

import com.alibaba.dataops.server.tools.base.wrapper.param.QueryParam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 查询表信息
 *
 * @author Jiaju Zhuang
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class TableQueryParam extends QueryParam {
    /**
     * 对应数据库存储的来源id
     */
    @NotNull
    private Long dataSourceId;

    /**
     * 对应的连接数据库名称
     */
    @NotNull
    private String databaseName;

    /**
     * 表名
     */
    @NotNull
    private String tableName;
}
