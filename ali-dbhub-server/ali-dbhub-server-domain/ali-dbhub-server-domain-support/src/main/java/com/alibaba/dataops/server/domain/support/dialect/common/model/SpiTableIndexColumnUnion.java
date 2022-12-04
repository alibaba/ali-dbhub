package com.alibaba.dataops.server.domain.support.dialect.common.model;

import com.alibaba.dataops.server.domain.support.enums.CollationEnum;
import com.alibaba.dataops.server.domain.support.enums.IndexTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 索引+列
 *
 * @author Jiaju Zhuang
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SpiTableIndexColumnUnion {

    /**
     * 索引名称
     */
    private String indexName;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 索引类型
     *
     * @see IndexTypeEnum
     */
    private String type;

    /**
     * 注释
     */
    private String comment;

    /**
     * 列名
     */
    private String columnName;

    /**
     * 顺序
     */
    private Long ordinalPosition;

    /**
     * 排序
     *
     * @see CollationEnum
     */
    private String collation;
}

