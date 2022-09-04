package com.alibaba.ctoo.opensource.domain.repository.dataobject.search;

import com.alibaba.ctoo.opensource.domain.api.enums.ArticleStatusEnum;
import com.alibaba.ctoo.opensource.domain.api.enums.ArticleTypeEnum;

import lombok.Data;

/**
 * 自定义文章查询参数
 *
 * @author zyb
 */
@Data
public class CustomArticleSearchParam extends BaseParam {

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
