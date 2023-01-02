package com.alibaba.dbhub.server.domain.support.enums;

import com.alibaba.dbhub.server.tools.base.enums.BaseEnum;
import com.alibaba.druid.sql.ast.SQLOrderingSpecification;

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
    ASC("asc", SQLOrderingSpecification.ASC),

    /**
     * DESC
     */
    DESC("desc", SQLOrderingSpecification.DESC),

    ;

    final String description;

    final SQLOrderingSpecification sqlOrderingSpecification;

    CollationEnum(String description, SQLOrderingSpecification sqlOrderingSpecification) {
        this.description = description;
        this.sqlOrderingSpecification = sqlOrderingSpecification;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
