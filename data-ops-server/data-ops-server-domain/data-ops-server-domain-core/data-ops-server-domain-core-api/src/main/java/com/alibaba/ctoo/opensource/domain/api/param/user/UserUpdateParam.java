package com.alibaba.ctoo.opensource.domain.api.param.user;

import java.util.Date;

import lombok.Data;

/**
 * @author qiuyuyu
 * @date 2022/03/03
 */
@Data
public class UserUpdateParam {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 最近登陆时间
     */
    private Date gmtLastLogin;

    /**
     * 部门编号
     */
    private String deptNo;

    /**
     * 部门名称
     */
    private String deptName;
}
