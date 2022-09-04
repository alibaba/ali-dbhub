package com.alibaba.ctoo.opensource.domain.core.converter;

import java.util.List;

import com.alibaba.ctoo.opensource.domain.repository.dataobject.UserRoleDO;
import com.alibaba.ctoo.opensource.domain.api.enums.RoleCodeEnum;
import com.alibaba.ctoo.opensource.domain.api.model.UserRoleDTO;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserRolePageQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserRoleQueryParam;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

/**
 * 转换器
 *
 * @author qiuyuyu
 * @date 2022/03/07
 */
@Mapper(componentModel = "spring", imports = RoleCodeEnum.class)
public abstract class UserRoleCoreConverter {

    /**
     * 转换
     *
     * @param list
     * @return
     */
    public abstract List<UserRoleDTO> do2dto(List<UserRoleDO> list);

    /**
     * 转换
     *
     * @param userRoleDO
     * @return
     */
    @Mappings({
        @Mapping(target = "role.roleCode", source = "roleCode"),
        @Mapping(target = "role.roleName", source = "roleType"),
        @Mapping(target = "user.userId", source = "userId"),
    })
    public abstract UserRoleDTO do2dto(UserRoleDO userRoleDO);

    /**
     * 转换
     *
     * @param param
     * @return
     */
    public abstract UserRolePageQueryParam param2param(UserRoleQueryParam param);

    ///**
    // * 转换
    // *
    // * @param param
    // * @return
    // */
    //public abstract CustomUserRoleSearchParam param2param(UserRolePageQueryFromSearchParam param);

    /**
     * 转换
     *
     * @param target
     * @param source
     */
    public abstract void addData(@MappingTarget UserRoleDTO target, UserRoleDTO source);

}
