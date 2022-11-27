package com.alibaba.dbhub.server.domain.data.api.param.datasource;

import javax.validation.constraints.NotNull;

import com.alibaba.dbhub.server.domain.data.api.enums.DbTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 数据源测试参数
 *
 * @author Jiaju Zhuang
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
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
