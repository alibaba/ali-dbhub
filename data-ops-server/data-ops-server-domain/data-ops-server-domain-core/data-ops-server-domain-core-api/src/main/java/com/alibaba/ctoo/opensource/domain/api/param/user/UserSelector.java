package com.alibaba.ctoo.opensource.domain.api.param.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户选择器
 *
 * @author qiuyuyu
 * @date 2022/03/03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSelector {

    /**
     * 角色列表
     *
     */
    private Boolean roleList;

    /**
     * 部门信息
     */
    private Boolean deptDetail;

}
