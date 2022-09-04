package com.alibaba.ctoo.opensource.domain.api.model;

import lombok.Data;

/**
 * 用户角色
 *
 * @author qiuyuyu
 * @date 2022/03/22
 */
@Data
public class UserRoleDTO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户信息
     */
    private UserDTO user;

    /**
     * 角色
     */
    private RoleDTO role;

    /**
     * 用户id
     */
    private String userId;

}
