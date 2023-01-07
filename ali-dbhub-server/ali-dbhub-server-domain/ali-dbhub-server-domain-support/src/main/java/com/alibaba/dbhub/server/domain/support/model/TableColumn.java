package com.alibaba.dbhub.server.domain.support.model;

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
public class TableColumn {
    /**
     * 旧的列名，在修改列的时候需要这个参数
     * 在返回的时候oldName=name
     */
    private String oldName;

    /**
     * 列名
     */
    private String name;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 列的类型
     * 比如 varchar(100) ,double(10,6)
     */
    private String columnType;

    /**
     * 列的数据类型
     * 比如 varchar ,double
     */
    private String dataType;

    /**
     * 是否可以为空
     * 为空 代表没有值 数据库的实际语义是 true
     */
    private Boolean nullable;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 是否自增
     * 为空 代表没有值 数据库的实际语义是 false
     */
    private Boolean autoIncrement;

    /**
     * 注释
     */
    private String comment;

    /**
     * 是否主键
     */
    private Boolean primaryKey;
}
