package com.alibaba.dataops.server.domain.data.core.dialect.common.model;

import java.util.List;

import com.alibaba.dataops.server.domain.data.api.enums.IndexTypeEnum;
import com.alibaba.dataops.server.domain.data.core.dialect.common.model.ExecutorTableIndexColumnDTO;

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
public class ExecutorTableIndexDTO {

    /**
     * 索引名称
     */
    private String name;

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
     * 索引包含的列
     */
    private List<ExecutorTableIndexColumnDTO> columnList;
}
