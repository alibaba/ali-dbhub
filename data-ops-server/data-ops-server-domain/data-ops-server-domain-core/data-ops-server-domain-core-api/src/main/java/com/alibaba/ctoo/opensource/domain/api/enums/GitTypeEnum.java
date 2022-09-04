package com.alibaba.ctoo.opensource.domain.api.enums;

import com.alibaba.easytools.base.enums.BaseEnum;

import lombok.Getter;

/**
 * git类型
 *
 * @author 知闰
 * @date 2022/03/22
 */
@Getter
public enum GitTypeEnum implements BaseEnum<String> {

    /**
     * github
     */
    GITHUB("github", "https://github.com/"),

    /**
     * gitee
     */
    GITEE("gitee", "https://gitee.com/");

    final String description;
    final String urlPrefix;

    GitTypeEnum(String description, String urlPrefix) {
        this.description = description;
        this.urlPrefix = urlPrefix;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
