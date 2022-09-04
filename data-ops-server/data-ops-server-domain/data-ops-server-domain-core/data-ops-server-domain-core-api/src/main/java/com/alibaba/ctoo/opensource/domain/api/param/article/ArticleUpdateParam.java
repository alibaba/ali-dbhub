package com.alibaba.ctoo.opensource.domain.api.param.article;

import lombok.Data;
import com.alibaba.ctoo.opensource.domain.api.param.resource.RichTextCreateOrUpdateParam;

/**
 * 文章修改参数
 *
 * @author zyb
 */
@Data
public class ArticleUpdateParam {

    /**
     * id
     */
    private Long id;

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
     * 内容
     */
    private RichTextCreateOrUpdateParam contentRichText;

    /**
     * 文字摘要
     */
    private String description;

    /**
     * 操作枚举
     *
     * @see com.alibaba.ctoo.opensource.domain.api.enums.OperationEnum
     */
    private String operation;

    /**
     * 文章状态
     *
     * @see com.alibaba.ctoo.opensource.domain.api.enums.ArticleStatusEnum
     */
    private String status;

    /**
     * 文章类型
     * @see com.alibaba.ctoo.opensource.domain.api.enums.ArticleTypeEnum
     */
    private String type;

}
