package com.alibaba.ctoo.opensource.domain.api.enums;

import com.alibaba.easytools.base.enums.BaseEnum;

import lombok.Getter;

/**
 * @author qiuyuyu
 * @date 2022/03/08
 */
@Getter
public enum SystemTypeEnum implements BaseEnum<String> {

    /**
     * 后台管理系统
     */
    ADMIN("后台管理系统"),

    ;
    final String description;

    SystemTypeEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
