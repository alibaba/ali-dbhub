package com.alibaba.ctoo.opensource.domain.api.model;

import lombok.Data;

/**
 * 角色资源
 *
 * @author qiuyuyu
 * @date 2022/03/07
 */
@Data
public class RoleAuthSourceDTO {

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 权限资源
     */
    private AuthSourceDTO authSource;
}
