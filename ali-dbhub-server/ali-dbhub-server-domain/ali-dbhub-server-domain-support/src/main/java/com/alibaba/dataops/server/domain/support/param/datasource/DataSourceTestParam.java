package com.alibaba.dataops.server.domain.support.param.datasource;

import javax.validation.constraints.NotNull;

import com.alibaba.dataops.server.domain.support.enums.DbTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 数据源测试参数
 *
 * @author Jiaju Zhuang
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceTestParam {

    /**
     * 数据库类型
     *
     * @see DbTypeEnum
     */
    @NotNull
    private String dbType;

    /**
     * 请求连接
     */
    @NotNull
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
