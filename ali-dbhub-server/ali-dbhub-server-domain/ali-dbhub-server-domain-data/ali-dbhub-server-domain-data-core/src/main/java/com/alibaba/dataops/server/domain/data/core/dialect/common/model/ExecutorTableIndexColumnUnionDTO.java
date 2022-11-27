package com.alibaba.dbhub.server.domain.data.core.dialect.common.model;

import com.alibaba.dbhub.server.domain.data.api.enums.CollationEnum;
import com.alibaba.dbhub.server.domain.data.api.enums.IndexTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 索引+列
 *
 * @author Jiaju Zhuang
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class ExecutorTableIndexColumnUnionDTO {

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

