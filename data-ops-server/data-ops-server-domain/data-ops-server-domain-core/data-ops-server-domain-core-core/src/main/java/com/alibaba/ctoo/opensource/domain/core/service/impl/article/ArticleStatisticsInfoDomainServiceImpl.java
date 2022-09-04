package com.alibaba.ctoo.opensource.domain.core.service.impl.article;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.common.util.ContextUtils;
import com.alibaba.ctoo.opensource.domain.api.model.ArticleStatisticsInfoDTO;
import com.alibaba.ctoo.opensource.domain.api.service.article.ArticleStatisticsInfoDomainService;
import com.alibaba.ctoo.opensource.domain.core.converter.ArticleStatisticsInfoCoreConverter;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ArticleStatisticsInfoDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ArticleStatisticsInfoParam;
import com.alibaba.ctoo.opensource.domain.repository.mapper.ArticleStatisticsInfoMapper;
import com.alibaba.ctoo.opensource.domain.repository.mapper.CustomArticleStatisticsInfoMapper;
import com.alibaba.easytools.base.enums.DeletedIdEnum;
import com.alibaba.easytools.base.excption.BusinessException;
import com.alibaba.easytools.base.excption.CommonErrorEnum;
import com.alibaba.easytools.base.wrapper.result.ActionResult;
import com.alibaba.easytools.base.wrapper.result.DataResult;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;


/**
 * 文章阅读量实现
 *
 * @author zyb
 */
@Service
public class ArticleStatisticsInfoDomainServiceImpl implements ArticleStatisticsInfoDomainService {

    @Resource
    private ArticleStatisticsInfoMapper articleStatisticsInfoMapper;
    @Resource
    private ArticleStatisticsInfoCoreConverter articleStatisticsInfoCoreConverter;
    @Resource
    private CustomArticleStatisticsInfoMapper customArticleStatisticsInfoMapper;

    @Override
    public ActionResult increase(Long articleId) {
        if (articleId == null) {
            throw new BusinessException(CommonErrorEnum.DATA_NOT_FOUND.getDescription());
        }

        // 文章阅读量数据在创建文章时就已经创建好了,这里直接进行+1就行
        return doIncrease(articleId);
    }

    @Override
    public ActionResult create(Long articleId) {
        ArticleStatisticsInfoDO articleStatisticsInfo = articleStatisticsInfoCoreConverter.param2do(articleId, ContextUtils.getContext());
        // 创建文章的时候阅读量设置为0
        articleStatisticsInfo.setReadCount(0L);
        articleStatisticsInfoMapper.insertSelective(articleStatisticsInfo);

        return ActionResult.isSuccess();
    }

    @Override
    public DataResult<ArticleStatisticsInfoDTO> query(Long articleId) {
        ArticleStatisticsInfoParam articleStatisticsInfoParam = new ArticleStatisticsInfoParam();
        articleStatisticsInfoParam.createCriteria()
            .andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode())
            .andArticleIdEqualTo(articleId);
        List<ArticleStatisticsInfoDO> dataList = articleStatisticsInfoMapper.selectByParam(articleStatisticsInfoParam);

        if (CollectionUtils.isEmpty(dataList)) {
            return DataResult.empty();
        }
        List<ArticleStatisticsInfoDTO> list = articleStatisticsInfoCoreConverter.doList2dtoList(dataList);

        return DataResult.of(list.get(0));
    }

    private ActionResult doIncrease(Long articleId) {
        customArticleStatisticsInfoMapper.doIncrease(articleId);
        return ActionResult.isSuccess();
    }
}
