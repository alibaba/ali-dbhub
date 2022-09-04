package com.alibaba.ctoo.opensource.domain.api.param.article;

import com.alibaba.ctoo.opensource.domain.api.enums.ArticleTypeEnum;

import lombok.Data;

/**
 * 博客 活动 新闻查询
 *
 * @author hyh
 */
@Data
public class OperationArticleQueryParam {

    /**
     * id
     */
    private Long id;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 文章类型
     *
     * @see ArticleTypeEnum
     */
    private String type;

}
