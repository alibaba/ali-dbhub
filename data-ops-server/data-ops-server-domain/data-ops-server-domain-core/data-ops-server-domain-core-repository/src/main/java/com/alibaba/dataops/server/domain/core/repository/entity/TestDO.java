package com.alibaba.dataops.server.domain.core.repository.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 测试表
 * </p>
 *
 * @author data-ops
 * @since 2022-09-18
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
