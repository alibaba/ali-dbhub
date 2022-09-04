package com.alibaba.ctoo.opensource.domain.repository.dataobject;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author zhuangjiaju
 */
public class ArticleDO {
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
     *   文章状态
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String status;

    /**
     * Database Column Remarks:
     *   发布时间
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Date gmtRelease;

    /**
     * Database Column Remarks:
     *   用户修改时间
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Date gmtCustomModified;

    /**
     * Database Column Remarks:
     *   用户修改人
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String customModifier;

    /**
     * Database Column Remarks:
     *   激活的版本id
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Long activatedArticleVersionId;

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
        sb.append(", status=").append(status);
        sb.append(", gmtRelease=").append(gmtRelease);
        sb.append(", gmtCustomModified=").append(gmtCustomModified);
        sb.append(", customModifier=").append(customModifier);
        sb.append(", activatedArticleVersionId=").append(activatedArticleVersionId);
        sb.append("]");
        return sb.toString();
    }
}