package com.alibaba.ctoo.opensource.domain.api.param.article;

import lombok.Data;

/**
 * 文章创建参数
 *
 * @author zyb
 */
@Data
public class ArticleCreateParam {

    /**
     * 文章类型
     */
    private String type;

    /**
     * 标题
     */
    private String title;

    /**
     * 封面
     */
    private String coverUrl;

    /**
     * 作者
     */
    private String author;

    /**
     * 文字摘要
     */
    private String description;

}
