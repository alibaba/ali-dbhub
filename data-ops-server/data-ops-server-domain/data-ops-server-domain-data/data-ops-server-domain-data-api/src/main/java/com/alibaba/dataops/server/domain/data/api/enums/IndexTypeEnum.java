package com.alibaba.dataops.server.domain.data.api.enums;

import com.alibaba.dataops.server.tools.base.enums.BaseEnum;

import lombok.Getter;

/**
 * 索引类型
 *
 * @author Jiaju Zhuang
 */
@Getter
public enum IndexTypeEnum implements BaseEnum<String> {
    /**
     * 普通索引
     */
    NORMAL("普通索引"),

    /**
     * 唯一索引
     */
    UNIQUE("唯一索引"),
    ;

    final String description;

    IndexTypeEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
