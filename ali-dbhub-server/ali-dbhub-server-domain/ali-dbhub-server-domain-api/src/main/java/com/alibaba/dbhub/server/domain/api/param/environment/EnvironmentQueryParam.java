package com.alibaba.dbhub.server.domain.api.param.environment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 查询
 *
 * @author 是仪
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentQueryParam {

    /**
     * 搜索关键词
     */
    private String searchKey;
}
