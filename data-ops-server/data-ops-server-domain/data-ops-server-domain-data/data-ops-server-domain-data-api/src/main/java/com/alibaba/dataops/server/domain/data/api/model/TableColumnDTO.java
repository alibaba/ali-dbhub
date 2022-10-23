package com.alibaba.dataops.server.domain.data.api.model;

import com.alibaba.dataops.server.domain.data.api.enums.column.ColumnTypeEnum;
import com.alibaba.dataops.server.tools.base.enums.YesOrNoEnum;

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
public class TableColumnDTO {
    /**
     * 列名
     */
    private String name;

    /**
     * 列的类型
     *
     * @see ColumnTypeEnum
     */
    private String type;

    /**
     * 是否为空
     *
     * @see YesOrNoEnum
     */
    private String nullable;

    /**
     * 是否主键
     *
     * @see YesOrNoEnum
     */
    private String primary;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 是否自增
     *
     * @see YesOrNoEnum
     */
    private String autoIncrement;

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
