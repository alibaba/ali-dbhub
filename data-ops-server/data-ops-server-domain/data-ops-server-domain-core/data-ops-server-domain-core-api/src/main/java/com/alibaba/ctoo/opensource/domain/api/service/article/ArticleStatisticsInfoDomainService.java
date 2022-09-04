package com.alibaba.ctoo.opensource.domain.api.service.article;


import com.alibaba.ctoo.opensource.domain.api.model.ArticleStatisticsInfoDTO;
import com.alibaba.easytools.base.wrapper.result.ActionResult;
import com.alibaba.easytools.base.wrapper.result.DataResult;

/**
 * 文章统计接口
 *
 * @author zyb
 */
public interface ArticleStatisticsInfoDomainService {

    /**
     * 文章阅读量+1
     *
     * @param articleId
     * @return
     */
    ActionResult increase(Long articleId);

    /**
     * 文章阅读量创建
     *
     * @param articleId
     * @return
     */
    ActionResult create(Long articleId);

    /**
     * 查询某篇文章的阅读量
     *
     * @param articleId
     * @return
     */
    DataResult<ArticleStatisticsInfoDTO> query(Long articleId);



}
