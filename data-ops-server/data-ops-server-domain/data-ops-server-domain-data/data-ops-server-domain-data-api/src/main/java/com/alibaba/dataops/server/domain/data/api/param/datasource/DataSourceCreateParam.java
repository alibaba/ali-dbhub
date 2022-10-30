package com.alibaba.dataops.server.domain.data.api.param.datasource;

import javax.validation.constraints.NotNull;

import com.alibaba.dataops.server.domain.data.api.enums.DbTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 数据源创建参数
 *
 * @author Jiaju Zhuang
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class DataSourceCreateParam {
    /**
     * 对应数据库存储的来源id
     * 确保不要重复，重复的情况下会弃用以前的连接，并重新创建
     */
    @NotNull
    private Long dataSourceId;

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
