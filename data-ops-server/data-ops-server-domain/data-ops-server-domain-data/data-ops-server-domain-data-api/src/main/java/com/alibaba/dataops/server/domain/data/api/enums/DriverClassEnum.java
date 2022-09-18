package com.alibaba.dataops.server.domain.data.api.enums;

import com.alibaba.dataops.server.tools.base.enums.BaseEnum;

import lombok.Getter;

/**
 * 驱动类枚举
 *
 * @author Jiaju Zhuang
 */
@Getter
public enum DriverClassEnum implements BaseEnum<String> {
    /**
     * MySQL
     */
    MYSQL("MySQL", "com.mysql.jdbc.Driver"),

    /**
     * Oracle
     */
    ORACLE("Oracle", "oracle.jdbc.driver.OracleDriver"),

    /**
     * SQLite
     */
    SQLITE("SQLite", "org.sqlite.JDBC"),
    ;

    final String description;
    final String className;

    DriverClassEnum(String description, String className) {
        this.description = description;
        this.className = className;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
