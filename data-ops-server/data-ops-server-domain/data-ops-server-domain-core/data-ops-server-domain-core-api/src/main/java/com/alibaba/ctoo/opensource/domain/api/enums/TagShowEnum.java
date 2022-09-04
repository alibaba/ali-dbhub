package com.alibaba.ctoo.opensource.domain.api.enums;

import com.alibaba.easytools.base.enums.BaseEnum;

import lombok.Getter;

/**
 * tag是否展示枚举
 * @author 知闰
 * @date 2022/03/23
 */
@Getter
public enum TagShowEnum implements BaseEnum<String> {

    /**
     * 展示
     */
    YES("展示"),

    /**
     * 隐藏
     */
    NO("隐藏");

    final String description;

    TagShowEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
