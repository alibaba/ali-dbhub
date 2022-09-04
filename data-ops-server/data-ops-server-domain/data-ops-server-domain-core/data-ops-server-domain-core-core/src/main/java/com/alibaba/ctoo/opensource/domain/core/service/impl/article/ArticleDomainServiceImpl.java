package com.alibaba.ctoo.opensource.domain.core.service.impl.article;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.common.model.Context;
import com.alibaba.ctoo.opensource.common.util.ContextUtils;
import com.alibaba.ctoo.opensource.domain.api.enums.ActivatedEnum;
import com.alibaba.ctoo.opensource.domain.api.enums.ArticleStatusEnum;
import com.alibaba.ctoo.opensource.domain.api.enums.ArticleTypeEnum;
import com.alibaba.ctoo.opensource.domain.api.enums.OperationEnum;
import com.alibaba.ctoo.opensource.domain.api.model.ArticleDTO;
import com.alibaba.ctoo.opensource.domain.api.model.ArticleVersionDTO;
import com.alibaba.ctoo.opensource.domain.api.model.OperationArticleDTO;
import com.alibaba.ctoo.opensource.domain.api.param.article.ArticleCreateParam;
import com.alibaba.ctoo.opensource.domain.api.param.article.ArticlePageQueryFromSearchParam;
import com.alibaba.ctoo.opensource.domain.api.param.article.ArticlePageQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.article.ArticleSelector;
import com.alibaba.ctoo.opensource.domain.api.param.article.ArticleUpdateParam;
import com.alibaba.ctoo.opensource.domain.api.param.article.ArticleVersionSelector;
import com.alibaba.ctoo.opensource.domain.api.param.article.OperationArticleQueryParam;
import com.alibaba.ctoo.opensource.domain.api.service.article.ArticleStatisticsInfoDomainService;
import com.alibaba.ctoo.opensource.domain.api.service.article.OperationArticleManageDomainService;
import com.alibaba.ctoo.opensource.domain.api.service.article.ArticleDomainService;
import com.alibaba.ctoo.opensource.domain.api.service.source.RichTextDomainService;
import com.alibaba.ctoo.opensource.domain.core.converter.ArticleCoreConverter;
import com.alibaba.ctoo.opensource.domain.core.converter.ArticleVersionCoreConverter;
import com.alibaba.ctoo.opensource.domain.core.converter.ArticleVersionCoreIgnoreNullConverter;
import com.alibaba.ctoo.opensource.domain.core.converter.RichTextCoreConverter;
import com.alibaba.ctoo.opensource.domain.repository.dao.ArticleVersionDAO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ArticleDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ArticleParam;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ArticleVersionDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.search.CustomArticleSearchParam;
import com.alibaba.ctoo.opensource.domain.repository.mapper.ArticleMapper;
import com.alibaba.ctoo.opensource.domain.repository.mapper.ArticleVersionMapper;
import com.alibaba.ctoo.opensource.domain.repository.mapper.CustomArticleMapper;
import com.alibaba.easytools.base.enums.DeletedIdEnum;
import com.alibaba.easytools.base.excption.BusinessException;
import com.alibaba.easytools.base.excption.CommonErrorEnum;
import com.alibaba.easytools.base.wrapper.result.ActionResult;
import com.alibaba.easytools.base.wrapper.result.DataResult;
import com.alibaba.easytools.base.wrapper.result.ListResult;
import com.alibaba.easytools.base.wrapper.result.PageResult;
import com.alibaba.easytools.common.util.EasyCollectionUtils;

import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 文章实现
 *
 * @author zyb
 */
@Service
public class ArticleDomainServiceImpl implements ArticleDomainService {

    @Resource
    private ArticleCoreConverter articleCoreConverter;
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private ArticleVersionMapper articleVersionMapper;
    @Resource
    private RichTextDomainService richTextDomainService;
    @Resource
    private ArticleVersionDAO articleVersionDAO;
    @Resource
    private RichTextCoreConverter richTextCoreConverter;
    @Resource
    private ArticleVersionCoreIgnoreNullConverter articleVersionCoreIgnoreNullConverter;
    @Resource
    private ArticleVersionCoreConverter articleVersionCoreConverter;
    @Resource
    private CustomArticleMapper customArticleMapper;
    @Resource
    private OperationArticleManageDomainService operationArticleManageDomainService;
    @Resource
    private ArticleStatisticsInfoDomainService articleStatisticsInfoDomainService;

    private static final ArticleSelector ARTICLE_GET_SELECTOR = ArticleSelector.builder().activatedArticleVersion(
        Boolean.TRUE).articleVersionSelector(ArticleVersionSelector.builder().richTextDetail(Boolean.TRUE).build())
        .build();

