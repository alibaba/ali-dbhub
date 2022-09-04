
package com.alibaba.ctoo.opensource.domain.api.enums;

import com.alibaba.easytools.base.enums.BaseEnum;

import lombok.Getter;

/**
 * 文档源代码
 *
 * @author 是仪
 */
@Getter
public enum DocumentSourceTypeEnum implements BaseEnum<String> {
    /**
     * git
     */
    GIT("git"),

    /**
     * 网站
     */
    WEBSITE("网站"),

    ;

    final String description;

    DocumentSourceTypeEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
