package com.alibaba.ctoo.opensource.domain.api.enums;

import com.alibaba.easytools.base.enums.BaseEnum;

import lombok.Getter;

/**
 * 文章搜索类型
 *
 * @author zyb
 */
@Getter
public enum ArticleSearchTypeEnum implements BaseEnum<String> {

    /**
     * 博客
     */
    BLOG("博客"),

    /**
     * 新闻
     */
    NEWS("新闻"),

    /**
     * 活动
     */
    ACTIVITY("活动"),

    /**
     * 全部
     */
    ALL("全部"),

    ;

    final String description;

    ArticleSearchTypeEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }

}
