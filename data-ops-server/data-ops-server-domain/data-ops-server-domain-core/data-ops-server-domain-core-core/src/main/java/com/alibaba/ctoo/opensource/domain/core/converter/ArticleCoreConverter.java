package com.alibaba.ctoo.opensource.domain.core.converter;

import org.mapstruct.Mapper;

/**
 * 文章转换器
 *
 * @author zyb
 */
@Mapper(componentModel = "spring")
public abstract class ArticleCoreConverter {

    ///**
    // * 文章添加转换
    // *
    // * @param param
    // * @param context
    // * @return
    // */
    //@Mappings({
    //    @Mapping(target = "creator", source = "context.userId"),
    //    @Mapping(target = "modifier", source = "context.userId"),
    //    @Mapping(target = "tenantId", source = "context.tenantId"),
    //    @Mapping(target = "status", expression = "java(ArticleStatusEnum.DRAFT.getCode())"),
    //    @Mapping(target = "gmtCustomModified", source = "current"),
    //    @Mapping(target = "customModifier", source = "context.userId")
    //})
    //public abstract ArticleDO param2do(ArticleCreateParam param, Context context, Date current);

}
