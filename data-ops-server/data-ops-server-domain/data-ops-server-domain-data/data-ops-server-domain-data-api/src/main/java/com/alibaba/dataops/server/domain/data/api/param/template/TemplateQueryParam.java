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
     * 控制台id
     */
    @NotNull
    private Long consoleId;

    /**
     * sql语句
     */
    private String sql;
}
