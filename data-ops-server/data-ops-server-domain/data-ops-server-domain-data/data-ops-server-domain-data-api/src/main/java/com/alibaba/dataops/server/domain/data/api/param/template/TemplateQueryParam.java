package com.alibaba.dataops.server.domain.data.api.param.template;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 查询参数
 *
 * @author Jiaju Zhuang
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class TemplateQueryParam {

    /**
     * 对应数据库存储的来源id
     */
    @NotNull
    private Long dataSourceId;

    /**
     * 对应的连接数据库名称
     * 支持多个database的数据库会调用use xx;来切换来数据库
     */
    private String databaseName;

    /**
     * sql语句
     */
    private String sql;
}
