package com.alibaba.ctoo.opensource.domain.core.service.impl.banner;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.common.util.ContextUtils;
import com.alibaba.ctoo.opensource.domain.api.model.OperationBannerDTO;
import com.alibaba.ctoo.opensource.domain.api.param.article.OperationBannerPageQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.banner.BannerCreateParam;
import com.alibaba.ctoo.opensource.domain.api.param.banner.BannerUpdateParam;
import com.alibaba.ctoo.opensource.domain.api.param.banner.OperationBannerSelector;
import com.alibaba.ctoo.opensource.domain.api.service.banner.OperationBannerManageDomainService;
import com.alibaba.ctoo.opensource.domain.core.converter.OperationBannerManageCoreConverter;
import com.alibaba.ctoo.opensource.domain.core.converter.UserCoreConverter;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.OperationBannerDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.OperationBannerParam;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.OperationBannerParam.Criteria;
import com.alibaba.ctoo.opensource.domain.repository.mapper.CustomOperationBannerMapper;
import com.alibaba.ctoo.opensource.domain.repository.mapper.OperationBannerMapper;
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
import org.springframework.stereotype.Service;

/**
 * 运营管理/首页banner
 *
 * @author hyh
 */
@Service
public class OperationBannerManageDomainServiceImpl implements OperationBannerManageDomainService {

    @Resource
    private OperationBannerMapper operationBannerMapper;
    @Resource
    private OperationBannerManageCoreConverter operationBannerManageCoreConverter;
    @Resource
    private UserCoreConverter userCoreConverter;
    @Resource
    private CustomOperationBannerMapper customOperationBannerMapper;

    @Override
    public PageResult<OperationBannerDTO> queryPage(OperationBannerPageQueryParam param,
        OperationBannerSelector selector) {

        OperationBannerParam operationBannerParam = new OperationBannerParam();
        operationBannerParam.createCriteria()
            .andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode());
        operationBannerParam.setPagination(param.getPageNo(), param.getPageSize());
        //排序
        if (CollectionUtils.isNotEmpty(param.getOrderByList())) {
            param.getOrderByList().forEach(orderBy -> operationBannerParam.appendOrderByClause(
                OperationBannerParam.OrderCondition.getEnumByName(orderBy.getOrderConditionName()),
                OperationBannerParam.SortType.getEnumByName(orderBy.getDirection().name())));
        }

        List<OperationBannerDO> operationBannerList = operationBannerMapper.selectByParam(operationBannerParam);
        List<OperationBannerDTO> list = operationBannerManageCoreConverter.do2dto(operationBannerList);

        fillData(list, selector);

        // 统计全部
        long totalCount = 0L;
        if (param.getEnableReturnCount()) {
            totalCount = operationBannerMapper.countByParam(operationBannerParam);
        }

