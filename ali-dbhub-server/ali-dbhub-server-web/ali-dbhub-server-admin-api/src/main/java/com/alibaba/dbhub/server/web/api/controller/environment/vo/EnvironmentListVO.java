package com.alibaba.dbhub.server.web.api.controller.environment.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 *环境
 * @author 是仪
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentListVO {

    /**
     * 环境code
     */
    private String code;

    /**
     * 环境名称
     */
    private String name;
}
