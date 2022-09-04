package com.alibaba.ctoo.opensource.domain.core.service.impl.auth;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.domain.repository.dataobject.AuthSourceDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.AuthSourceParam;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.AuthSourceParam.Criteria;
import com.alibaba.ctoo.opensource.domain.repository.mapper.AuthSourceMapper;
import com.alibaba.ctoo.opensource.common.constants.RedisConstant;
import com.alibaba.ctoo.opensource.domain.api.enums.AuthSourceLevelEnum;
import com.alibaba.ctoo.opensource.domain.api.enums.SystemTypeEnum;
import com.alibaba.ctoo.opensource.domain.api.model.AuthSourceDTO;
import com.alibaba.ctoo.opensource.domain.api.model.RoleAuthSourceDTO;
import com.alibaba.ctoo.opensource.domain.api.param.auth.AuthSourceQueryParam;
import com.alibaba.ctoo.opensource.domain.api.service.auth.AuthDomainService;
import com.alibaba.ctoo.opensource.domain.api.service.auth.AuthSourceDomainService;
import com.alibaba.ctoo.opensource.domain.core.converter.AuthSourceCoreConverter;
import com.alibaba.easytools.base.enums.DeletedIdEnum;
import com.alibaba.easytools.base.wrapper.result.ListResult;
import com.alibaba.easytools.common.util.EasyCollectionUtils;
import com.alibaba.easytools.spring.cache.EasyCache;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 权限资源服务
 *
 * @author qiuyuyu
 * @date 2022/03/22
 */
@Service
public class AuthSourceDomainServiceImpl implements AuthSourceDomainService {
    @Resource(name = "easyCache")
    private EasyCache<ListResult<String>> easyCache;
    @Resource
    private AuthSourceMapper authSourceMapper;
    @Resource
    private AuthSourceCoreConverter authSourceCoreConverter;
    @Resource
    private AuthDomainService authDomainService;

    @Override
    public ListResult<AuthSourceDTO> queryByParentId(List<Long> parentIdList, String systemType) {
        if (CollectionUtil.isEmpty(parentIdList)) {
            return ListResult.empty();
        }
        // 查询用户的顶级权限
        AuthSourceParam authSourceParam = new AuthSourceParam();
        authSourceParam.createCriteria().
            andIdIn(parentIdList)
            .andSystemTypeEqualToWhenPresent(systemType)
            .andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode());
        List<AuthSourceDO> authSourceList = authSourceMapper.selectByParam(authSourceParam);

        // 查询其子资源
        List<AuthSourceDO> l0List = Lists.newArrayList();
        List<AuthSourceDO> l1List = Lists.newArrayList();
        for (AuthSourceDO authSourceDO : authSourceList) {
            if (AuthSourceLevelEnum.L0.getCode().equals(authSourceDO.getSourceLevel())) {
                l0List.add(authSourceDO);
                continue;
            }
            if (AuthSourceLevelEnum.L1.getCode().equals(authSourceDO.getSourceLevel())) {
                l1List.add(authSourceDO);
            }
        }

        // 查询所有l1 的放到l1
        if (CollectionUtils.isNotEmpty(l0List)) {
            authSourceParam = new AuthSourceParam();
            authSourceParam.createCriteria()
                .andParentIdIn(l0List.stream().map(AuthSourceDO::getId).collect(Collectors.toList()))
                .andSourceLevelEqualTo(AuthSourceLevelEnum.L1.getCode())
                .andSystemTypeEqualToWhenPresent(systemType)
                .andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode());
            List<AuthSourceDO> queryL1list = authSourceMapper.selectByParam(authSourceParam);
            l1List.addAll(queryL1list);
            authSourceList.addAll(queryL1list);
        }

        // 查询所有l2 的放到l2
        if (CollectionUtils.isNotEmpty(l1List)) {
            // 去重
            l1List = l1List.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(AuthSourceDO::getId))),
                ArrayList::new));
            authSourceParam = new AuthSourceParam();
            authSourceParam.createCriteria()
                .andParentIdIn(l1List.stream().map(AuthSourceDO::getId).collect(Collectors.toList()))
                .andSourceLevelEqualTo(AuthSourceLevelEnum.L2.getCode())
                .andSystemTypeEqualToWhenPresent(systemType)
                .andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode());
            authSourceList.addAll(authSourceMapper.selectByParam(authSourceParam));
        }

        // 查询通用的权限
        authSourceParam = new AuthSourceParam();
        authSourceParam.createCriteria()
            .andSourceLevelEqualTo(AuthSourceLevelEnum.COMMON.getCode())
            .andSystemTypeEqualToWhenPresent(systemType)
            .andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode());
        authSourceList.addAll(authSourceMapper.selectByParam(authSourceParam));

        // 总的去重
        authSourceList = authSourceList.stream().collect(Collectors.collectingAndThen(Collectors
            .toCollection(() -> new TreeSet<>(Comparator.comparing(AuthSourceDO::getId))), ArrayList::new));
        return ListResult.of(authSourceCoreConverter.do2dto(authSourceList));
    }

    @Override
    public ListResult<AuthSourceDTO> query(AuthSourceQueryParam param) {
        // 查询数据库
        AuthSourceParam authSourceParam = new AuthSourceParam();
        Criteria criteria = authSourceParam.createCriteria().andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode());
        if (param.getSourceLevel() != null) {
            criteria.andSourceLevelEqualTo(param.getSourceLevel());
        }
        if (param.getParentId() != null) {
            criteria.andParentIdEqualTo(param.getParentId());
        }
        criteria.andIdInWhenPresent(param.getIdList());
        List<AuthSourceDO> list = authSourceMapper.selectByParam(authSourceParam);
        if (CollectionUtils.isEmpty(list)) {
            return ListResult.empty();
        }
        return ListResult.of(authSourceCoreConverter.do2dto(list));
    }

    @Override
    public ListResult<String> queryAdminFrontSourceByRoleList(List<String> roleCodeList) {
        String cacheKey = RedisConstant.AUTH_CACHE_MANAGER + RedisConstant.ROLE_L2_UGC_FRONT_SOURCE_PREFIX
            + roleCodeList.toString();
        return easyCache.get(cacheKey, () -> {
            List<RoleAuthSourceDTO> roleAuthSourceList = authDomainService.queryByRoles(roleCodeList).getData();
            // 获取顶级资源
            List<AuthSourceDTO> parentAuthSourceList = roleAuthSourceList.stream()
                .map(RoleAuthSourceDTO::getAuthSource)
                .filter(Objects::nonNull).collect(Collectors.toList());

            List<AuthSourceDTO> authSourceList = queryByParentId(
                parentAuthSourceList.stream().map(AuthSourceDTO::getId).collect(Collectors.toList()),
                SystemTypeEnum.ADMIN.getCode()).getData();
            if (CollectionUtils.isEmpty(authSourceList)) {
                return ListResult.empty();
            }
            // 查询所有二级菜单的一级菜单 前端需要一二级都传
            AuthSourceQueryParam authSourceQueryParam = new AuthSourceQueryParam();
            authSourceQueryParam.setIdList(EasyCollectionUtils.toList(authSourceList, AuthSourceDTO::getParentId));
            ListResult<AuthSourceDTO> authSourceListOf = query(authSourceQueryParam);
            authSourceList.addAll(authSourceListOf.getData());

            return ListResult.of(authSourceList.stream()
                .filter(Objects::nonNull)
                .map(AuthSourceDTO::getFrontSource)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .collect(Collectors.toList()));
        }, Duration.ofMinutes(10L).toMillis());
    }
}