        return PageResult.of(list, totalCount, param);
    }

    @Override
    public ListResult<OperationBannerDTO> queryList(List<Long> idList, OperationBannerSelector selector) {
        // 组装查询条件
        OperationBannerParam operationBannerParam = new OperationBannerParam();
        Criteria criteria = operationBannerParam.createCriteria().andDeletedIdEqualTo(
            DeletedIdEnum.NOT_DELETED.getCode())
            .andIdInWhenPresent(idList);
        List<OperationBannerDO> operationBannerList = operationBannerMapper.selectByParam(operationBannerParam);
        // 查询不存在数据返回
        if (CollectionUtils.isEmpty(operationBannerList)) {
            return ListResult.empty();
        }
        List<OperationBannerDTO> list = operationBannerManageCoreConverter.do2dto(operationBannerList);

        // 填充数据
        fillData(list, selector);

        return ListResult.of(list);
    }

    @Override
    public DataResult<OperationBannerDTO> get(Long id, OperationBannerSelector selector) {
        if (id == null) {
            throw new BusinessException(CommonErrorEnum.DATA_NOT_FOUND.getCode());
        }

        List<OperationBannerDTO> operationBannerList = queryList(Lists.newArrayList(id), selector).getData();
        if (CollectionUtils.isEmpty(operationBannerList)) {
            throw new BusinessException(CommonErrorEnum.DATA_NOT_FOUND.getCode());
        }

        return DataResult.of(operationBannerList.get(0));
    }

    /**
     * 填充额外信息
     *
     * @param list
     * @param selector
     */
    private void fillData(List<OperationBannerDTO> list, OperationBannerSelector selector) {
        if (CollectionUtils.isEmpty(list) || selector == null) {
            return;
        }
        // 填充创建人
        fillCreatorDetail(list, selector);
        // 填充修改人
        fillModifierDetail(list, selector);
    }

    private void fillCreatorDetail(List<OperationBannerDTO> list, OperationBannerSelector selector) {
        if (BooleanUtils.isNotTrue(selector.getCreatorDetail())) {
            return;
        }

        userCoreConverter.fillUserDetail(EasyCollectionUtils.toList(list, OperationBannerDTO::getCreator), null);
    }

    private void fillModifierDetail(List<OperationBannerDTO> list, OperationBannerSelector selector) {
        if (BooleanUtils.isNotTrue(selector.getModifierDetail())) {
            return;
        }
        userCoreConverter.fillUserDetail(EasyCollectionUtils.toList(list, OperationBannerDTO::getModifier), null);
    }

    @Override
    public ActionResult create(BannerCreateParam param) {
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

        // 转换
        OperationBannerDO operationBannerCreate = operationBannerManageCoreConverter.param2do(param,
            ContextUtils.getContext().getTenantId(), ContextUtils.getContext().getUserId(),
            ContextUtils.getContext().getUserId());
        // 新增banner
        operationBannerMapper.insertSelective(operationBannerCreate);

        return ActionResult.isSuccess();
    }

    private void checkGmtStartEnd(Date gmtStart, Date gmtEnd) {
        if (!gmtEnd.after(gmtStart)) {
            throw new BusinessException("开始时间应小于结束时间");
        }
    }

    @Override
    public ActionResult update(BannerUpdateParam param) {
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
        OperationBannerDO operationBanner = operationBannerMapper.selectByPrimaryKey(param.getId());
        if (operationBanner == null) {
            throw new BusinessException(CommonErrorEnum.DATA_NOT_FOUND.getCode());
        }

        // 转换
        OperationBannerDO operationBannerUpdate = operationBannerManageCoreConverter.param2do(param,
            ContextUtils.getContext().getUserId());
        // 校验是否有效数据(无效数据可以直接修改)
        Date now = DateUtil.date();
        if (operationBanner.getGmtStart() == null) {
            if (operationBanner.getGmtEnd() == null || operationBanner.getGmtEnd().after(now)) {
                // 校验有效banner数量
                if (countBannerEffective(param.getId()) > 1) {
                    // 更新表operation_banner
                    operationBannerMapper.updateByPrimaryKeySelective(operationBannerUpdate);
                    if (gmtStart == null || gmtEnd == null) {
                        customOperationBannerMapper.updateGmtStartEndByPrimaryKey(param.getId(), gmtStart, gmtEnd);
                    }
                    return ActionResult.isSuccess();
                }
                // 修改后的数据必须是有效数据
                if (gmtEnd != null && gmtEnd.before(now)) {
                    throw new BusinessException("至少需要展示1张banner");
                }
                if (gmtStart != null && gmtStart.after(now)) {
                    throw new BusinessException("至少需要展示1张banner");
                }
            } else {
                // 更新表operation_banner
                operationBannerMapper.updateByPrimaryKeySelective(operationBannerUpdate);
                if (gmtStart == null || gmtEnd == null) {
                    customOperationBannerMapper.updateGmtStartEndByPrimaryKey(param.getId(), gmtStart, gmtEnd);
                }
                return ActionResult.isSuccess();
            }
        } else {
            if (operationBanner.getGmtStart().after(now)) {
                // 更新表operation_banner
                operationBannerMapper.updateByPrimaryKeySelective(operationBannerUpdate);
                if (gmtStart == null || gmtEnd == null) {
                    customOperationBannerMapper.updateGmtStartEndByPrimaryKey(param.getId(), gmtStart, gmtEnd);
                }
                return ActionResult.isSuccess();
            }
            // 校验有效banner数量
            if (countBannerEffective(param.getId()) > 1) {
                // 更新表operation_banner
                operationBannerMapper.updateByPrimaryKeySelective(operationBannerUpdate);
                if (gmtStart == null || gmtEnd == null) {
                    customOperationBannerMapper.updateGmtStartEndByPrimaryKey(param.getId(), gmtStart, gmtEnd);
                }
                return ActionResult.isSuccess();
            }
            // 修改后的数据必须是有效数据
            if (gmtEnd != null && gmtEnd.before(now)) {
                throw new BusinessException("至少需要展示1张banner");
            }
            if (gmtStart != null && gmtStart.after(now)) {
                throw new BusinessException("至少需要展示1张banner");
            }
        }
        // 更新表operation_banner
        operationBannerMapper.updateByPrimaryKeySelective(operationBannerUpdate);
        if (gmtStart == null || gmtEnd == null) {
            customOperationBannerMapper.updateGmtStartEndByPrimaryKey(param.getId(), gmtStart, gmtEnd);
        }

        return ActionResult.isSuccess();
    }

    private int countBannerEffective(Long id) {
        // 查询未删除banner的数量(有效数据)，排除当前需要删除的id剩余数量大于0才可以删除
        int countOperationBanner = 0;
        OperationBannerParam operationBannerParam = new OperationBannerParam();
        operationBannerParam.createCriteria()
            .andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode())
            .andIdNotEqualTo(id);
        List<OperationBannerDO> operationBannerList = operationBannerMapper.selectByParam(operationBannerParam);
        if (CollectionUtils.isEmpty(operationBannerList)) {
            return countOperationBanner;
        }
        Date now = DateUtil.date();
        for (OperationBannerDO operationBanner : operationBannerList) {
            // 开始时间为空，判断结束时间是否为空或者大于当前时间
            if (operationBanner.getGmtStart() == null) {
                if (operationBanner.getGmtEnd() == null || operationBanner.getGmtEnd().after(now)) {
                    countOperationBanner++;
                }
            } else {
                if (operationBanner.getGmtEnd() == null) {
                    if (operationBanner.getGmtStart().before(now)) {
                        countOperationBanner++;
                    }
                } else {
                    if (operationBanner.getGmtStart().before(now) && operationBanner.getGmtEnd().after(now)) {
                        countOperationBanner++;
                    }
                }
            }
        }
        return countOperationBanner;
    }

    @Override
    public ActionResult delete(Long id) {
        // 判断数据是否存在，存在可以删除，不存在抛出异常
        OperationBannerDO operationBanner = operationBannerMapper.selectByPrimaryKey(id);
        if (operationBanner == null) {
            throw new BusinessException(CommonErrorEnum.DATA_NOT_FOUND.getCode());
        }

        // 删除数据
        OperationBannerDO operationBannerUpdate = new OperationBannerDO();
        operationBannerUpdate.setId(id);
        operationBannerUpdate.setDeletedId(id);

        // 校验数据是否为有效数据(无效数据可以直接删除)
        Date now = DateUtil.date();
        if (operationBanner.getGmtStart() == null) {
            if (operationBanner.getGmtEnd() == null || operationBanner.getGmtEnd().after(now)) {
                // 校验有效banner
                checkBannerEffective(id);
            } else {
                // 删除数据
                operationBannerMapper.updateByPrimaryKeySelective(operationBannerUpdate);
                return ActionResult.isSuccess();
            }
        } else {
            if (operationBanner.getGmtStart().after(now)) {
                // 删除数据
                operationBannerMapper.updateByPrimaryKeySelective(operationBannerUpdate);
                return ActionResult.isSuccess();
            } else {
                // 校验有效banner
                checkBannerEffective(id);
            }
        }
        operationBannerMapper.updateByPrimaryKeySelective(operationBannerUpdate);
        return ActionResult.isSuccess();
    }

    private void checkBannerEffective(Long id) {
        // 查询未删除banner的数量(有效数据)，排除当前需要删除的id剩余数量大于0才可以删除，否则提示错误信息
        OperationBannerParam operationBannerParam = new OperationBannerParam();
        operationBannerParam.createCriteria().andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode())
            .andIdNotEqualTo(id);
        List<OperationBannerDO> operationBannerList = operationBannerMapper.selectByParam(operationBannerParam);
        if (CollectionUtils.isEmpty(operationBannerList)) {
            throw new BusinessException("至少需要展示1张banner");
        }
        int countOperationBanner = 0;
        Date now = DateUtil.date();
        for (OperationBannerDO operationBanner : operationBannerList) {
            // 开始时间为空，判断结束时间是否为空或者大于当前时间
            if (operationBanner.getGmtStart() == null) {
                if (operationBanner.getGmtEnd() == null || operationBanner.getGmtEnd().after(now)) {
                    countOperationBanner++;
                    break;
                }
            } else {
                if (operationBanner.getGmtEnd() == null) {
                    if (operationBanner.getGmtStart().before(now)) {
                        countOperationBanner++;
                        break;
                    }
                } else {
                    if (operationBanner.getGmtStart().before(now) && operationBanner.getGmtEnd().after(now)) {
                        countOperationBanner++;
                        break;
                    }
                }
            }
        }

        // 判断是否存在有效数据
        if (countOperationBanner == 0) {
            throw new BusinessException("至少需要展示1张banner");
        }
    }

}