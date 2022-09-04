package com.alibaba.ctoo.opensource.domain.api.enums;

import com.alibaba.easytools.base.enums.BaseEnum;

import lombok.Getter;

/**
 * 文章搜索状态
 *
 * @author zyb
 */
@Getter
public enum ArticleSearchStatusEnum implements BaseEnum<String>  {

    /**
     * 草稿
     */
    DRAFT("草稿"),

    /**
     * 已发布
     */
    RELEASE("已发布"),

    /**
     * 全部
     */
    ALL("全部"),

    ;

    final String description;

    ArticleSearchStatusEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }

}
