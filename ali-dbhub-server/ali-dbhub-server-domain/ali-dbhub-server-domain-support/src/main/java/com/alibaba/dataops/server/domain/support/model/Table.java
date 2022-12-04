package com.alibaba.dataops.server.domain.support.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 表信息
 *
 * @author Jiaju Zhuang
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Table {
    /**
     * 表名
     */
    private String name;


    /**
     * 描述
     */
    private String comment;

    /**
     * 列列表
     */
    private List<TableColumn> columnList;

    /**
     * 索引列表
     */
    private List<TableIndex> indexList;

    /**
     * 建表语句
     * 暂时不支持
     */
    private String createSql;
}

