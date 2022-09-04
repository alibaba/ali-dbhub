package com.alibaba.ctoo.opensource.domain.repository.dataobject;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author zhuangjiaju
 */
public class ArticleVersionDO {
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
     *   标题
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String title;

    /**
     * Database Column Remarks:
     *   封面url
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String coverUrl;

    /**
     * Database Column Remarks:
     *   作者
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String author;

    /**
     * Database Column Remarks:
     *   内容富文本id
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Long contentRichTextId;

    /**
     * Database Column Remarks:
     *   文章摘要
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String description;

    /**
     * Database Column Remarks:
     *   文章类型
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String type;

    /**
     * Database Column Remarks:
     *   文章id
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Long articleId;

    /**
     * Database Column Remarks:
     *   是否激活
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String activated;

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
        sb.append(", title=").append(title);
        sb.append(", coverUrl=").append(coverUrl);
        sb.append(", author=").append(author);
        sb.append(", contentRichTextId=").append(contentRichTextId);
        sb.append(", description=").append(description);
        sb.append(", type=").append(type);
        sb.append(", articleId=").append(articleId);
        sb.append(", activated=").append(activated);
        sb.append("]");
        return sb.toString();
    }
}