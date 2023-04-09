package com.alibaba.dbhub.server.domain.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户团队表
 * </p>
 *
 * @author ali-dbhub
 * @since 2023-04-09
 */
@Getter
@Setter
@TableName("DBHUB_USER_TEAM")
public class DbhubUserTeamDO implements Serializable {

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
     * 创建人用户id
     */
    private Long createUserId;

    /**
     * 修改人用户id
     */
    private Long modifiedUserId;

    /**
     * 用户id
     */
    private Long dbhubUserId;

    /**
     * 团队id
     */
    private Long teamId;
}
