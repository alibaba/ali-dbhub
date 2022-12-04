package com.alibaba.dbhub.server.domain.support.enums;

import com.alibaba.dbhub.server.tools.base.enums.BaseEnum;

import lombok.Getter;

/**
 * 排序枚举
 *
 * @author Jiaju Zhuang
 */
@Getter
public enum CollationEnum implements BaseEnum<String> {
    /**
     * ASC
     */
    ASC("asc"),

    /**
     * DESC
     */
    DESC("desc"),

    ;

    final String description;

    CollationEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
