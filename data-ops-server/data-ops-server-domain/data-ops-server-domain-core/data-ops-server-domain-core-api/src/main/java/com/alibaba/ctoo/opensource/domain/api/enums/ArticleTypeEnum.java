package com.alibaba.ctoo.opensource.domain.api.enums;

import com.alibaba.easytools.base.enums.BaseEnum;
import com.alibaba.fastvalidator.constraints.utils.StringUtils;

import lombok.Getter;

/**
 * 文字类型
 *
 * @author 是仪
 */
@Getter
public enum ArticleTypeEnum implements BaseEnum<String> {
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

    ;

    final String description;

    ArticleTypeEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    public final static String getDescription(String code) {
        ArticleTypeEnum[] enums = ArticleTypeEnum.values();
        for (ArticleTypeEnum articleTypeEnum : enums) {
            if (StringUtils.equals(code, articleTypeEnum.getCode())) {
                return articleTypeEnum.description;
            }
        }
        return null;
    }
}
