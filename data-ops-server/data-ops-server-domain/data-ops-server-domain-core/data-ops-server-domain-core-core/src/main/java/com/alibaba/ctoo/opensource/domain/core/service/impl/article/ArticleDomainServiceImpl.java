package com.alibaba.ctoo.opensource.domain.core.service.impl.article;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.domain.api.service.article.ArticleDomainService;
import com.alibaba.ctoo.opensource.domain.core.converter.ArticleCoreConverter;

import org.springframework.stereotype.Service;

/**
 * 文章实现
 *
 * @author Jiaju Zhuang
 */
@Service
public class ArticleDomainServiceImpl implements ArticleDomainService {

    @Resource
    private ArticleCoreConverter articleCoreConverter;

}
