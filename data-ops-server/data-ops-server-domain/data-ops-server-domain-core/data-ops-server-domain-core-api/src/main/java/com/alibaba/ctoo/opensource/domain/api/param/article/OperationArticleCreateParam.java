package com.alibaba.ctoo.opensource.domain.api.param.article;

import java.util.Date;

import com.alibaba.ctoo.opensource.domain.api.enums.ArticleTypeEnum;

import lombok.Data;

/**
 * 博客 活动 新闻创建
 *
 * @author hyh
 */
@Data
public class OperationArticleCreateParam {

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

    /**
     * 开始时间
     */
    private Date gmtStart;

    /**
     * 结束时间
     */
    private Date gmtEnd;

    /**
     * 排序
     */
    private Integer orderNum;
}
