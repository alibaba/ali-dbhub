package com.alibaba.dataops.server.domain.data.api.param.template;

import javax.validation.constraints.NotNull;

import com.alibaba.dataops.server.tools.base.constant.EasyToolsConstant;

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
     * 必须是单个语句
     */
    private String sql;

    /**
     * 分页编码
     * 只有查询语句分页才有效
     * 不传默认1
     */
    private Integer pageNo;

    /**
     * 分页大小
     * 只有查询语句分页才有效
     * 不传默认500
     *
     * @see EasyToolsConstant#MAX_PAGE_SIZE
     */
    private Integer pageSize;
}
