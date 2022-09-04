package com.alibaba.ctoo.opensource.domain.repository.dataobject;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author qiuyuyu
 */
public class AuthSourceDO {
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
     *   前端资源配置
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String frontSource;

    /**
     * Database Column Remarks:
     *   资源名称
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String sourceName;

    /**
     * Database Column Remarks:
     *   后端资源url,多个用逗号隔开，可以匹配*号
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String url;

    /**
     * Database Column Remarks:
     *   排序字段 默认为0
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Integer sourceOrder;

    /**
     * Database Column Remarks:
     *   父资源id
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Long parentId;

    /**
     * Database Column Remarks:
     *   资源等级 L0 - 总的资源 L1 - 一级菜单 L2 - 二级菜单 L3 - 按钮权限(暂无)
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String sourceLevel;

    /**
     * Database Column Remarks:
     *   对应的系统
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String systemType;

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
        sb.append(", frontSource=").append(frontSource);
        sb.append(", sourceName=").append(sourceName);
        sb.append(", url=").append(url);
        sb.append(", sourceOrder=").append(sourceOrder);
        sb.append(", parentId=").append(parentId);
        sb.append(", sourceLevel=").append(sourceLevel);
        sb.append(", systemType=").append(systemType);
        sb.append("]");
        return sb.toString();
    }
}