package com.alibaba.dataops.server.domain.data.api.enums;

import com.alibaba.dataops.server.tools.base.enums.BaseEnum;

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
    MYSQL("MySQL", "com.mysql.jdbc.Driver"),

    /**
     * Oracle
     */
    ORACLE("Oracle", "oracle.jdbc.driver.OracleDriver"),

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
