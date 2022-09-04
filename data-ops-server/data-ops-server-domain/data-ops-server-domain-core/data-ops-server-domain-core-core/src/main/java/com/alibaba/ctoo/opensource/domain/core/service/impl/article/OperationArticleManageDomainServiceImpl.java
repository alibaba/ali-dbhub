package com.alibaba.ctoo.opensource.domain.core.service.impl.article;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.common.util.ContextUtils;
import com.alibaba.ctoo.opensource.domain.api.enums.ArticleTypeEnum;
import com.alibaba.ctoo.opensource.domain.api.model.ArticleDTO;
import com.alibaba.ctoo.opensource.domain.api.model.ArticleVersionDTO;
import com.alibaba.ctoo.opensource.domain.api.model.OperationArticleDTO;
import com.alibaba.ctoo.opensource.domain.api.param.article.ArticleSelector;
import com.alibaba.ctoo.opensource.domain.api.param.article.ArticleVersionSelector;
import com.alibaba.ctoo.opensource.domain.api.param.article.OperationArticleCreateParam;
import com.alibaba.ctoo.opensource.domain.api.param.article.OperationArticlePageQueryFromSearchParam;
import com.alibaba.ctoo.opensource.domain.api.param.article.OperationArticlePageQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.article.OperationArticleQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.article.OperationArticleSelector;
import com.alibaba.ctoo.opensource.domain.api.param.article.OperationArticleUpdateParam;
import com.alibaba.ctoo.opensource.domain.api.service.article.ArticleDomainService;
import com.alibaba.ctoo.opensource.domain.api.service.article.OperationArticleManageDomainService;
import com.alibaba.ctoo.opensource.domain.core.converter.OperationArticleManageCoreConverter;
import com.alibaba.ctoo.opensource.domain.core.converter.UserCoreConverter;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.OperationArticleDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.OperationArticleParam;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.search.CustomOperationArticleSearchParam;
import com.alibaba.ctoo.opensource.domain.repository.mapper.CustomOperationArticleMapper;
import com.alibaba.ctoo.opensource.domain.repository.mapper.OperationArticleMapper;
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
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

/**
 * 运营管理/运营管理
 * 包含 博客 活动 新闻
 *
 * @author hyh
 */
@Service
public class OperationArticleManageDomainServiceImpl implements OperationArticleManageDomainService {

    /**
     * 博客最少数量
     */
    private static final Long COUNT_BLOG_THREE = 3L;

    /**
     * 博客
     */
    private static final Long COUNT_BLOG_TWO = 2L;

    private static final ArticleSelector LIST_ARTICLE_SELECTOR = ArticleSelector.builder()
        .activatedArticleVersion(Boolean.TRUE)
        .articleVersionSelector(ArticleVersionSelector.builder().build())
        .build();

    @Resource
    private OperationArticleMapper operationArticleMapper;
    @Resource
    private OperationArticleManageCoreConverter operationArticleManageCoreConverter;
    @Resource
    private UserCoreConverter userCoreConverter;
    @Resource
    private CustomOperationArticleMapper customOperationArticleMapper;
    @Resource
    private ArticleDomainService articleDomainService;

    @Override
    public PageResult<OperationArticleDTO> queryPage(OperationArticlePageQueryParam param,
        OperationArticleSelector selector) {

        OperationArticleParam operationArticleParam = new OperationArticleParam();
        operationArticleParam.createCriteria()
            .andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode())
            .andTypeEqualTo(param.getType());
        operationArticleParam.setPagination(param.getPageNo(), param.getPageSize());
        // 排序
        if (CollectionUtils.isNotEmpty(param.getOrderByList())) {
            param.getOrderByList().forEach(orderBy -> operationArticleParam.appendOrderByClause(
                OperationArticleParam.OrderCondition.getEnumByName(orderBy.getOrderConditionName()),
                OperationArticleParam.SortType.getEnumByName(orderBy.getDirection().name())));
        }

        List<OperationArticleDO> operationArticleList = operationArticleMapper.selectByParam(operationArticleParam);
        List<OperationArticleDTO> list = operationArticleManageCoreConverter.do2dto(operationArticleList);

        fillData(list, selector);

        // 统计全部
        long totalCount = 0L;
        if (param.getEnableReturnCount()) {
            totalCount = operationArticleMapper.countByParam(operationArticleParam);
        }

