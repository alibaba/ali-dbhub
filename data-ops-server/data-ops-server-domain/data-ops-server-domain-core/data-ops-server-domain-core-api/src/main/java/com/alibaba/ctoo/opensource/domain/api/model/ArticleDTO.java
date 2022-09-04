package com.alibaba.ctoo.opensource.domain.api.model;

import java.util.Date;

import lombok.Data;

/**
 * 文章DTO
 *
 * @author zyb
 */
@Data
public class ArticleDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 状态
     * @see com.alibaba.ctoo.opensource.domain.api.enums.ArticleStatusEnum
     */
    private String status;

    /**
     * 激活的版本id
     */
    private Long activatedArticleVersionId;

    /**
     * 激活的版本
     */
    private ArticleVersionDTO activatedArticleVersion;

    /**
     * 上架时间
     */
    private Date gmtRelease;

    /**
     * 用户修改时间
     */
    private Date gmtCustomModified;


}
