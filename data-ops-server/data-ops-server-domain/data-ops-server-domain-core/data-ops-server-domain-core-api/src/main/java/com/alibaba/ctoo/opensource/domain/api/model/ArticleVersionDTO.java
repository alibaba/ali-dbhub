package com.alibaba.ctoo.opensource.domain.api.model;

import lombok.Data;

/**
 * 文章版本
 *
 * @author sunyongqiang
 */
@Data
public class ArticleVersionDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 封面url
     */
    private String coverUrl;

    /**
     * 作者
     */
    private String author;

    /**
     * 内容富文本id
     */
    private Long contentRichTextId;

    /**
     * 富文本
     */
    private RichTextDTO contentRichText;

    /**
     * 文章类型
     * @see com.alibaba.ctoo.opensource.domain.api.enums.ArticleTypeEnum
     */
    private String type;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 是否激活
     * @see com.alibaba.ctoo.opensource.domain.api.enums.ActivatedEnum
     */
    private String activated;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 文章摘要
     */
    private String description;

}
