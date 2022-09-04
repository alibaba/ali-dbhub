package com.alibaba.ctoo.opensource.domain.core.service.impl.auth;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.domain.repository.dataobject.RoleAuthSourceDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.RoleAuthSourceParam;
import com.alibaba.ctoo.opensource.domain.repository.mapper.RoleAuthSourceMapper;
import com.alibaba.ctoo.opensource.common.constants.RedisConstant;
import com.alibaba.ctoo.opensource.domain.api.enums.RoleCodeEnum;
import com.alibaba.ctoo.opensource.domain.api.model.AuthSourceDTO;
import com.alibaba.ctoo.opensource.domain.api.model.RoleAuthSourceDTO;
import com.alibaba.ctoo.opensource.domain.api.service.auth.AuthDomainService;
import com.alibaba.ctoo.opensource.domain.api.service.auth.AuthSourceDomainService;
import com.alibaba.ctoo.opensource.domain.core.converter.AuthCoreConverter;
import com.alibaba.easytools.base.constant.SymbolConstant;
import com.alibaba.easytools.base.enums.DeletedIdEnum;
import com.alibaba.easytools.base.wrapper.result.ActionResult;
import com.alibaba.easytools.base.wrapper.result.DataResult;
import com.alibaba.easytools.base.wrapper.result.ListResult;
import com.alibaba.easytools.spring.cache.EasyCache;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * @author qiuyuyu
 * @date 2022/03/22
 */
@Service
@Slf4j
public class AuthDomainServiceImpl implements AuthDomainService {

    @Resource
    private EasyCache<DataResult<Boolean>> easyAuthCache;
    @Resource
    private EasyCache<String> easyCache;
    @Resource
    private AuthSourceDomainService authSourceDomainService;
    @Resource
    private RoleAuthSourceMapper roleAuthSourceMapper;
    @Resource
    private AuthCoreConverter authCoreConverter;

    @Override
    public ListResult<RoleAuthSourceDTO> queryByRoles(List<String> roleCodeList) {
        RoleAuthSourceParam roleAuthSourceParam = new RoleAuthSourceParam();
        roleAuthSourceParam.createCriteria().andDeletedIdEqualTo(
            DeletedIdEnum.NOT_DELETED.getCode()).andRoleCodeIn(roleCodeList);
        List<RoleAuthSourceDO> roleAuthSourceDataList = roleAuthSourceMapper.selectByParam(roleAuthSourceParam);
        List<RoleAuthSourceDTO> roleAuthSourceList = authCoreConverter.do2dto(roleAuthSourceDataList);
        return ListResult.of(roleAuthSourceList);
    }

    @Override
    public DataResult<Boolean> checkAuth(List<String> roleCodeList, String url) {
        String cacheKey = RedisConstant.AUTH_CACHE_MANAGER + RedisConstant.ROLE_AUTH_PREFIX + roleCodeList.toString()
            + url;
        return easyAuthCache.get(cacheKey, () -> {
            if (CollectionUtils.isEmpty(roleCodeList)) {
                return DataResult.of(Boolean.FALSE);
            }
            // 超管直接返回TRUE
            boolean isSuperAdmin = roleCodeList.stream().anyMatch(
                roleCode -> RoleCodeEnum.SUPER_ADMIN.getCode().equals(roleCode));
            if (isSuperAdmin) {
                return DataResult.of(Boolean.TRUE);
            }

            // 获取角色的顶级权限
            List<RoleAuthSourceDTO> roleAuthSourceList = queryByRoles(roleCodeList).getData();
            List<AuthSourceDTO> parentAuthSourceList = roleAuthSourceList.stream()
                .map(RoleAuthSourceDTO::getAuthSource)
                .filter(Objects::nonNull).collect(Collectors.toList());

            // 没有权限
            if (CollectionUtils.isEmpty(roleAuthSourceList)) {
                return DataResult.of(Boolean.FALSE);
            }
            // 查询用户拥有的全部权限资源（父资源及其子资源）
            List<AuthSourceDTO> authSourceList = authSourceDomainService.queryByParentId(
                parentAuthSourceList.stream().map(AuthSourceDTO::getId).collect(Collectors.toList()), null).getData();

            // 根据路径计算
            PathMatcher matcher = new AntPathMatcher();
            for (AuthSourceDTO authSource : authSourceList) {
                if (StringUtils.isBlank(authSource.getUrl())) {
                    continue;
                }
                String[] authSourceUrls = authSource.getUrl().split(SymbolConstant.COMMA);
                for (String authSourceUrl : authSourceUrls) {
                    if (matcher.match(authSourceUrl, url)) {
                        return DataResult.of(Boolean.TRUE);
                    }
                }
            }
            return DataResult.of(Boolean.FALSE);
        }, Duration.ofMinutes(10L).toMillis());
    }

    @Override
    public ActionResult removeAuthCache() {
        easyAuthCache.delete(RedisConstant.AUTH_CACHE_MANAGER + RedisConstant.ROLE_AUTH_PREFIX,
            RedisConstant.AUTH_CACHE_MANAGER + RedisConstant.ROLE_L2_UGC_FRONT_SOURCE_PREFIX
        );
        log.info("移除授权缓存");
        return ActionResult.isSuccess();
    }
}
