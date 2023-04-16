package com.alibaba.dbhub.server.web.api.controller.environment.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 环境列表请求
 *
 * @author 是仪
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentListRequest {

    /**
     * 空的
     */
    private String empty;
}
