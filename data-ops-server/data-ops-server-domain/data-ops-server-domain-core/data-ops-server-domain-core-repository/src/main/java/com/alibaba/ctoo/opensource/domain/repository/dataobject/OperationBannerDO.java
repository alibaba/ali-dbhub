package com.alibaba.ctoo.opensource.domain.repository.dataobject;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author zhuangjiaju
 */
public class OperationBannerDO {
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
     *   图片地址
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String pictureUrl;

    /**
     * Database Column Remarks:
     *   调查地址
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String jumpUrl;

    /**
     * Database Column Remarks:
     *   0
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Integer orderNum;

    /**
     * Database Column Remarks:
     *   开始时间
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Date gmtStart;

    /**
     * Database Column Remarks:
     *   结束时间
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Date gmtEnd;

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
        sb.append(", pictureUrl=").append(pictureUrl);
        sb.append(", jumpUrl=").append(jumpUrl);
        sb.append(", orderNum=").append(orderNum);
        sb.append(", gmtStart=").append(gmtStart);
        sb.append(", gmtEnd=").append(gmtEnd);
        sb.append("]");
        return sb.toString();
    }
}