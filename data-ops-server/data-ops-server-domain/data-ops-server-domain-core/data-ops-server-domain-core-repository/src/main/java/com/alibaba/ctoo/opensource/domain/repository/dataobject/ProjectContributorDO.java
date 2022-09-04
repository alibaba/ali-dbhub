package com.alibaba.ctoo.opensource.domain.repository.dataobject;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author liugong
 */
public class ProjectContributorDO {
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
     *   项目ID
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Long projectId;

    /**
     * Database Column Remarks:
     *   用户邮箱
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String email;

    /**
     * Database Column Remarks:
     *   用户昵称/登录名
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String name;

    /**
     * Database Column Remarks:
     *   用户类型
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String userType;

    /**
     * Database Column Remarks:
     *   贡献值
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Integer contributions;

    /**
     * Database Column Remarks:
     *   git类型
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String gitType;

    /**
     * Database Column Remarks:
     *   git 用户ID
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Long gitUserId;

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
        sb.append(", projectId=").append(projectId);
        sb.append(", email=").append(email);
        sb.append(", name=").append(name);
        sb.append(", userType=").append(userType);
        sb.append(", contributions=").append(contributions);
        sb.append(", gitType=").append(gitType);
        sb.append(", gitUserId=").append(gitUserId);
        sb.append("]");
        return sb.toString();
    }
}