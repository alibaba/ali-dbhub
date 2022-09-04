package com.alibaba.ctoo.opensource.domain.core.converter;

import com.alibaba.ctoo.opensource.domain.repository.dataobject.ArticleVersionDO;
import com.alibaba.ctoo.opensource.domain.api.enums.ActivatedEnum;
import com.alibaba.ctoo.opensource.domain.api.param.article.ArticleUpdateParam;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * 转换器
 * 这里要注意 这个转换器会忽略空在字段
 *
 * @author sunyongqiang
 */
@Mapper(componentModel = "spring", imports = {ActivatedEnum.class},
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class ArticleVersionCoreIgnoreNullConverter {

    /**
     * 转换器
     * @param target
     * @param param
     * @param creator
     */
    @Mappings({
        @Mapping(target = "activated", expression = "java(ActivatedEnum.ACTIVATED.getCode())"),
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "modifier", source = "creator"),
        @Mapping(target = "creator", source = "creator"),
    })
    public abstract void copy(@MappingTarget ArticleVersionDO target, ArticleUpdateParam param, String creator);
}
