package com.alibaba.dataops.server.domain.data.api.model;

import com.alibaba.dataops.server.domain.data.api.enums.CollationEnum;

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
public class TableIndexColumnDTO {
    /**
     * 列名
     */
    private String name;

    /**
     * 排序
     *
     * @see CollationEnum
     */
    private String collation;
}

