package com.alibaba.ctoo.opensource.domain.repository.mapper;

import java.util.List;

import com.alibaba.ctoo.opensource.domain.repository.dataobject.search.CustomArticleSearchParam;

import org.apache.ibatis.annotations.Mapper;


/**
 * 自定义ArticleMapper
 *
 * @author zyb
 */
@Mapper
public interface CustomArticleMapper {


    /**
     * 模拟搜索引擎查询
     * @param param
     * @return
     */
    List<Long> queryPageFromSearch(CustomArticleSearchParam param);

    /**
     * 模拟搜索引擎查询 统计
     * @param param
     * @return
     */
    long countQueryPageFromSearch(CustomArticleSearchParam param);



}
