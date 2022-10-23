package com.alibaba.dataops.server.domain.data.core.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 列信息
 *
 * @author Jiaju Zhuang
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class TableColumnDO {
    /**
     * 列名
     */
    private String name;

    /**
     * 列的类型
     */
    private String type;

    /**
     * 是否为空
     */
    private String nullable;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 额外数据
     */
    private String extra;

    /**
     * 数字精度
     */
    private Integer numericPrecision;

    /**
     * 数字比例
     */
    private Integer numericScale;

    /**
     * 字符串最大长度
     */
    private Integer characterMaximumLength;

    /**
     * 注释
     */
    private String comment;
}

