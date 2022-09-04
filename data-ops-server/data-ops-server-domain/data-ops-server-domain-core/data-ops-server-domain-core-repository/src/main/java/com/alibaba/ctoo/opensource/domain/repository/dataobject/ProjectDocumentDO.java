package com.alibaba.ctoo.opensource.domain.repository.dataobject;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author zhuangjiaju
 */
public class ProjectDocumentDO {
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
     *   项目id
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Long projectId;

    /**
     * Database Column Remarks:
     *   文档源类型
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String sourceType;

    /**
     * Database Column Remarks:
     *   文档源地址
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String sourceUrl;

    /**
     * Database Column Remarks:
     *   文档源分支
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String sourceBranch;

    /**
     * Database Column Remarks:
     *   相对文档地址
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String sourcePath;

    /**
     * Database Column Remarks:
     *   文档刷新时间
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Date gmtRefresh;

    /**
     * Database Column Remarks:
     *   git的类型
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String sourceGitType;

    /**
     * Database Column Remarks:
     *   刷新成功时间
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Date gmtRefreshSuccess;

    /**
     * Database Column Remarks:
     *   文档源全称
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String sourceFullName;

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
        sb.append(", sourceType=").append(sourceType);
        sb.append(", sourceUrl=").append(sourceUrl);
        sb.append(", sourceBranch=").append(sourceBranch);
        sb.append(", sourcePath=").append(sourcePath);
        sb.append(", gmtRefresh=").append(gmtRefresh);
        sb.append(", sourceGitType=").append(sourceGitType);
        sb.append(", gmtRefreshSuccess=").append(gmtRefreshSuccess);
        sb.append(", sourceFullName=").append(sourceFullName);
        sb.append("]");
        return sb.toString();
    }
}