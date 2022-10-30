package com.alibaba.dataops.server.web.api.controller.rdb.vo;

import com.alibaba.dataops.server.domain.data.api.enums.ColumnTypeEnum;
import com.alibaba.dataops.server.tools.base.enums.YesOrNoEnum;

import lombok.Data;

/**
 * @author moji
 * @version TableVO.java, v 0.1 2022年09月16日 17:16 moji Exp $
 * @date 2022/09/16
 */
@Data
public class ColumnVO {

    /**
     * 名称
     */
    private String name;

    /**
     * 列的类型
     *
     * @see ColumnTypeEnum
     */
    private String type;

    /**
     * 是否为空
     *
     * @see YesOrNoEnum
     */
    private String nullable;

    /**
     * 是否主键
     *
     * @see YesOrNoEnum
     */
    private String primary;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 是否自增
     *
     * @see YesOrNoEnum
     */
    private String autoIncrement;

    /**
     * 数字精度
     */
    private Integer numericPrecision;

    /**
     * 数字比例
     */
    private Integer numericScale;

    /**
     * 字符串最大长度
     */
    private Integer characterMaximumLength;

    /**
     * 注释
     */
    private String comment;

}
