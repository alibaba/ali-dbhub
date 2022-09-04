package com.alibaba.ctoo.opensource.domain.core.converter;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.domain.api.model.ArticleDTO;
import com.alibaba.ctoo.opensource.domain.api.model.OperationArticleDTO;
import com.alibaba.ctoo.opensource.domain.api.param.article.ArticleSelector;
import com.alibaba.ctoo.opensource.domain.api.param.article.OperationArticleCreateParam;
import com.alibaba.ctoo.opensource.domain.api.param.article.OperationArticlePageQueryFromSearchParam;
import com.alibaba.ctoo.opensource.domain.api.param.article.OperationArticleUpdateParam;
import com.alibaba.ctoo.opensource.domain.api.service.article.ArticleDomainService;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.OperationArticleDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.search.CustomOperationArticleSearchParam;
import com.alibaba.easytools.base.enums.DeletedIdEnum;
import com.alibaba.easytools.common.util.EasyCollectionUtils;

import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

/**
 * banner转换器
 *
 * @author hyh
 */
@Mapper(componentModel = "spring", imports = DeletedIdEnum.class)
public abstract class OperationArticleManageCoreConverter {

    @Resource
    private ArticleDomainService articleDomainService;

    /**
     * 转换
     *
     * @param param      param
     * @param tenantId   tenantId
     * @param creator    creator
     * @param modifier   modifier
     * @return
     */
    @Mappings({
        @Mapping(target = "deletedId", expression = "java(DeletedIdEnum.NOT_DELETED.getCode())"),
    })
    public abstract OperationArticleDO param2do(OperationArticleCreateParam param, String tenantId, String creator,
        String modifier);

    /**
     * 转换
     *
     * @param list list
     * @return
     */
    public abstract List<OperationArticleDTO> do2dto(List<OperationArticleDO> list);

    /**
     * 转换
     *
     * @param operationArticleDO operationArticleDO
     * @return
     */
    @Mappings({
        @Mapping(target = "creator.userId", source = "creator"),
        @Mapping(target = "modifier.userId", source = "modifier"),
        @Mapping(target = "article.id", source = "articleId"),
    })
    public abstract OperationArticleDTO do2dto(OperationArticleDO operationArticleDO);

    /**
     * 填充文章信息
     *
     * @param list     list
     * @param selector selector
     * @return
     */
    public void fillArticle(List<ArticleDTO> list, ArticleSelector selector) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        List<Long> idList = EasyCollectionUtils.toList(list, ArticleDTO::getId);
        // 填充文章信息
        List<ArticleDTO> dataList = articleDomainService.queryList(idList, selector)
            .getData();
        Map<Long, ArticleDTO> dataMap = EasyCollectionUtils.toIdentityMap(dataList, ArticleDTO::getId);
        for (ArticleDTO data : list) {
            if (data == null || data.getId() == null) {
                continue;
            }
            ArticleDTO queryData = dataMap.get(data.getId());
            if (queryData == null) {
                continue;
            }
            addData(data, queryData);
        }
    }

    /**
     * 转换
     *
     * @param target target
     * @param source source
     * @return
     */
    protected abstract void addData(@MappingTarget ArticleDTO target, ArticleDTO source);

    /**
     * 转换
     *
     * @param param      param
     * @param modifier   modifier
     * @return
     */
    public abstract OperationArticleDO param2do(OperationArticleUpdateParam param, String modifier);

    /**
     * 转换
     *
     * @param param param
     * @return
     */
    public abstract CustomOperationArticleSearchParam param2custom(OperationArticlePageQueryFromSearchParam param);

    /**
     * 转换
     *
     * @param target target
     * @param source source
     * @return
     */
    public abstract void addData(@MappingTarget OperationArticleDTO target, OperationArticleDTO source);
}
