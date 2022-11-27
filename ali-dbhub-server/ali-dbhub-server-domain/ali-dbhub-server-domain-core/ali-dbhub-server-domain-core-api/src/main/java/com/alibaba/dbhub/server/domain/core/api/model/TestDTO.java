package com.alibaba.dbhub.server.domain.core.api.model;

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
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
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
