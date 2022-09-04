package com.alibaba.ctoo.opensource.domain.core.service.impl.user;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.domain.repository.dataobject.UserRoleDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.UserRoleParam;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.UserRoleParam.Criteria;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.UserRoleParam.OrderCondition;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.UserRoleParam.SortType;
import com.alibaba.ctoo.opensource.domain.repository.mapper.CustomUserRoleMapper;
import com.alibaba.ctoo.opensource.domain.repository.mapper.UserRoleMapper;
import com.alibaba.ctoo.opensource.common.util.ContextUtils;
import com.alibaba.ctoo.opensource.domain.api.enums.RoleCodeEnum;
import com.alibaba.ctoo.opensource.domain.api.model.UserDTO;
import com.alibaba.ctoo.opensource.domain.api.model.UserRoleDTO;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserRoleCreateParam;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserRoleDeleteParam;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserRolePageQueryFromSearchParam;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserRolePageQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserRoleQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserRoleSelector;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserSelector;
import com.alibaba.ctoo.opensource.domain.api.service.auth.AuthDomainService;
import com.alibaba.ctoo.opensource.domain.api.service.user.UserCacheDomainService;
import com.alibaba.ctoo.opensource.domain.api.service.user.UserRoleDomainService;
import com.alibaba.ctoo.opensource.domain.core.converter.UserCoreConverter;
import com.alibaba.ctoo.opensource.domain.core.converter.UserRoleCoreConverter;
import com.alibaba.easytools.base.enums.DeletedIdEnum;
import com.alibaba.easytools.base.excption.BusinessException;
import com.alibaba.easytools.base.excption.CommonErrorEnum;
import com.alibaba.easytools.base.wrapper.result.ActionResult;
import com.alibaba.easytools.base.wrapper.result.ListResult;
import com.alibaba.easytools.base.wrapper.result.PageResult;
import com.alibaba.easytools.common.util.EasyStringUtils;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 用户角色服务
 *
 * @author qiuyuyu
 * @date 2022/03/22
 */
@Service
@Slf4j
public class UserRoleDomainServiceImpl implements UserRoleDomainService {
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private UserRoleCoreConverter userRoleCoreConverter;
    @Resource
    private UserCoreConverter userCoreConverter;
    @Resource
    private CustomUserRoleMapper customUserRoleMapper;
    @Resource
    private UserCacheDomainService userCacheDomainService;
    @Resource
    private AuthDomainService authDomainService;
    @Value("${system.admin-user-id}")
    private String adminUserId;

    @Override
    public PageResult<UserRoleDTO> queryPage(UserRolePageQueryParam param, UserRoleSelector selector) {
        UserRoleParam userRoleParam = new UserRoleParam();
        Criteria criteria = userRoleParam.createCriteria().andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode());
        if (CollectionUtils.isNotEmpty(param.getUserIdList())) {
            criteria.andUserIdInWhenPresent(param.getUserIdList().stream().map(EasyStringUtils::cutUserId).collect(
                Collectors.toList()));
        }

        criteria.andRoleCodeInWhenPresent(param.getRoleCodeList())
            .andIdInWhenPresent(param.getIdList());

        userRoleParam.setPagination(param.getPageNo(), param.getPageSize());
        //排序
        if (CollectionUtils.isNotEmpty(param.getOrderByList())) {
            param.getOrderByList().forEach(
                orderBy -> userRoleParam.appendOrderByClause(
                    OrderCondition.getEnumByName(orderBy.getOrderConditionName()),
                    SortType.getEnumByName(orderBy.getDirection().name())));
        }
        // 查询
        List<UserRoleDO> userRoleDataList = userRoleMapper.selectByParam(userRoleParam);
        List<UserRoleDTO> userRoleList = userRoleCoreConverter.do2dto(userRoleDataList);

        // 统计全部
        long totalCount = 0L;
        if (param.getEnableReturnCount()) {
            totalCount = userRoleMapper.countByParam(userRoleParam);
        }
        fillData(userRoleList, selector);
        return PageResult.of(userRoleList, totalCount, param.getPageNo(), param.getPageSize());
    }

    private void fillData(List<UserRoleDTO> userRoleList, UserRoleSelector selector) {
        if (selector == null || CollectionUtils.isEmpty(userRoleList)) {
            return;
        }
        fillUser(userRoleList, selector);
    }

    private void fillUser(List<UserRoleDTO> userRoleList, UserRoleSelector selector) {
        if (BooleanUtils.isNotTrue(selector.getUserDetail())) {
            return;
        }
        List<UserDTO> userList = userRoleList.stream().map(UserRoleDTO::getUser).collect(Collectors.toList());
        UserSelector userSelector = new UserSelector();
        userSelector.setDeptDetail(Boolean.TRUE);
        userCoreConverter.fillUserDetail(userList, userSelector);
    }

    @Override
    public ListResult<UserRoleDTO> queryList(UserRoleQueryParam param, UserRoleSelector selector) {
        UserRolePageQueryParam userRolePageQueryParam = userRoleCoreConverter.param2param(param);
        userRolePageQueryParam.queryAll();
        List<UserRoleDTO> userRoleList = queryPage(userRolePageQueryParam, selector).getData();
        return ListResult.of(userRoleList);
    }

    @Override
    public ListResult<UserRoleDTO> queryList(List<Long> idList, UserRoleSelector selector) {
        if (CollectionUtils.isEmpty(idList)) {
            return ListResult.empty();
        }
        UserRoleQueryParam userRoleQueryParam = new UserRoleQueryParam();
        userRoleQueryParam.setIdList(idList);
        return ListResult.of(queryList(userRoleQueryParam, selector).getData());
    }

    @Override
    public ActionResult create(UserRoleCreateParam param) {
        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setUserId(EasyStringUtils.cutUserId(param.getUserId()));
        userRoleDO.setRoleCode(param.getRoleCode());
        userRoleDO.setRoleType(RoleCodeEnum.getByCode(param.getRoleCode()));
        userRoleDO.setTenantId(ContextUtils.getContext().getTenantId());
        userRoleDO.setCreator(ContextUtils.getContext().getUserId());
        userRoleDO.setModifier(ContextUtils.getContext().getUserId());
        int createRes = userRoleMapper.insertSelective(userRoleDO);
        // 移除缓存
        authDomainService.removeAuthCache();
        userCacheDomainService.cacheEvict(param.getUserId());
        return createRes > 0
            ? ActionResult.isSuccess()
            : ActionResult.fail(CommonErrorEnum.COMMON_SYSTEM_ERROR);
    }

    @Override
    public ActionResult delete(UserRoleDeleteParam param) {
        List<UserRoleDTO> userRoleDataList = queryList(Lists.newArrayList(param.getId()), null).getData();
        if (CollectionUtils.isEmpty(userRoleDataList)) {
            throw new BusinessException(CommonErrorEnum.DATA_NOT_FOUND);
        }
        if (adminUserId.equals(userRoleDataList.get(0).getUserId()) && RoleCodeEnum.SUPER_ADMIN.getCode().equals(
            userRoleDataList.get(0).getRole().getRoleCode())) {
            throw new BusinessException("不可删除");
        }
        customUserRoleMapper.logicDelete(param.getId());
        authDomainService.removeAuthCache();
        userCacheDomainService.cacheEvict(userRoleDataList.get(0).getUserId());
        return ActionResult.isSuccess();
    }
}
