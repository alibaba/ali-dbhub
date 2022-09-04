package com.alibaba.ctoo.opensource.domain.core.service.impl.user;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.domain.repository.dataobject.UserDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.UserParam;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.UserParam.Criteria;
import com.alibaba.ctoo.opensource.domain.repository.mapper.UserMapper;
import com.alibaba.ctoo.opensource.common.constants.RedisConstant;
import com.alibaba.ctoo.opensource.common.util.ContextUtils;
import com.alibaba.ctoo.opensource.domain.api.model.DeptDTO;
import com.alibaba.ctoo.opensource.domain.api.model.UserDTO;
import com.alibaba.ctoo.opensource.domain.api.model.UserRoleDTO;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserCreateParam;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserRoleQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserSelector;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserUpdateParam;
import com.alibaba.ctoo.opensource.domain.api.service.user.UserCacheDomainService;
import com.alibaba.ctoo.opensource.domain.api.service.user.UserDomainService;
import com.alibaba.ctoo.opensource.domain.api.service.user.UserRoleDomainService;
import com.alibaba.ctoo.opensource.domain.core.converter.UserCoreConverter;

import com.alibaba.ctoo.opensource.integration.service.EmpIntegrationService;
import com.alibaba.easytools.base.enums.DeletedIdEnum;
import com.alibaba.easytools.base.excption.BusinessException;
import com.alibaba.easytools.base.excption.CommonErrorEnum;
import com.alibaba.easytools.base.wrapper.result.ActionResult;
import com.alibaba.easytools.base.wrapper.result.DataResult;
import com.alibaba.easytools.base.wrapper.result.ListResult;
import com.alibaba.easytools.common.util.EasyStringUtils;
import com.alibaba.easytools.spring.cache.EasyCache;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务
 *
 * @author qiuyuyu
 * @date 2022/03/04
 */
