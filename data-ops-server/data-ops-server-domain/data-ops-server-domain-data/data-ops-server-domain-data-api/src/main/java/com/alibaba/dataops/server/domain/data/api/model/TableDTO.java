package com.alibaba.dataops.server.domain.data.api.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 表信息
 *
 * @author Jiaju Zhuang
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class TableDTO {
    /**
     * 表名
     */
    private String name;

    /**
     * 列列表
     */
    private List<TableColumnDTO> columnList;

    /**
     * 索引列表
     */
    private List<TableIndexDTO> indexList;
}
