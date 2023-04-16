package com.alibaba.dbhub.server.domain.api.param.environment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 环境选择器
 *
 * @author Jiaju Zhuang
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentSelector {
    /**
     * 创建人
     */
    private Boolean createUser;

    /**
     * 修改人用户
     */
    private Boolean modifiedUser;

}