package com.alibaba.ctoo.opensource.domain.api.param.article;

import java.util.Date;

import lombok.Data;

/**
 * 博客 活动 新闻修改
 *
 * @author hyh
 */
@Data
public class OperationArticleUpdateParam {

    /**
     * id
     */
    private Long id;

    /**
     * 文章id
     */
    private Long articleId;

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
