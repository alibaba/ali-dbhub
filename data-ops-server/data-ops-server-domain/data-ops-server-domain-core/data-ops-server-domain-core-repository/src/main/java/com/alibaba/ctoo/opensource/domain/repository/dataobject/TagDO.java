package com.alibaba.ctoo.opensource.domain.repository.dataobject;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author zhuangjiaju
 */
public class TagDO {
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
     *   标签类型
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String tagType;

    /**
     * Database Column Remarks:
     *   标签编码
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String tagCode;

    /**
     * Database Column Remarks:
     *   标签值
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String tagValue;

    /**
     * Database Column Remarks:
     *   父标签id
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Long parentId;

    /**
     * Database Column Remarks:
     *   标签等级
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Integer tagLevel;

    /**
     * Database Column Remarks:
     *   排序
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Integer orderNum;

    /**
     * Database Column Remarks:
     *   图标编码
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String tagIcon;

    /**
     * Database Column Remarks:
     *   标签是否展示
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String tagShow;

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
        sb.append(", tagType=").append(tagType);
        sb.append(", tagCode=").append(tagCode);
        sb.append(", tagValue=").append(tagValue);
        sb.append(", parentId=").append(parentId);
        sb.append(", tagLevel=").append(tagLevel);
        sb.append(", orderNum=").append(orderNum);
        sb.append(", tagIcon=").append(tagIcon);
        sb.append(", tagShow=").append(tagShow);
        sb.append("]");
        return sb.toString();
    }
}