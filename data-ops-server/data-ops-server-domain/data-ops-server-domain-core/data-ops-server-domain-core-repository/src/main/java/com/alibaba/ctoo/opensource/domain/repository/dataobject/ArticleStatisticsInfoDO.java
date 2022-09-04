package com.alibaba.ctoo.opensource.domain.repository.dataobject;

import java.util.Date;

/**
 *
 * @author hyh
 */
public class ArticleStatisticsInfoDO {
    /**
     * Database Column Remarks:
     *   主键
     *
     *
     * @mbg.generated
     */
    private Long id;

    /**
     * Database Column Remarks:
     *   创建时间
     *
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     * Database Column Remarks:
     *   修改时间
     *
     *
     * @mbg.generated
     */
    private Date gmtModified;

    /**
     * Database Column Remarks:
     *   创建人
     *
     *
     * @mbg.generated
     */
    private String creator;

    /**
     * Database Column Remarks:
     *   修改人
     *
     *
     * @mbg.generated
     */
    private String modifier;

    /**
     * Database Column Remarks:
     *   逻辑删除标志 删除的时候 将当前字段设置成id，0 - 有效  其他有效
     *
     *
     * @mbg.generated
     */
    private Long deletedId;

    /**
     * Database Column Remarks:
     *   租户id
     *
     *
     * @mbg.generated
     */
    private String tenantId;

    /**
     * Database Column Remarks:
     *   博客id
     *
     *
     * @mbg.generated
     */
    private Long articleId;

    /**
     * Database Column Remarks:
     *   阅读量
     *
     *
     * @mbg.generated
     */
    private Long readCount;

    /**
     *
     * @return the value of article_statistics_info.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id the value for article_statistics_info.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return the value of article_statistics_info.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     *
     * @param gmtCreate the value for article_statistics_info.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     *
     * @return the value of article_statistics_info.gmt_modified
     *
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     *
     * @param gmtModified the value for article_statistics_info.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     *
     * @return the value of article_statistics_info.creator
     *
     * @mbg.generated
     */
    public String getCreator() {
        return creator;
    }

    /**
     *
     * @param creator the value for article_statistics_info.creator
     *
     * @mbg.generated
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     *
     * @return the value of article_statistics_info.modifier
     *
     * @mbg.generated
     */
    public String getModifier() {
        return modifier;
    }

    /**
     *
     * @param modifier the value for article_statistics_info.modifier
     *
     * @mbg.generated
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     *
     * @return the value of article_statistics_info.deleted_id
     *
     * @mbg.generated
     */
    public Long getDeletedId() {
        return deletedId;
    }

    /**
     *
     * @param deletedId the value for article_statistics_info.deleted_id
     *
     * @mbg.generated
     */
    public void setDeletedId(Long deletedId) {
        this.deletedId = deletedId;
    }

    /**
     *
     * @return the value of article_statistics_info.tenant_id
     *
     * @mbg.generated
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     *
     * @param tenantId the value for article_statistics_info.tenant_id
     *
     * @mbg.generated
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     *
     * @return the value of article_statistics_info.article_id
     *
     * @mbg.generated
     */
    public Long getArticleId() {
        return articleId;
    }

    /**
     *
     * @param articleId the value for article_statistics_info.article_id
     *
     * @mbg.generated
     */
    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    /**
     *
     * @return the value of article_statistics_info.read_count
     *
     * @mbg.generated
     */
    public Long getReadCount() {
        return readCount;
    }

    /**
     *
     * @param readCount the value for article_statistics_info.read_count
     *
     * @mbg.generated
     */
    public void setReadCount(Long readCount) {
        this.readCount = readCount;
    }

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
        sb.append(", articleId=").append(articleId);
        sb.append(", readCount=").append(readCount);
        sb.append("]");
        return sb.toString();
    }
}