@Service
public class UserDomainServiceImpl implements UserDomainService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserCoreConverter userCoreConverter;
    @Resource
    private EmpIntegrationService empIntegrationService;
    @Resource
    private UserRoleDomainService userRoleDomainService;
    @Resource(name = "easyCache")
    private EasyCache<DataResult<UserDTO>> easyLoginUserCache;
    @Resource
    private UserCacheDomainService userCacheDomainService;

    @Override
    public DataResult<UserDTO> get(String userId, UserSelector selector) {
        List<UserDTO> userList = queryList(Lists.newArrayList(userId), selector).getData();
        if (CollectionUtil.isEmpty(userList)) {
            throw new BusinessException(CommonErrorEnum.DATA_NOT_FOUND);
        }
        return DataResult.of(userList.get(0));
    }

    @Override
    public DataResult<UserDTO> query(String userId, UserSelector selector) {
        List<UserDTO> userList = queryList(Lists.newArrayList(userId), selector).getData();
        if (CollectionUtil.isEmpty(userList)) {
            return DataResult.empty();
        }
        return DataResult.of(userList.get(0));
    }

    @Override
    public ListResult<UserDTO> queryList(List<String> userIdList, UserSelector selector) {
        if (CollectionUtil.isEmpty(userIdList)) {
            return ListResult.empty();
        }
        UserQueryParam userQueryParam = new UserQueryParam();
        userQueryParam.setUserIdList(userIdList);
        return queryList(userQueryParam, selector);
    }

    @Override
    public ListResult<UserDTO> queryList(UserQueryParam param, UserSelector selector) {
        UserParam userParam = new UserParam();
        Criteria criteria = userParam.createCriteria().andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode());
        criteria.andIdEqualToWhenPresent(param.getId())
            .andIdInWhenPresent(param.getIdList());
        if (param.getUserId() != null) {
            criteria.andUserIdEqualToWhenPresent(EasyStringUtils.cutUserId(param.getUserId()));
        }

        if (CollectionUtils.isNotEmpty(param.getUserIdList())){
            criteria.andUserIdInWhenPresent(
                param.getUserIdList().stream().map(EasyStringUtils::cutUserId).collect(Collectors.toList()));
        }

        List<UserDO> userDataList = userMapper.selectByParam(userParam);
        List<UserDTO> userList = userCoreConverter.do2dto(userDataList);
        fillData(userList, selector);
        return ListResult.of(userList);
    }

    private void fillData(List<UserDTO> userList, UserSelector selector) {
        if (selector == null || CollectionUtils.isEmpty(userList)) {
            return;
        }
        fillRole(userList, selector);
        fillDept(userList, selector);
    }

    private void fillDept(List<UserDTO> userList, UserSelector selector) {
        if (BooleanUtils.isNotTrue(selector.getDeptDetail())) {
            return;
        }
        for (UserDTO userDTO : userList) {
            if (ObjectUtils.allNotNull(userDTO.getDeptName(), userDTO.getDeptNo())) {
                continue;
            }
            List<DeptDTO> deptDTOList = empIntegrationService.queryByUserIdList(Lists.newArrayList(userDTO.getUserId()))
                .getData();
            if (CollectionUtil.isEmpty(deptDTOList)) {
                continue;
            }
            userDTO.setDeptName(deptDTOList.get(0).getDeptName());
            userDTO.setDeptNo(deptDTOList.get(0).getDeptNo());
        }
    }

    private void fillRole(List<UserDTO> userList, UserSelector selector) {
        if (BooleanUtils.isNotTrue(selector.getRoleList())) {
            return;
        }
        for (UserDTO userDTO : userList) {
            UserRoleQueryParam param = new UserRoleQueryParam();
            param.setUserIdList(Lists.newArrayList(userDTO.getUserId()));
            List<UserRoleDTO> userRoleList = userRoleDomainService.queryList(param, null).getData();
            if (CollectionUtil.isEmpty(userRoleList)) {
                return;
            }
            userDTO.setRoleList(userRoleList.stream().map(UserRoleDTO::getRole).collect(Collectors.toList()));
        }
    }

    @Override
    public DataResult<String> create(UserCreateParam param) {
        UserDO userDO = userCoreConverter.param2do(param);
        userDO.setCreator(ContextUtils.getContext().getUserId());
        userDO.setModifier(ContextUtils.getContext().getUserId());
        userDO.setTenantId(ContextUtils.getContext().getTenantId());
        userMapper.insertSelective(userDO);
        return DataResult.of(userDO.getUserId());
    }

    @Override
    public ActionResult update(UserUpdateParam param) {
        UserDTO user = get(param.getUserId(), null).getData();
        UserDO record = userCoreConverter.param2do(param);
        record.setId(user.getId());
        record.setModifier(ContextUtils.getContext().getUserId());
        int updateRes = userMapper.updateByPrimaryKeySelective(record);
        userCacheDomainService.cacheEvict(user.getUserId());
        return updateRes > 0
            ? ActionResult.isSuccess()
            : ActionResult.fail(CommonErrorEnum.COMMON_BUSINESS_ERROR);
    }

    @Override
    public DataResult<Map<String, UserDTO>> queryMap(List<String> userIdList, UserSelector selector) {
        List<UserDTO> userList = queryList(userIdList, selector).getData();
        Map<String, UserDTO> userMap = userList.stream().collect(
            Collectors.toMap(UserDTO::getUserId, Function.identity(), (oldvalue, newValue) -> newValue));
        return DataResult.of(userMap);
    }

    @Override
    public DataResult<UserDTO> loginUser(String userId) {
        String cacheKey = RedisConstant.USER_MANAGER + RedisConstant.USER_PREFIX + userId;
        return easyLoginUserCache.get(cacheKey,
            () -> query(userId, UserSelector.builder().roleList(Boolean.TRUE).build()),
            Duration.ofMinutes(10L).toMillis());
    }

    @Override
    public ActionResult updateGmtLastLogin(String userId) {
        UserDTO userDTO = get(userId, null).getData();
        UserDO record = new UserDO();
        record.setId(userDTO.getId());
        record.setGmtLastLogin(new Date());
        int updateRes = userMapper.updateByPrimaryKeySelective(record);
        return updateRes > 0
            ? ActionResult.isSuccess()
            : ActionResult.fail(CommonErrorEnum.COMMON_BUSINESS_ERROR);
    }
}
