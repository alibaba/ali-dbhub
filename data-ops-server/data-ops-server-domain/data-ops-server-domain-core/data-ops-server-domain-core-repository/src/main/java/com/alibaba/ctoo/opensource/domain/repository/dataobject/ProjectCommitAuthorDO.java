package com.alibaba.ctoo.opensource.domain.repository.dataobject;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author zhuangjiaju
 */
public class ProjectCommitAuthorDO {
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
     *   作者email
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String email;

    /**
     * Database Column Remarks:
     *   作者人用户名
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String name;

    /**
     * Database Column Remarks:
     *   提交时间
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Date gmtCommit;

    /**
     * Database Column Remarks:
     *   提交人用户名
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String committerName;

    /**
     * Database Column Remarks:
     *   提交人邮箱
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String committerEmail;

    /**
     * Database Column Remarks:
     *   本次提交的sha
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String sha;

    /**
     * Database Column Remarks:
     *   提交的描述
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String message;

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
        sb.append(", gmtCommit=").append(gmtCommit);
        sb.append(", committerName=").append(committerName);
        sb.append(", committerEmail=").append(committerEmail);
        sb.append(", sha=").append(sha);
        sb.append(", message=").append(message);
        sb.append("]");
        return sb.toString();
    }
}