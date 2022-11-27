package com.alibaba.dbhub.server.domain.core.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 测试表
 * </p>
 *
 * @author ali-dbhub
 * @since 2022-11-20
 */
@Getter
@Setter
@TableName("TEST")
public class TestDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 名字
     */
    private String name;

    /**
     * 时间
     */
    private LocalDateTime date;

    /**
     * 数字
     */
    private Integer number;
}
