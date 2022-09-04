package com.alibaba.ctoo.opensource.domain.api.model;

import lombok.Data;

/**
 * 员工部门
 *
 * @author qiuyuyu
 * @date 2022/03/07
 */
@Data
public class DeptDTO {

    /**
     * 员工工号
     */
    private String workNo;
    /**
     * 花名
     */
    private String nickName;

    /**
     * 员工姓名
     */
    private String name;

    /**
     * 姓名（花名）
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

}
