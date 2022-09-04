package com.alibaba.ctoo.opensource.domain.repository.dataobject;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author zhuangjiaju
 */
public class UserDO {
    /**
     * Database Column Remarks:
     *   主键
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Long id;

    /**
     * Database Column Remarks:
     *   创建时间
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Date gmtCreate;

    /**
     * Database Column Remarks:
     *   修改时间
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Date gmtModified;

    /**
     * Database Column Remarks:
     *   创建人
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String creator;

    /**
     * Database Column Remarks:
     *   修改人
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String modifier;

    /**
     * Database Column Remarks:
     *   逻辑删除标志 删除的时候 将当前字段设置成id，0 - 有效  其他有效
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Long deletedId;

    /**
     * Database Column Remarks:
     *   租户id
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String tenantId;

    /**
     * Database Column Remarks:
     *   工号
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String userId;

    /**
     * Database Column Remarks:
     *   姓名
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String name;

    /**
     * Database Column Remarks:
     *   花名
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String nickName;

    /**
     * Database Column Remarks:
     *   最后登陆时间
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Date gmtLastLogin;

    /**
     * Database Column Remarks:
     *   部门编号
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String deptNo;

    /**
     * Database Column Remarks:
     *   部门名称
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String deptName;

    /**
     * @return
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", creator=").append(creator);
        sb.append(", modifier=").append(modifier);
        sb.append(", deletedId=").append(deletedId);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", userId=").append(userId);
        sb.append(", name=").append(name);
        sb.append(", nickName=").append(nickName);
        sb.append(", gmtLastLogin=").append(gmtLastLogin);
        sb.append(", deptNo=").append(deptNo);
        sb.append(", deptName=").append(deptName);
        sb.append("]");
        return sb.toString();
    }
}