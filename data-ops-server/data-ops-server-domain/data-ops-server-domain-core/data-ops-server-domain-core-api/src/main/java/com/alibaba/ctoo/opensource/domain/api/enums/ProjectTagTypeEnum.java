package com.alibaba.ctoo.opensource.domain.api.enums;

import com.alibaba.easytools.base.enums.BaseEnum;

import lombok.Getter;

/**
 * 项目标签类型
 *
 * @author 是仪
 */
@Getter
public enum ProjectTagTypeEnum implements BaseEnum<String> {

    /**
     * 基础（心选、特色、自主开源、已经捐献等）
     */
    PROJECT_BASIS("基础"),

    /**
     * 领域
     */
    PROJECT_DOMAIN("领域"),

    /**
     * 语言
     */
    PROJECT_LANGUAGE("语言"),
    ;

    final String description;

    ProjectTagTypeEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
