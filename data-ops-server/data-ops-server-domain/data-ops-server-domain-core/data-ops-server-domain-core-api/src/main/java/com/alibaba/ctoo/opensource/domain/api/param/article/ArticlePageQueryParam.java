package com.alibaba.ctoo.opensource.domain.api.param.article;

import com.alibaba.ctoo.opensource.domain.api.enums.ArticleStatusEnum;
import com.alibaba.ctoo.opensource.domain.api.enums.ArticleTypeEnum;
import com.alibaba.easytools.base.wrapper.param.PageQueryParam;

import lombok.Data;

/**
 * 文章分页查询参数
 *
 * @author zyb
 */
@Data
public class ArticlePageQueryParam extends PageQueryParam {

    /**
     * 搜索关键字
     */
    private String searchKey;

    /**
     * 状态
     *
     * @see ArticleStatusEnum
     */
    private String status;

    /**
     * 文章类型
     *
     * @see ArticleTypeEnum
     */
    private String type;

}
