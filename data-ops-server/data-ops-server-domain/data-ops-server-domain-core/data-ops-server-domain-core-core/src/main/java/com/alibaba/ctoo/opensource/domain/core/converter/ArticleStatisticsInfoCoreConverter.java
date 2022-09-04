package com.alibaba.ctoo.opensource.domain.core.converter;

import java.util.List;

import com.alibaba.ctoo.opensource.common.model.Context;
import com.alibaba.ctoo.opensource.domain.api.model.ArticleStatisticsInfoDTO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ArticleStatisticsInfoDO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


/**
 * 文章阅读量转换器
 *
 * @author zyb
 */
@Mapper(componentModel = "spring")
public abstract class ArticleStatisticsInfoCoreConverter {

    @Mappings({
        @Mapping(target = "creator",source = "context.userId"),
        @Mapping(target = "modifier",source = "context.userId"),
        @Mapping(target = "tenantId",source = "context.tenantId")
    })
    public abstract ArticleStatisticsInfoDO param2do(Long articleId, Context context);

    /**
     * 转换
     *
     * @param articleStatisticsInfo
     * @return
     */
    public abstract ArticleStatisticsInfoDTO do2dto(ArticleStatisticsInfoDO articleStatisticsInfo);

    /**
     * List<ArticleStatisticsInfoDO>2List<ArticleStatisticsInfoDTO>
     *
     * @param doList
     * @return
     */
    public abstract List<ArticleStatisticsInfoDTO> doList2dtoList(List<ArticleStatisticsInfoDO> doList);


}
