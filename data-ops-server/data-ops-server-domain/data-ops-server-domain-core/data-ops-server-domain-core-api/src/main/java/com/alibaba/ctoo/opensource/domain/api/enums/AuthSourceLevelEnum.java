package com.alibaba.ctoo.opensource.domain.api.enums;

import com.alibaba.easytools.base.enums.BaseEnum;

import lombok.Getter;

/**
 * @author qiuyuyu
 * @date 2022/03/07
 */
@Getter
public enum AuthSourceLevelEnum implements BaseEnum<String> {
    /**
     * 顶级 每个项目一个
     */
    L0("顶级"),

    /**
     * 一级
     */
    L1("一级"),

    /**
     * 二级
     */
    L2("二级"),

    /**
     * 通用权限（默认任何管理员都拥有）
     */
    COMMON("通用权限"),

    ;

    String description;

    AuthSourceLevelEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
