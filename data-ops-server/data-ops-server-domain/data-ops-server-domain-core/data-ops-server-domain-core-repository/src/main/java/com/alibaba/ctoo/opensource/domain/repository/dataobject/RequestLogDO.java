package com.alibaba.ctoo.opensource.domain.repository.dataobject;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author zhuangjiaju
 */
public class RequestLogDO {
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
     *   租户
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String tenantId;

    /**
     * Database Column Remarks:
     *   用户id
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String userId;

    /**
     * Database Column Remarks:
     *   跟踪编码
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String traceId;

    /**
     * Database Column Remarks:
     *   方法
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String method;

    /**
     * Database Column Remarks:
     *   模块
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String module;

    /**
     * Database Column Remarks:
     *   请求路径
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String path;

    /**
     * Database Column Remarks:
     *   查询条件
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String query;

    /**
     * Database Column Remarks:
     *   耗时 ms
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Long duration;

    /**
     * Database Column Remarks:
     *   开始时间
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Date startTime;

    /**
     * Database Column Remarks:
     *   结束时间
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private Date endTime;

    /**
     * Database Column Remarks:
     *   请求值
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String request;

    /**
     * Database Column Remarks:
     *   返回值
     *
     *
     * @mbg.generated
     */
    @Getter
    @Setter
    private String response;

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
        sb.append(", tenantId=").append(tenantId);
        sb.append(", userId=").append(userId);
        sb.append(", traceId=").append(traceId);
        sb.append(", method=").append(method);
        sb.append(", module=").append(module);
        sb.append(", path=").append(path);
        sb.append(", query=").append(query);
        sb.append(", duration=").append(duration);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", request=").append(request);
        sb.append(", response=").append(response);
        sb.append("]");
        return sb.toString();
    }
}