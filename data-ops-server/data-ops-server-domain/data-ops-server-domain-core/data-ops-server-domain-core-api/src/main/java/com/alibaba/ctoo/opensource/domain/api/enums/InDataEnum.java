package com.alibaba.ctoo.opensource.domain.api.enums;

import com.alibaba.easytools.base.enums.BaseEnum;

import lombok.Getter;

/**
 * 是否加入主页数据展示枚举
 * @author 知闰
 * @date 2022/03/23
 */
@Getter
public enum InDataEnum implements BaseEnum<String> {

    /**
     * 加入数据统计
     */
    YES("加入"),

    /**
     * 不加入数据统计
     */
    NO("不加入");

    final String description;

    InDataEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
