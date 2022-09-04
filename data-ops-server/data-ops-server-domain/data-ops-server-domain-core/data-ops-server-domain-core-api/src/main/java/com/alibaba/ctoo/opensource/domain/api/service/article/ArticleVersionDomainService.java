package com.alibaba.ctoo.opensource.domain.api.service.article;

import java.util.List;

import com.alibaba.ctoo.opensource.domain.api.model.ArticleVersionDTO;
import com.alibaba.ctoo.opensource.domain.api.param.article.ArticleVersionSelector;
import com.alibaba.easytools.base.wrapper.result.ListResult;

/**
 * 文章版本服务
 * @author sunyongqiang
 */
public interface ArticleVersionDomainService {
    /**
     * 文章版本列表
     *
     * @param idList
     * @param selector
     * @return
     */
    ListResult<ArticleVersionDTO> queryList(List<Long> idList, ArticleVersionSelector selector);
}
