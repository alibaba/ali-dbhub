package com.alibaba.ctoo.opensource.domain.repository.dataobject;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author liugong
 */
public class ProjectDO {
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
     *   项目名
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String name;

    /**
     * Database Column Remarks:
     *   点赞数量
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Integer starCount;

    /**
     * Database Column Remarks:
     *   分支数量
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Integer forkCount;

    /**
     * Database Column Remarks:
     *   github
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String githubUrl;

    /**
     * Database Column Remarks:
     *   gitee
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String giteeUrl;

    /**
     * Database Column Remarks:
     *   文档地址
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String documentUrl;

    /**
     * Database Column Remarks:
     *   官方网站
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String officialUrl;

    /**
     * Database Column Remarks:
     *   刷新日期
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Date gmtRefresh;

    /**
     * Database Column Remarks:
     *   激活的版本id
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Long activatedProjectVersionId;

    /**
     * Database Column Remarks:
     *   完整项目名
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String fullName;

    /**
     * Database Column Remarks:
     *   贡献者数量
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Integer contributorCount;

    /**
     * Database Column Remarks:
     *   是否展示
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String inShow;

    /**
     * Database Column Remarks:
     *   项目展示名称
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String showName;

    /**
     * Database Column Remarks:
     *   logo地址
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String logoUrl;

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
     *   是否加入主页数据统计
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String inData;

    /**
     * Database Column Remarks:
     *   所属组织
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String organization;

    /**
     * Database Column Remarks:
     *   项目描述
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String description;

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
        sb.append(", name=").append(name);
        sb.append(", starCount=").append(starCount);
        sb.append(", forkCount=").append(forkCount);
        sb.append(", githubUrl=").append(githubUrl);
        sb.append(", giteeUrl=").append(giteeUrl);
        sb.append(", documentUrl=").append(documentUrl);
        sb.append(", officialUrl=").append(officialUrl);
        sb.append(", gmtRefresh=").append(gmtRefresh);
        sb.append(", activatedProjectVersionId=").append(activatedProjectVersionId);
        sb.append(", fullName=").append(fullName);
        sb.append(", contributorCount=").append(contributorCount);
        sb.append(", inShow=").append(inShow);
        sb.append(", showName=").append(showName);
        sb.append(", logoUrl=").append(logoUrl);
        sb.append(", gitType=").append(gitType);
        sb.append(", inData=").append(inData);
        sb.append(", organization=").append(organization);
        sb.append(", description=").append(description);
        sb.append("]");
        return sb.toString();
    }
}