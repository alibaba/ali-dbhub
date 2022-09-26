package com.alibaba.dataops.server.domain.data.api.enums;

import java.util.Objects;

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

    /**
     * H2
     */
    H2("H2", "org.h2.Driver"),
    ;

    final String description;
    final String className;

    DriverClassEnum(String description, String className) {
        this.description = description;
        this.className = className;
    }

    /**
     * 通过名称获取枚举
     *
     * @param name
     * @return
     */
    public static DriverClassEnum getByName(String name) {
        for (DriverClassEnum driverClassEnum : DriverClassEnum.values()) {
            if (driverClassEnum.name().equals(name)) {
                return driverClassEnum;
            }
        }
        return null;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
