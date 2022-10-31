package com.alibaba.dataops.server.test.domain.data.service.dialect;

import java.util.Date;

import com.alibaba.dataops.server.domain.data.api.enums.DbTypeEnum;

/**
 * 方言配置
 *
 * @author Jiaju Zhuang
 */
public interface DialectProperties {

    /**
     * 支持的数据库类型
     *
     * @return
     */
    DbTypeEnum getDbType();

    /**
     * 连接
     *
     * @return
     */
    String getUrl();

    /**
     * 用户名
     *
     * @return
     */

    String getUsername();

    /**
     * 密码
     *
     * @return
     */
    String getPassword();

    /**
     * 数据库名称
     *
     * @return
     */
    String getDatabaseName();

    /**
     * 创建表表结构 : 测试表
     * 字段：
     * ID   主键自增
     * DATE 日期 非空
     * NUMBER 长整型
     * STRING  字符串 长度100 默认值 "DATA"
     *
     * 索引：
     * IDX_DATE 日期索引 倒序
     * UK_NUMBER 唯一索引
     * IDX_NUMBER_STRING 联合索引
     *
     * @return
     */
    String getCrateTableSql(String tableName);

    /**
     * 创建表表结构
     *
     * @return
     */
    String getDropTableSql(String tableName);

    /**
     * 创建一条数据
     *
     * @return
     */
    String getInsertSql(String tableName, Date date, Long number, String string);

    /**
     * 查询一条查询sql
     *
     * @return
     */
    String getSelectSqlById(String tableName, Long id);
}