    @Override
    public PageResult<ArticleDTO> queryPage(ArticlePageQueryParam param, ArticleSelector selector) {

        return null;
    }

    @Override
    public ListResult<ArticleDTO> queryList(List<Long> idList, ArticleSelector selector) {
        if (CollectionUtils.isEmpty(idList)) {
            return ListResult.empty();
        }
        ArticleParam articleParam = new ArticleParam();
        articleParam.createCriteria()
            .andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode())
            .andIdIn(idList);
        // 查询
        List<ArticleDO> dataList = articleMapper.selectByParam(articleParam);
        List<ArticleDTO> list = articleCoreConverter.do2dto(dataList);
        // 填充数据
        fillData(list, selector);
        return ListResult.of(list);
    }

    @Override
    public DataResult<ArticleDTO> query(Long id, ArticleSelector selector) {
        if (id == null) {
            return DataResult.empty();
        }
        List<ArticleDTO> list = queryList(Lists.newArrayList(id), selector).getData();
        if (CollectionUtils.isEmpty(list)) {
            return DataResult.empty();
        }
        return DataResult.of(list.get(0));
    }

    @Override
    public DataResult<Long> create(ArticleCreateParam param) {
        // 添加文章
        Date now = DateUtil.date();
        ArticleDO article = articleCoreConverter.param2do(param, ContextUtils.getContext(), now);
        articleMapper.insertSelective(article);

        // 这里要添加富文本并返回id
        Long id = richTextDomainService.createOrUpdate(param.getContentRichText()).getData();

        // 添加文章version
        ArticleVersionDO articleVersion = articleCoreConverter.param2versionDO(param, ContextUtils.getContext());
        articleVersion.setContentRichTextId(id);
        articleVersion.setArticleId(article.getId());
        articleVersion.setActivated(ActivatedEnum.ACTIVATED.getCode());
        articleVersionMapper.insertSelective(articleVersion);

        // 回填文章versionId
        ArticleDO articleUpdate = new ArticleDO();
        articleUpdate.setId(article.getId());
        articleUpdate.setActivatedArticleVersionId(articleVersion.getId());
        articleMapper.updateByPrimaryKeySelective(articleUpdate);

        // 准备统计信息
        articleStatisticsInfoDomainService.create(article.getId());

        return DataResult.of(article.getId());
    }

    @Override
    public DataResult<Long> update(ArticleUpdateParam param) {
        // 请求参数异常
        if (param == null || param.getId() == null) {
            throw new BusinessException(CommonErrorEnum.PARAM_ERROR.getDescription());
        }
        Context context = ContextUtils.getContext();
        String loginUser = context.getUserId();
        Date now = new Date();
        ArticleDTO article = get(param.getId(), ARTICLE_GET_SELECTOR).getData();
        ArticleDO articleUpdate = new ArticleDO();
        articleUpdate.setId(article.getId());
        //上架
        if (StringUtils.equals(param.getOperation(), OperationEnum.SHELF.getCode())) {
            articleUpdate.setStatus(ArticleStatusEnum.RELEASE.getCode());
            articleUpdate.setGmtRelease(now);
            //下架
        } else if (StringUtils.equals(param.getOperation(), OperationEnum.UN_SHELF.getCode())) {
            //下架校验
            checkUpdate(article, param);
            articleUpdate = articleMapper.selectByPrimaryKey(param.getId());
            articleUpdate.setStatus(ArticleStatusEnum.DRAFT.getCode());
            articleUpdate.setGmtRelease(null);
            articleMapper.updateByPrimaryKey(articleUpdate);
            return DataResult.of(param.getId());
            // 修改
        } else {
            //修改校验
            checkUpdate(article, param);
            ArticleVersionDO articleVersionData = articleVersionMapper.selectByPrimaryKey(
                article.getActivatedArticleVersionId());
            // 将参数拷贝进去
            articleVersionCoreIgnoreNullConverter.copy(articleVersionData, param, loginUser);
            // 将所有历史版本改成未激活
            articleVersionDAO.unactivatedByArticelId(article.getId());
            // 保存备注信息
            articleVersionData.setContentRichTextId(
                richTextDomainService.createOrUpdate(param.getContentRichText()).getData());
            // 插入激活的数据
            articleVersionData.setId(null);
            articleVersionMapper.insertSelective(articleVersionData);
            // 再去更新文章表的最新版本信息
            articleUpdate.setActivatedArticleVersionId(articleVersionData.getId());
            articleUpdate.setCustomModifier(loginUser);
            articleUpdate.setGmtCustomModified(now);
        }
        articleMapper.updateByPrimaryKeySelective(articleUpdate);
        return DataResult.of(param.getId());
    }

    private void checkUpdate(ArticleDTO article, ArticleUpdateParam param) {
        if (article == null) {
            return;
        }
        if (StringUtils.equals(article.getStatus(), ArticleStatusEnum.RELEASE.getCode()) && StringUtils.equals(
            param.getOperation(), OperationEnum.UPDATE.getCode())) {
            throw new BusinessException("已上架文章不可编辑");
        }
        String type = param.getType();
        OperationArticleQueryParam operationArticleQueryParam = new OperationArticleQueryParam();
        operationArticleQueryParam.setArticleId(article.getId());
        List<OperationArticleDTO> operationArticleList = operationArticleManageDomainService.queryList(
            operationArticleQueryParam, null).getData();
        ArticleVersionDTO activatedArticleVersion = article.getActivatedArticleVersion();
        if (CollectionUtils.isEmpty(operationArticleList)) {
            return;
        }
        //首页最新动态引用的文章不能下架
        if (StringUtils.equals(param.getOperation(), OperationEnum.UN_SHELF.getCode())) {
            throw new BusinessException("文章已配置为最新动态，请在运营管理取消后再下架");
        }
        //首页最新动态引用的文章不能更改栏目
        if (StringUtils.isNotEmpty(type) && !StringUtils.equals(type, activatedArticleVersion.getType())) {
            throw new BusinessException(
                "该" + ArticleTypeEnum.getDescription(activatedArticleVersion.getType()) + "被配置在首页最新动态，不能修改为其他栏目");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActionResult delete(Long id) {
        // 校验数据是否存在
        get(id, null);

        // 先将article删除
        ArticleDO article = new ArticleDO();
        article.setId(id);
        article.setDeletedId(id);
        articleMapper.updateByPrimaryKeySelective(article);

        // 将articleVersion都改为未激活
        articleVersionDAO.unactivatedByArticelId(id);

        return ActionResult.isSuccess();
    }

    @Override
    public DataResult<ArticleDTO> get(Long id, ArticleSelector selector) {
        if (id == null) {
            throw new BusinessException(CommonErrorEnum.DATA_NOT_FOUND.getDescription());
        }
        List<ArticleDTO> articleList = queryList(Lists.newArrayList(id), selector).getData();
        if (CollectionUtils.isEmpty(articleList)) {
            throw new BusinessException(CommonErrorEnum.DATA_NOT_FOUND.getCode());
        }

        return DataResult.of(articleList.get(0));
    }

    @Override
    public PageResult<ArticleDTO> queryPageFromSearch(ArticlePageQueryFromSearchParam param, ArticleSelector selector) {
        CustomArticleSearchParam searchParam = articleCoreConverter.param2custom(param);

        searchParam.setPagination(param.getPageNo(), param.getPageSize());
        //排序
        if (CollectionUtils.isNotEmpty(param.getOrderByList())) {
            param.getOrderByList().forEach(searchParam::appendOrderByClause);
        }
        // 查询
        List<Long> dataIdList = customArticleMapper.queryPageFromSearch(searchParam);
        // 组装dto对象
        List<ArticleDTO> dataList = EasyCollectionUtils.toList(dataIdList, dataId -> {
            ArticleDTO article = new ArticleDTO();
            article.setId(dataId);
            return article;
        });

        // 复制数据
        List<ArticleDTO> queryDataList = queryList(dataIdList, selector).getData();
        Map<Long, ArticleDTO> queryDataMap = EasyCollectionUtils.toIdentityMap(queryDataList, ArticleDTO::getId);
        for (ArticleDTO data : dataList) {
            articleCoreConverter.addData(data, queryDataMap.get(data.getId()));
        }

        // 统计全部
        long totalCount = 0L;
        if (param.getEnableReturnCount()) {
            totalCount = customArticleMapper.countQueryPageFromSearch(searchParam);
        }

        return PageResult.of(dataList, totalCount, param);
    }

    private void fillData(List<ArticleDTO> list, ArticleSelector selector) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        if (selector == null) {
            return;
        }
        // 填充激活版本
        fillActivatedArticleVersion(list, selector);
    }

    private void fillActivatedArticleVersion(List<ArticleDTO> list, ArticleSelector selector) {
        if (BooleanUtils.isNotTrue(selector.getActivatedArticleVersion())) {
            return;
        }
        articleVersionCoreConverter.fillDetail(
            list.stream().map(ArticleDTO::getActivatedArticleVersion).collect(Collectors.toList()),
            selector.getArticleVersionSelector());
    }

}
