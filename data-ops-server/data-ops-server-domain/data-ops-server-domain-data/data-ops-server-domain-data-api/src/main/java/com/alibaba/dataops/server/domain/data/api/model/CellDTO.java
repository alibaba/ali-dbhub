package com.alibaba.dataops.server.domain.data.api.model;

import java.math.BigDecimal;

import com.alibaba.dataops.server.domain.data.api.enums.CellTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 单元格类型
 *
 * @author Jiaju Zhuang
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class CellDTO {

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
    private BigDecimal bigDecimal;

    /**
     * 日期数据
     */
    private Long dateValue;

    /**
     * 二进制流
     */
    private byte[] byteValue;
}
