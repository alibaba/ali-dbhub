package com.alibaba.dbhub.server.domain.data.api.param.table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 表结构选择器
 *
 * @author Jiaju Zhuang
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class TableSelector {
    /**
     * 列列表
     */
    private Boolean columnList;

    /**
     * 索引列表
     */
    private Boolean indexList;
}
