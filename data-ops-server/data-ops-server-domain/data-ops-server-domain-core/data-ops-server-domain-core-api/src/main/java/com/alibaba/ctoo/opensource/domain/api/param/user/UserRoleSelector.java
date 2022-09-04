package com.alibaba.ctoo.opensource.domain.api.param.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户角色选择器
 *
 * @author qiuyuyu
 * @date 2022/03/03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleSelector {

    /**
     * 用户信息
     */
    private Boolean userDetail;

}
