package com.alibaba.ctoo.opensource.domain.api.service.article;

import java.util.List;

import com.alibaba.ctoo.opensource.domain.api.param.article.ArticlePageQueryFromSearchParam;
import com.alibaba.easytools.base.wrapper.result.ActionResult;
import com.alibaba.ctoo.opensource.domain.api.param.article.ArticleSelector;
import com.alibaba.ctoo.opensource.domain.api.model.ArticleDTO;
import com.alibaba.ctoo.opensource.domain.api.param.article.ArticleCreateParam;
import com.alibaba.ctoo.opensource.domain.api.param.article.ArticlePageQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.article.ArticleUpdateParam;
import com.alibaba.easytools.base.wrapper.result.DataResult;
import com.alibaba.easytools.base.wrapper.result.ListResult;
import com.alibaba.easytools.base.wrapper.result.PageResult;

/**
 * 文章接口
 *
 * @author zyb
 */
public interface ArticleDomainService {

    /**
     * 文章分页查询
     * @param param
     * @param selector
     * @return
     */
    PageResult<ArticleDTO> queryPage(ArticlePageQueryParam param, ArticleSelector selector);

    /**
     * 文章列表
     *
     * @param idList
     * @param selector
     * @return
     */
    ListResult<ArticleDTO> queryList(List<Long> idList, ArticleSelector selector);

    /**
     * 文章查询，预览专用
     *
     * @param id
     * @param selector
     * @return
     */
    DataResult<ArticleDTO> query(Long id, ArticleSelector selector);

    /**
     * 文章创建
     *
     * @param param
     * @return
     */
    DataResult<Long> create(ArticleCreateParam param);

    /**
     * 文章修改
     *
     * @param param
     * @return
     */
    DataResult<Long> update(ArticleUpdateParam param);

    /**
     * 文章删除
     *
     * @param id
     * @return
     */
    ActionResult delete(Long id);

    /**
     * 文章详情
     *
     * @param id
     * @param selector
     * @return
     */
    DataResult<ArticleDTO> get(Long id, ArticleSelector selector);

    /**
     * 去搜索引擎查询
     *
     * @param param
     * @param selector
     * @return
     */
    PageResult<ArticleDTO> queryPageFromSearch(ArticlePageQueryFromSearchParam param, ArticleSelector selector);




}
