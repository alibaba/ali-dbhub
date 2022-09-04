package com.alibaba.ctoo.opensource.domain.repository.dataobject.search;

import com.alibaba.ctoo.opensource.domain.api.enums.ArticleTypeEnum;

import lombok.Data;

/**
 * 自定义查询参数
 *
 * @author hyh
 */
@Data
public class CustomOperationArticleSearchParam extends BaseParam {

    /**
     * 文章类型
     *
     * @see ArticleTypeEnum
     */
    private String type;

    /**
     * id
     */
    private Long idNotEquals;
}
