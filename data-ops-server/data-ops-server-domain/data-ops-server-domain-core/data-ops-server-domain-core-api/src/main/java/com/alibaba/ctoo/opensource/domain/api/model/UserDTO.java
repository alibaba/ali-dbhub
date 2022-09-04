package com.alibaba.ctoo.opensource.domain.api.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 用户对象
 *
 * @author 是仪
 */
@Data
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 排序id
     */
    private Long id;

    /**
     * 用户id
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
     * 姓名（花名） 如果没有花名 则只展示姓名
     */
    private String showName;

    /**
     * 部门编码
     */
    private String deptNo;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 最后登陆时间
     */
    private Date gmtLastLogin;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 角色列表
     */
    private List<RoleDTO> roleList;

}
