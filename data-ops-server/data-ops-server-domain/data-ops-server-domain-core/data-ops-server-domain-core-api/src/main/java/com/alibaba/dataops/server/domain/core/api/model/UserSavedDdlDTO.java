package com.alibaba.dataops.server.domain.core.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 我的保存表
 * </p>
 *
 * @author data-ops
 * @since 2022-09-18
 */
@Data
public class UserSavedDdlDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    /**
     * 数据源连接ID
     */
    private Long dataSourceId;

    /**
     * db名称
     */
    private String dataBaseName;

    /**
     * 保存名称
     */
    private String name;

    /**
     * 数据库类型
     */
    private String type;

    /**
     * ddl内容
     */
    private String ddl;
}
