package com.alibaba.dataops.server.domain.data.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 标签索引列名称
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
}
