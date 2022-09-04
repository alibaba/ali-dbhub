package com.alibaba.ctoo.opensource.domain.api.enums;

import com.alibaba.easytools.base.enums.BaseEnum;

import lombok.Getter;

/**
 * 展示枚举
 * @author 知闰
 * @date 2022/03/23
 */
@Getter
public enum ShowEnum implements BaseEnum<String> {

    /**
     * 项目展示
     */
    YES("展示"),

    /**
     * 项目不展示
     */
    NO("不展示");

    final String description;

    ShowEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
