package com.alibaba.dataops.server.domain.data.api.model;

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
     * 表名
     */
    private String tableName;

    /**
     * 列的类型
     * 比如 varchar(100) ,double(10,6)
     */
    private String columnType;

    /**
     * 列的数据类型
     * 比如 varchar ,double
     */
    private String dataType;

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
     * 注释
     */
    private String comment;
}
