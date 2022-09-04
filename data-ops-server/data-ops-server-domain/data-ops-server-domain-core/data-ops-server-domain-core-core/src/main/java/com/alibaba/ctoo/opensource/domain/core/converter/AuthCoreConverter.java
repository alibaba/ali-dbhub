package com.alibaba.ctoo.opensource.domain.core.converter;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.domain.repository.dataobject.RoleAuthSourceDO;
import com.alibaba.ctoo.opensource.domain.api.model.AuthSourceDTO;
import com.alibaba.ctoo.opensource.domain.api.model.RoleAuthSourceDTO;
import com.alibaba.ctoo.opensource.domain.api.param.auth.AuthSourceQueryParam;
import com.alibaba.ctoo.opensource.domain.api.service.auth.AuthSourceDomainService;

import com.google.common.collect.Lists;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * 权限服务
 *
 * @author qiuyuyu
 * @date 2022/03/08
 */
@Mapper(componentModel = "spring")
public abstract class AuthCoreConverter {
    @Resource
    protected AuthSourceDomainService authSourceDomainService;

    /**
     * 转换
     *
     * @param list
     * @return
     */
    public abstract List<RoleAuthSourceDTO> do2dto(List<RoleAuthSourceDO> list);

    /**
     * 转换
     *
     * @param roleAuthSourceDO
     * @return
     */
    public abstract RoleAuthSourceDTO do2dto(RoleAuthSourceDO roleAuthSourceDO);

    @AfterMapping
    public void fillAuthSource(@MappingTarget RoleAuthSourceDTO roleAuthSourceDTO, RoleAuthSourceDO roleAuthSourceDO) {
        if (roleAuthSourceDO == null) {
            return;
        }
        AuthSourceQueryParam authSourceQueryParam = new AuthSourceQueryParam();
        authSourceQueryParam.setIdList(Lists.newArrayList(roleAuthSourceDO.getAuthSourceId()));
        List<AuthSourceDTO> authSourceList = authSourceDomainService.query(authSourceQueryParam).getData();
        if (authSourceList.isEmpty()) {
            return;
        }
        roleAuthSourceDTO.setAuthSource(authSourceList.get(0));
    }
}
