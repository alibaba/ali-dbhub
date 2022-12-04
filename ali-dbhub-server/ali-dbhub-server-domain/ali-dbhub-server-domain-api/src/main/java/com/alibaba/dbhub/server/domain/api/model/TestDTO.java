package com.alibaba.dbhub.server.domain.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 文章版本
 *
 * @author Jiaju Zhuang
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TestDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 名字
     */
    private String name;

}
