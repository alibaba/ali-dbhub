package com.alibaba.ctoo.opensource.domain.core.converter;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.domain.repository.dataobject.ArticleVersionDO;
import com.alibaba.ctoo.opensource.domain.api.model.ArticleVersionDTO;
import com.alibaba.ctoo.opensource.domain.api.param.article.ArticleVersionSelector;
import com.alibaba.ctoo.opensource.domain.api.service.article.ArticleVersionDomainService;

import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

/**
 * 文章版本选择器
 *
 * @author sunyongqiang
 */
@Mapper(componentModel = "spring")
public abstract class ArticleVersionCoreConverter {


    @Resource
    private ArticleVersionDomainService articleVersionDomainService;

    /**
     * 文章版本转换
     *
     * @param data
     * @return
     */
    @Mappings({
        @Mapping(target = "contentRichText.id", source = "contentRichTextId")
    })
    public abstract ArticleVersionDTO do2dto(ArticleVersionDO data);

    /**
     * 文章版本列表转换
     *
     * @param list
     * @return
     */
    public abstract List<ArticleVersionDTO> do2dto(List<ArticleVersionDO> list);


    public void fillDetail(List<ArticleVersionDTO> list, ArticleVersionSelector selector){
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        List<Long> idList = list.stream().map(ArticleVersionDTO::getId).collect(Collectors.toList());
        List<ArticleVersionDTO> dataList = articleVersionDomainService.queryList(idList, selector).getData();
        Map<Long, ArticleVersionDTO> dataMap = dataList.stream().collect(Collectors.toMap(ArticleVersionDTO::getId, Function
            .identity()));
        for (ArticleVersionDTO data : list) {
            if (data == null || data.getId() == null) {
                continue;
            }
            ArticleVersionDTO queryData = dataMap.get(data.getId());
            if (queryData == null) {
                continue;
            }
            addData(data, queryData);
        }
    }

    /**
     * 转换
     *
     * @param target
     * @param source
     */
    @Mappings({
        @Mapping(target = "id", ignore = true),
    })
    protected abstract void addData(@MappingTarget ArticleVersionDTO target, ArticleVersionDTO source);
}
