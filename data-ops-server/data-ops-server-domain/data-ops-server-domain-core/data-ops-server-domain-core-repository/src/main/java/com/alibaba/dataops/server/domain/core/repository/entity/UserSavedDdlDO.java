package com.alibaba.dataops.server.domain.core.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 我的保存表
 * </p>
 *
 * @author data-ops
 * @since 2022-09-25
 */
@Getter
@Setter
@TableName("USER_SAVED_DDL")
public class UserSavedDdlDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
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
    private String databaseName;

    /**
     * 保存名称
     */
    private String name;

    /**
     * 数据库类型
     */
    private String type;

    /**
     * ddl语句状态:DRAFT/RELEASE
     */
    private String status;

    /**
     * ddl内容
     */
    private String ddl;
}
