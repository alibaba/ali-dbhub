package com.alibaba.dbhub.server.domain.support.model;

import com.alibaba.dbhub.server.domain.support.enums.CollationEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 列信息
 *
 * @author Jiaju Zhuang
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TableIndexColumn {
    /**
     * 列名
     */
    private String name;

    /**
     * 索引名字
     */
    private String indexName;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 排序
     *
     * @see CollationEnum
     */
    private String collation;
}

