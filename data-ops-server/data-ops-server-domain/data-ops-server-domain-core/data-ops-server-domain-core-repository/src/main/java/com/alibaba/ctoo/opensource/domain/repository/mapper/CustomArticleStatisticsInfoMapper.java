package com.alibaba.ctoo.opensource.domain.repository.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * 自定义mapper
 *
 * @author zyb
 */
@Mapper
public interface CustomArticleStatisticsInfoMapper {

    /**
     * 文章阅读量+1
     *
     * @param articleId
     * @return
     */
    int doIncrease(Long articleId);
}
