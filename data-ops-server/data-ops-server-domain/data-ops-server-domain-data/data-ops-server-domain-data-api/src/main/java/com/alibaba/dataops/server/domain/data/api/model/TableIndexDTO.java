package com.alibaba.dataops.server.domain.data.api.model;

import java.util.List;

import com.alibaba.dataops.server.domain.data.api.enums.IndexTypeEnum;
import com.alibaba.dataops.server.tools.base.enums.YesOrNoEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 索引信息
 *
 * @author Jiaju Zhuang
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class TableIndexDTO {
    /**
     * 索引名称
     */
    private String name;

    /**
     * 是否主键
     *
     * @see YesOrNoEnum
     */
    private String primary;

    /**
     * 所以类型
     *
     * @see IndexTypeEnum
     */
    private String type;

    /**
     * 注释
     */
    private String comment;

    /**
     * 索引包含的列
     */
    private List<TableIndexColumnDTO> columnList;
}
