package com.alibaba.ctoo.opensource.domain.api.model;

import lombok.Data;

/**
 * 文章阅读量DTO
 *
 * @author zyb
 */
@Data
public class ArticleStatisticsInfoDTO {

    /**
     * 数据主键
     */
    private Long id;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 阅读量
     */
    private Long readCount;


}
