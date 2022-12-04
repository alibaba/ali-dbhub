package com.alibaba.dataops.server.domain.support.dialect.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 表信息
 *
 * @author Jiaju Zhuang
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SpiTable {
    /**
     * 表名
     */
    private String name;

    /**
     * 描述
     */
    private String comment;
}

