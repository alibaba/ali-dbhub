package com.alibaba.ctoo.opensource.domain.api.param.user;

import java.util.Date;

import lombok.Data;

/**
 * @author qiuyuyu
 * @date 2022/03/03
 */
@Data
public class UserCreateParam {

    /**
     * 工号
     */
    private String userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 花名
     */
    private String nickName;

    /**
     * 最后登陆时间
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

    /**
     * 租户
     */
    private String tenantId;

}
