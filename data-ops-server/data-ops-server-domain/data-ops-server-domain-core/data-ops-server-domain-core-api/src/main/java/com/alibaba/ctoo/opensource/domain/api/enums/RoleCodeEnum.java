package com.alibaba.ctoo.opensource.domain.api.enums;

import com.alibaba.easytools.base.enums.BaseEnum;

import lombok.Getter;

/**
 * 管理员类型
 *
 * @author 是仪
 */
@Getter
public enum RoleCodeEnum implements BaseEnum<String> {
    /**
     * 超级管理员
     */
    SUPER_ADMIN("超级管理员"),
    /**
     * 管理员
     */
    ADMIN("管理员");

    final String description;

    RoleCodeEnum(String description) {
        this.description = description;
    }

    public static String getByCode(String code) {
        for (RoleCodeEnum roleCodeEnum : RoleCodeEnum.values()) {
            if (roleCodeEnum.getCode().equals(code)) {
                return roleCodeEnum.getDescription();
            }
        }
        return null;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
