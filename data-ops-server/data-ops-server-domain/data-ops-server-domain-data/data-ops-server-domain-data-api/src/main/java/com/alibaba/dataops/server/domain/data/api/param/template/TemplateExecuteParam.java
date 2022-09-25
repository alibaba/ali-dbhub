package com.alibaba.dataops.server.domain.data.api.param.template;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 执行参数
 *
 * @author Jiaju Zhuang
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class TemplateExecuteParam {
    /**
     * 对应数据库存储的来源id
     */
    @NotNull
    private Long dataSourceId;

    /**
     * 控制台id
     */
    @NotNull
    private Long consoleId;

    /**
     * sql语句
     */
    private String sql;
}
