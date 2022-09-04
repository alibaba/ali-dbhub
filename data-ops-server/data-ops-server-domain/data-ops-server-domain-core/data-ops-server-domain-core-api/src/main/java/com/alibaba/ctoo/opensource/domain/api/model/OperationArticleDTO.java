package com.alibaba.ctoo.opensource.domain.api.model;

import java.util.Date;

import com.alibaba.ctoo.opensource.domain.api.enums.ArticleTypeEnum;

import lombok.Data;

/**
 * 运营管理：博客 活动 新闻
 *
 * @author hyh
 */
@Data
public class OperationArticleDTO {

    /**
     * id
     */
    private Long id;

    /**
     * 图片地址
     */
    private String pictureUrl;

    /**
     * 开始时间
     */
    private Date gmtStart;

    /**
     * 结束时间
     */
    private Date gmtEnd;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 创建人
     */
    private UserDTO creator;

    /**
     * 修改人
     */
    private UserDTO modifier;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 文章
     */
    private ArticleDTO article;

    /**
     * 文章类型
     *
     * @see ArticleTypeEnum
     */
    private String type;

    /**
     * 排序
     */
    private Integer orderNum;
}
