package com.alibaba.ctoo.opensource.domain.api.model;

import com.alibaba.ctoo.opensource.domain.api.enums.RoleCodeEnum;

import lombok.Data;

/**
 * 角色
 *
 * @author 是仪
 */
@Data
public class RoleDTO {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     *
     * @see RoleCodeEnum
     */
    private String roleCode;
}
