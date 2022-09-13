package com.alibaba.ctoo.opensource.domain.repository.dao;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.domain.repository.mapper.ArticleVersionMapper;

import org.springframework.stereotype.Repository;

/**
 * 文章dao
 *
 * @author zyb
 */
@Repository
public class ArticleVersionDAO {

    @Resource
    private ArticleVersionMapper articleVersionMapper;



}
