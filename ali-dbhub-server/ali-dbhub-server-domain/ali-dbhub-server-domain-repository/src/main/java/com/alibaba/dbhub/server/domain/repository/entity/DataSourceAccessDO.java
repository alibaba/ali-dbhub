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
 * 数据源授权
 * </p>
 *
 * @author ali-dbhub
 * @since 2023-04-09
 */
@Getter
@Setter
@TableName("DATA_SOURCE_ACCESS")
public class DataSourceAccessDO implements Serializable {

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
     * 环境id
     */
    private Long dataSourceId;

    /**
     * 授权类型
     */
    private String accessObjectType;

    /**
     * 授权id,根据类型区分是用户还是团队
     */
    private Long accessObjectId;
}
