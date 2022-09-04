package com.alibaba.ctoo.opensource.domain.api.enums;

import com.alibaba.easytools.base.enums.BaseEnum;

import lombok.Getter;

/**
 * 是否激活枚举
 *
 * @author 是仪
 */
@Getter
public enum ActivatedEnum implements BaseEnum<String> {
    /**
     * 激活
     */
    ACTIVATED("激活"),

    /**
     * 未激活
     */
    UNACTIVATED("未激活"),

    ;

    final String description;

    ActivatedEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
