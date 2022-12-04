package com.alibaba.dbhub.server.web.api.controller.rdb.vo;

import java.math.BigDecimal;

import com.alibaba.dbhub.server.domain.support.enums.CellTypeEnum;

import lombok.Data;

/**
 * @author moji
 * @version CellVO.java, v 0.1 2022年10月23日 11:20 moji Exp $
 * @date 2022/10/23
 */
@Data
public class CellVO {

    /**
     * 单元格类型
     *
     * @see CellTypeEnum
     */
    private String type;

    /**
     * 字符串数据
     */
    private String stringValue;

    /**
     * 数字
     */
    private BigDecimal bigDecimalValue;

    /**
     * 日期数据
     */
    private Long dateValue;

    /**
     * 二进制流
     */
    private byte[] byteValue;

}
