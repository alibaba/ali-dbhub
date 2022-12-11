package com.alibaba.dbhub.server.domain.support.enums;

import com.alibaba.dbhub.server.tools.base.enums.BaseEnum;

import lombok.Getter;

/**
 * 数据类型
 *
 * @author Jiaju Zhuang
 */
@Getter
public enum DbTypeEnum implements BaseEnum<String> {
    /**
     * MySQL
     */
    MYSQL("MySQL", "com.mysql.cj.jdbc.Driver"),

    /**
     * PostgreSQL
     */
    POSTGRESQL("PostgreSQL", "org.postgresql.Driver"),

    /**
     * Oracle
     */
    ORACLE("Oracle", "oracle.jdbc.driver.OracleDriver"),

    /**
     * SQLServer
     */
    SQLSERVER("SQLServer", "com.microsoft.sqlserver.jdbc.SQLServerDriver"),

    /**
     * SQLite
     */
    SQLITE("SQLite", "org.sqlite.JDBC"),

    /**
     * H2
     */
    H2("H2", "org.h2.Driver"),
    ;

    final String description;
    final String className;

    DbTypeEnum(String description, String className) {
        this.description = description;
        this.className = className;
    }

    /**
     * 通过名称获取枚举
     *
     * @param name
     * @return
     */
    public static DbTypeEnum getByName(String name) {
        for (DbTypeEnum dbTypeEnum : DbTypeEnum.values()) {
            if (dbTypeEnum.name().equals(name)) {
                return dbTypeEnum;
            }
        }
        return null;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
