package com.alibaba.ctoo.opensource.domain.api.param.user;

import lombok.Data;

/**
 * @author qiuyuyu
 * @date 2022/03/04
 */
@Data
public class UserRoleCreateParam {

    /**
     * 员工id
     */
    private String userId;

    /**
     * 角色编码
     *
     * @see com.alibaba.ctoo.opensource.domain.api.enums.RoleCodeEnum
     */
    private String roleCode;

    /**
     * 转换
     */
    private String roleType;
}