        return PageResult.of(list, totalCount, param);
    }

    @Override
    public PageResult<OperationArticleDTO> queryPageFromSearch(OperationArticlePageQueryFromSearchParam param,
        OperationArticleSelector selector) {
        CustomOperationArticleSearchParam searchParam = operationArticleManageCoreConverter.param2custom(param);

        searchParam.setPagination(param.getPageNo(), param.getPageSize());
        //排序
        if (CollectionUtils.isNotEmpty(param.getOrderByList())) {
            param.getOrderByList().forEach(searchParam::appendOrderByClause);
        }
        // 查询
        List<Long> dataIdList = customOperationArticleMapper.queryPageByParam(searchParam);
        // 组装dto对象
        List<OperationArticleDTO> dataList = EasyCollectionUtils.toList(dataIdList, dataId -> {
            OperationArticleDTO operationArticle = new OperationArticleDTO();
            operationArticle.setId(dataId);
            return operationArticle;
        });

        // 复制数据
        List<OperationArticleDTO> queryDataList = queryList(dataIdList, selector).getData();
        Map<Long, OperationArticleDTO> queryDataMap = EasyCollectionUtils.toIdentityMap(queryDataList,
            OperationArticleDTO::getId);
        for (OperationArticleDTO data : dataList) {
            operationArticleManageCoreConverter.addData(data, queryDataMap.get(data.getId()));
        }

        // 统计全部
        long totalCount = 0L;
        if (param.getEnableReturnCount()) {
            totalCount = customOperationArticleMapper.countQueryPageByParam(searchParam);
        }

        return PageResult.of(dataList, totalCount, param);
    }

    @Override
    public ListResult<OperationArticleDTO> queryList(OperationArticleQueryParam param,
        OperationArticleSelector selector) {

        // 参数异常
        if (param == null) {
            throw new BusinessException(CommonErrorEnum.PARAM_ERROR.getCode());
        }
        // 组装查询条件
        OperationArticleParam operationArticleParam = new OperationArticleParam();
        operationArticleParam.createCriteria().andDeletedIdEqualTo(
            DeletedIdEnum.NOT_DELETED.getCode())
            .andTypeEqualToWhenPresent(param.getType())
            .andIdEqualToWhenPresent(param.getId())
            .andArticleIdEqualToWhenPresent(param.getArticleId());

        List<OperationArticleDO> operationArticleList = operationArticleMapper.selectByParam(operationArticleParam);
        // 查询不存在数据返回
        if (CollectionUtils.isEmpty(operationArticleList)) {
            return ListResult.empty();
        }
        List<OperationArticleDTO> list = operationArticleManageCoreConverter.do2dto(operationArticleList);

        // 填充数据
        fillData(list, selector);

        return ListResult.of(list);
    }

    @Override
    public ListResult<OperationArticleDTO> queryList(List<Long> idList, OperationArticleSelector selector) {
        if (CollectionUtils.isEmpty(idList)) {
            return ListResult.empty();
        }
        OperationArticleParam operationArticleParam = new OperationArticleParam();
        operationArticleParam.createCriteria().andDeletedIdEqualTo(
            DeletedIdEnum.NOT_DELETED.getCode())
            .andIdIn(idList);

        List<OperationArticleDO> dataList = operationArticleMapper.selectByParam(operationArticleParam);
        List<OperationArticleDTO> list = operationArticleManageCoreConverter.do2dto(dataList);

        // 填充数据
        fillData(list, selector);

        return ListResult.of(list);
    }

    /**
     * 填充额外信息
     *
     * @param list     list
     * @param selector selector
     * @return
     */
    private void fillData(List<OperationArticleDTO> list, OperationArticleSelector selector) {
        if (CollectionUtils.isEmpty(list) || selector == null) {
            return;
        }
        // 填充创建人
        fillCreatorDetail(list, selector);
        // 填充修改人
        fillModifierDetail(list, selector);
        // 填充文章
        fillArticle(list, selector);
    }

    private void fillCreatorDetail(List<OperationArticleDTO> list, OperationArticleSelector selector) {
        if (BooleanUtils.isNotTrue(selector.getCreatorDetail())) {
            return;
        }

        userCoreConverter.fillUserDetail(EasyCollectionUtils.toList(list, OperationArticleDTO::getCreator), null);
    }

    private void fillModifierDetail(List<OperationArticleDTO> list, OperationArticleSelector selector) {
        if (BooleanUtils.isNotTrue(selector.getModifierDetail())) {
            return;
        }
        userCoreConverter.fillUserDetail(EasyCollectionUtils.toList(list, OperationArticleDTO::getModifier), null);
    }

    private void fillArticle(List<OperationArticleDTO> list, OperationArticleSelector selector) {
        if (BooleanUtils.isNotTrue(selector.getArticle())) {
            return;
        }

        operationArticleManageCoreConverter.fillArticle(
            EasyCollectionUtils.toList(list, OperationArticleDTO::getArticle),
            selector.getArticleSelector());
    }

    @Override
    public DataResult<OperationArticleDTO> get(Long id, OperationArticleSelector selector) {
        if (id == null) {
            throw new BusinessException(CommonErrorEnum.DATA_NOT_FOUND.getCode());
        }
        List<OperationArticleDTO> operationArticleList = queryList(Lists.newArrayList(id), selector).getData();
        if (CollectionUtils.isEmpty(operationArticleList)) {
            throw new BusinessException(CommonErrorEnum.DATA_NOT_FOUND.getCode());
        }

        return DataResult.of(operationArticleList.get(0));
    }

    @Override
    public ActionResult create(OperationArticleCreateParam param) {
        // 参数异常
        if (param == null) {
            throw new BusinessException(CommonErrorEnum.PARAM_ERROR.getCode());
        }

        // 开始时间不能小于结束时间(都可以为空或者单个为空)
        Date gmtStart = param.getGmtStart();
        Date gmtEnd = param.getGmtEnd();
        if (gmtStart != null && gmtEnd != null) {
            checkGmtStartEnd(gmtStart, gmtEnd);
        }

        // 根据articleId查询表article获取cover_url(校验数据正确性)
        List<ArticleDTO> articleList = articleDomainService.queryList(Lists.newArrayList(param.getArticleId()),
            LIST_ARTICLE_SELECTOR).getData();
        if (CollectionUtils.isEmpty(articleList)) {
            throw new BusinessException(CommonErrorEnum.PARAM_ERROR.getCode());
        }
        // 获取article_version版本信息
        ArticleVersionDTO articleVersion = articleList.get(0).getActivatedArticleVersion();
        if (articleVersion == null) {
            throw new BusinessException(CommonErrorEnum.DATA_NOT_FOUND.getCode());
        }
        // 活动和新闻封面不能为空
        if (StringUtils.equals(param.getType(), ArticleTypeEnum.ACTIVITY.getCode())
            || StringUtils.equals(param.getType(), ArticleTypeEnum.NEWS.getCode())) {
            // 校验活动、新闻封面是否存在，不存在无法保存
            if (StringUtils.isBlank(articleVersion.getCoverUrl())) {
                throw new BusinessException("该文章无封面，请重新选择");
            }
        }
        // 转换
        OperationArticleDO operationArticleCreate = operationArticleManageCoreConverter.param2do(param,
            ContextUtils.getContext().getTenantId(),
            ContextUtils.getContext().getUserId(),
            ContextUtils.getContext().getUserId());
        // 新增operation_article
        operationArticleMapper.insertSelective(operationArticleCreate);

        return ActionResult.isSuccess();
    }

    private void checkGmtStartEnd(Date gmtStart, Date gmtEnd) {
        if (!gmtEnd.after(gmtStart)) {
            throw new BusinessException("开始时间应小于结束时间");
        }
    }

    @Override
    public ActionResult update(OperationArticleUpdateParam param) {
        // 参数异常
        if (param == null || param.getId() == null) {
            throw new BusinessException(CommonErrorEnum.PARAM_ERROR.getCode());
        }
        // 开始时间不能小于结束时间(都可以为空或者单个为空)
        Date gmtStart = param.getGmtStart();
        Date gmtEnd = param.getGmtEnd();
        if (gmtStart != null && gmtEnd != null) {
            checkGmtStartEnd(gmtStart, gmtEnd);
        }
        // 查询数据(防止数据被删除以后在进行修改操作)
        OperationArticleDTO operationArticle = get(param.getId(), null).getData();
        if (operationArticle == null) {
            throw new BusinessException(CommonErrorEnum.DATA_NOT_FOUND.getCode());
        }

        // 根据articleId查询表article获取cover_url(校验数据正确性)
        List<ArticleDTO> articleList = articleDomainService.queryList(Lists.newArrayList(param.getArticleId()),
            LIST_ARTICLE_SELECTOR).getData();
        if (CollectionUtils.isEmpty(articleList)) {
            throw new BusinessException(CommonErrorEnum.PARAM_ERROR.getCode());
        }
        // 获取article_version版本信息
        ArticleVersionDTO articleVersion = articleList.get(0).getActivatedArticleVersion();
        if (articleVersion == null) {
            throw new BusinessException(CommonErrorEnum.DATA_NOT_FOUND.getCode());
        }
        // 活动和新闻封面不能为空
        if (StringUtils.equals(operationArticle.getType(), ArticleTypeEnum.ACTIVITY.getCode())
            || StringUtils.equals(operationArticle.getType(), ArticleTypeEnum.NEWS.getCode())) {
            // 校验活动、新闻封面是否存在，不存在无法更新
            if (StringUtils.isBlank(articleVersion.getCoverUrl())) {
                throw new BusinessException("该文章无封面，请重新选择");
            }
        }
        // 转换
        OperationArticleDO operationArticleUpdate = operationArticleManageCoreConverter.param2do(param,
            ContextUtils.getContext().getUserId());
        // 博客至少3条，活动与新闻至少1条
        // 校验是否有效数据(无效数据可以直接修改)
        Date now = DateUtil.date();
        // 查询有效文章数量
        CustomOperationArticleSearchParam customOperationArticleSearchParam = new CustomOperationArticleSearchParam();
        customOperationArticleSearchParam.setType(operationArticle.getType());
        customOperationArticleSearchParam.setIdNotEquals(operationArticle.getId());
        long count = customOperationArticleMapper.countQueryPageByParam(customOperationArticleSearchParam);

        // 获取文章类型
        String type = operationArticle.getType();
        if (operationArticle.getGmtStart() == null) {
            if (operationArticle.getGmtEnd() == null || operationArticle.getGmtEnd().after(now)) {
                // 活动
                if (StringUtils.equals(type, ArticleTypeEnum.ACTIVITY.getCode())) {
                    if (count > NumberUtils.LONG_ZERO) {
                        // 更新表operation_article
                        operationArticleMapper.updateByPrimaryKeySelective(operationArticleUpdate);
                        if (gmtStart == null || gmtEnd == null) {
                            customOperationArticleMapper.updateGmtStartEndByPrimaryKey(param.getId(), gmtStart, gmtEnd);
                        }
                        return ActionResult.isSuccess();
                    }
                }
                // 博客
                if (StringUtils.equals(type, ArticleTypeEnum.BLOG.getCode())) {
                    if (count > COUNT_BLOG_TWO) {
                        // 更新表operation_article
                        operationArticleMapper.updateByPrimaryKeySelective(operationArticleUpdate);
                        if (gmtStart == null || gmtEnd == null) {
                            customOperationArticleMapper.updateGmtStartEndByPrimaryKey(param.getId(), gmtStart, gmtEnd);
                        }
                        return ActionResult.isSuccess();
                    }
                }
                // 新闻
                if (StringUtils.equals(type, ArticleTypeEnum.NEWS.getCode())) {
                    if (count > NumberUtils.LONG_ZERO) {
                        // 更新表operation_article
                        operationArticleMapper.updateByPrimaryKeySelective(operationArticleUpdate);
                        if (gmtStart == null || gmtEnd == null) {
                            customOperationArticleMapper.updateGmtStartEndByPrimaryKey(param.getId(), gmtStart, gmtEnd);
                        }
                        return ActionResult.isSuccess();
                    }
                }
                // 修改后的数据必须是有效数据
                if (gmtEnd != null && gmtEnd.before(now)) {
                    remind(NumberUtils.LONG_ZERO, type);
                }
                if (gmtStart != null && gmtStart.after(now)) {
                    remind(NumberUtils.LONG_ZERO, type);
                }
            } else {
                // 更新表operation_article
                operationArticleMapper.updateByPrimaryKeySelective(operationArticleUpdate);
                if (gmtStart == null || gmtEnd == null) {
                    customOperationArticleMapper.updateGmtStartEndByPrimaryKey(param.getId(), gmtStart, gmtEnd);
                }
                return ActionResult.isSuccess();
            }
        } else {
            if (operationArticle.getGmtStart().after(now)) {
                // 更新表operation_article
                operationArticleMapper.updateByPrimaryKeySelective(operationArticleUpdate);
                if (gmtStart == null || gmtEnd == null) {
                    customOperationArticleMapper.updateGmtStartEndByPrimaryKey(param.getId(), gmtStart, gmtEnd);
                }
                return ActionResult.isSuccess();
            }
            // 活动
            if (StringUtils.equals(type, ArticleTypeEnum.ACTIVITY.getCode())) {
                if (count > NumberUtils.LONG_ZERO) {
                    // 更新表operation_article
                    operationArticleMapper.updateByPrimaryKeySelective(operationArticleUpdate);
                    if (gmtStart == null || gmtEnd == null) {
                        customOperationArticleMapper.updateGmtStartEndByPrimaryKey(param.getId(), gmtStart, gmtEnd);
                    }
                    return ActionResult.isSuccess();
                }
            }
            // 博客
            if (StringUtils.equals(type, ArticleTypeEnum.BLOG.getCode())) {
                if (count > COUNT_BLOG_TWO) {
                    // 更新表operation_article
                    operationArticleMapper.updateByPrimaryKeySelective(operationArticleUpdate);
                    if (gmtStart == null || gmtEnd == null) {
                        customOperationArticleMapper.updateGmtStartEndByPrimaryKey(param.getId(), gmtStart, gmtEnd);
                    }
                    return ActionResult.isSuccess();
                }
            }
            // 新闻
            if (StringUtils.equals(type, ArticleTypeEnum.NEWS.getCode())) {
                if (count > NumberUtils.LONG_ZERO) {
                    // 更新表operation_article
                    operationArticleMapper.updateByPrimaryKeySelective(operationArticleUpdate);
                    if (gmtStart == null || gmtEnd == null) {
                        customOperationArticleMapper.updateGmtStartEndByPrimaryKey(param.getId(), gmtStart, gmtEnd);
                    }
                    return ActionResult.isSuccess();
                }
            }
            // 修改后的数据必须是有效数据
            if (gmtEnd != null && gmtEnd.before(now)) {
                remind(NumberUtils.LONG_ZERO, type);
            }
            if (gmtStart != null && gmtStart.after(now)) {
                remind(NumberUtils.LONG_ZERO, type);
            }
        }
        // 更新表operation_article
        operationArticleMapper.updateByPrimaryKeySelective(operationArticleUpdate);
        if (gmtStart == null || gmtEnd == null) {
            customOperationArticleMapper.updateGmtStartEndByPrimaryKey(param.getId(), gmtStart, gmtEnd);
        }

        return ActionResult.isSuccess();
    }

    @Override
    public ActionResult delete(Long id) {
        // 博客至少3条，活动与新闻各至少1条
        // 判断数据是否存在，存在可以删除，不存在抛出异常
        OperationArticleDTO operationArticle = get(id, null).getData();
        if (operationArticle == null) {
            throw new BusinessException(CommonErrorEnum.DATA_NOT_FOUND.getCode());
        }

        // 删除数据
        OperationArticleDO operationArticleDelete = new OperationArticleDO();
        operationArticleDelete.setId(id);
        operationArticleDelete.setDeletedId(id);

        // 查询有效文章数量
        CustomOperationArticleSearchParam customOperationArticleSearchParam = new CustomOperationArticleSearchParam();
        customOperationArticleSearchParam.setType(operationArticle.getType());
        customOperationArticleSearchParam.setIdNotEquals(operationArticle.getId());

        // 校验数据是否为有效数据(无效数据可以直接删除)
        Date now = DateUtil.date();
        if (operationArticle.getGmtStart() == null) {
            if (operationArticle.getGmtEnd() == null || operationArticle.getGmtEnd().after(now)) {
                // 校验有效文章
                remind(customOperationArticleMapper.countQueryPageByParam(customOperationArticleSearchParam),
                    operationArticle.getType());
            } else {
                // 删除数据
                operationArticleMapper.updateByPrimaryKeySelective(operationArticleDelete);
                return ActionResult.isSuccess();
            }
        } else {
            if (operationArticle.getGmtStart().after(now)) {
                // 删除数据
                operationArticleMapper.updateByPrimaryKeySelective(operationArticleDelete);
                return ActionResult.isSuccess();
            } else {
                // 校验有效文章
                remind(customOperationArticleMapper.countQueryPageByParam(customOperationArticleSearchParam),
                    operationArticle.getType());
            }
        }
        operationArticleMapper.updateByPrimaryKeySelective(operationArticleDelete);
        return ActionResult.isSuccess();
    }

    public void remind(Long countArticle, String type) {
        // 活动
        if (StringUtils.equals(type, ArticleTypeEnum.ACTIVITY.getCode())) {
            if (countArticle < NumberUtils.LONG_ONE) {
                throw new BusinessException("至少需要展示1篇活动");
            }
        }
        // 博客
        if (StringUtils.equals(type, ArticleTypeEnum.BLOG.getCode())) {
            if (countArticle < COUNT_BLOG_THREE) {
                throw new BusinessException("至少需要展示3篇博客");
            }
        }
        // 新闻
        if (StringUtils.equals(type, ArticleTypeEnum.NEWS.getCode())) {
            if (countArticle < NumberUtils.LONG_ONE) {
                throw new BusinessException("至少需要展示1篇新闻");
            }
        }
    }
}
