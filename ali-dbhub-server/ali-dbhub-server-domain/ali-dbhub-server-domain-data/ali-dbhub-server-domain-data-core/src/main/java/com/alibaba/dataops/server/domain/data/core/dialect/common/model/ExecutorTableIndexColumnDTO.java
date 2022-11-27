package com.alibaba.dbhub.server.domain.data.core.dialect.common.model;

import com.alibaba.dbhub.server.domain.data.api.enums.CollationEnum;

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
public class ExecutorTableIndexColumnDTO {
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

