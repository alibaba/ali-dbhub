package com.alibaba.ctoo.opensource.domain.core.converter;

import java.util.Date;
import java.util.List;

import com.alibaba.ctoo.opensource.domain.api.enums.ArticleStatusEnum;
import com.alibaba.ctoo.opensource.domain.api.param.article.ArticlePageQueryFromSearchParam;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ArticleDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ArticleVersionDO;
import com.alibaba.ctoo.opensource.common.model.Context;
import com.alibaba.ctoo.opensource.domain.api.model.ArticleDTO;
import com.alibaba.ctoo.opensource.domain.api.param.article.ArticleCreateParam;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.search.CustomArticleSearchParam;

import cn.hutool.core.date.DateUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

/**
 * 文章转换器
 *
 * @author zyb
 */
@Mapper(componentModel = "spring", imports = {ArticleStatusEnum.class, DateUtil.class})
public abstract class ArticleCoreConverter {

    /**
     * 文章添加转换
     *
     * @param param
     * @param context
     * @return
     */
    @Mappings({
        @Mapping(target = "creator", source = "context.userId"),
        @Mapping(target = "modifier", source = "context.userId"),
        @Mapping(target = "tenantId", source = "context.tenantId"),
        @Mapping(target = "status", expression = "java(ArticleStatusEnum.DRAFT.getCode())"),
        @Mapping(target = "gmtCustomModified", source = "current"),
        @Mapping(target = "customModifier", source = "context.userId")
    })
    public abstract ArticleDO param2do(ArticleCreateParam param, Context context, Date current);

    /**
     * 文章version添加转换
     *
     * @param param
     * @param context
     * @return
     */
    @Mappings({
        @Mapping(target = "creator", source = "context.userId"),
        @Mapping(target = "modifier", source = "context.userId"),
        @Mapping(target = "tenantId", source = "context.tenantId")
    })
    public abstract ArticleVersionDO param2versionDO(ArticleCreateParam param, Context context);

    /**
     * List<ArticleDO>2List<ArticleDTO>
     *
     * @param doList
     * @return
     */
    public abstract List<ArticleDTO> doList2dtoList(List<ArticleDO> doList);

    /**
     * 转换
     *
     * @param target
     * @param source
     */
    public abstract void addData(@MappingTarget ArticleDTO target, ArticleDTO source);

    /**
     * 文章转换
     *
     * @param data
     * @return
     */
    @Mappings({
        @Mapping(target = "activatedArticleVersion.id", source = "activatedArticleVersionId")
    })
    public abstract ArticleDTO do2dto(ArticleDO data);

    /**
     * 文章列表转换
     *
     * @param list
     * @return
     */
    public abstract List<ArticleDTO> do2dto(List<ArticleDO> list);

    /**
     * 转换
     *
     * @param param
     * @return
     */
    public abstract CustomArticleSearchParam param2custom(ArticlePageQueryFromSearchParam param);

}
