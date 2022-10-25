package com.alibaba.dataops.server.domain.data.core.dialect.common.model;

import com.alibaba.dataops.server.domain.data.api.enums.ColumnTypeEnum;
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
public class ExecutorTableColumnDTO {
    /**
     * 列名
     */
    private String name;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 列的类型
     *
     * @see ColumnTypeEnum
     */
    private String type;

    /**
     * 是否可以为空
     *
     * @see YesOrNoEnum
     */
    private String nullable;

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

    /**
     * 排序
     */
    private Integer ordinalPosition;
}

