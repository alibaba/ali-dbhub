package com.alibaba.ctoo.opensource.domain.api.service.article;

import java.util.List;

import com.alibaba.ctoo.opensource.domain.api.model.OperationArticleDTO;
import com.alibaba.ctoo.opensource.domain.api.param.article.OperationArticleCreateParam;
import com.alibaba.ctoo.opensource.domain.api.param.article.OperationArticlePageQueryFromSearchParam;
import com.alibaba.ctoo.opensource.domain.api.param.article.OperationArticlePageQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.article.OperationArticleQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.article.OperationArticleSelector;
import com.alibaba.ctoo.opensource.domain.api.param.article.OperationArticleUpdateParam;
import com.alibaba.easytools.base.wrapper.result.ActionResult;
import com.alibaba.easytools.base.wrapper.result.DataResult;
import com.alibaba.easytools.base.wrapper.result.ListResult;
import com.alibaba.easytools.base.wrapper.result.PageResult;

/**
 * 运营管理/运营管理
 * 包含 博客 活动 新闻
 *
 * @author hyh
 */
public interface OperationArticleManageDomainService {

    /**
     * 列表分页查询
     *
     * @param param    param
     * @param selector selector
     * @return
     */
    PageResult<OperationArticleDTO> queryPage(OperationArticlePageQueryParam param, OperationArticleSelector selector);

    /**
     * 根据type查询有效数据
     *
     * @param param
     * @param selector
     * @return
     */
    PageResult<OperationArticleDTO> queryPageFromSearch(OperationArticlePageQueryFromSearchParam param,
        OperationArticleSelector selector);

    /**
     * 博客 活动 新闻列表
     *
     * @param param    param
     * @param selector selector
     * @return
     */
    ListResult<OperationArticleDTO> queryList(OperationArticleQueryParam param, OperationArticleSelector selector);

    /**
     * 列表查询
     *
     * @param idList   idList
     * @param selector selector
     * @return
     */
    ListResult<OperationArticleDTO> queryList(List<Long> idList, OperationArticleSelector selector);

    /**
     * 博客 活动 新闻详情
     *
     * @param id       id
     * @param selector selector
     * @return
     */
    DataResult<OperationArticleDTO> get(Long id, OperationArticleSelector selector);

    /**
     * 创建博客 活动 新闻
     *
     * @param param param
     * @return
     */
    ActionResult create(OperationArticleCreateParam param);

    /**
     * 修改博客 活动 新闻
     *
     * @param param param
     * @return
     */
    ActionResult update(OperationArticleUpdateParam param);

    /**
     * 删除博客 活动 新闻
     *
     * @param id id
     * @return
     */
    ActionResult delete(Long id);
}
