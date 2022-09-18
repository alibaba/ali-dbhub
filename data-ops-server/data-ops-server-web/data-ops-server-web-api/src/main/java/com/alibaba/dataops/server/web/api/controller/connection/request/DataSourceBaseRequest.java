package com.alibaba.dataops.server.web.api.controller.connection.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author moji
 * @version MysqlBaseRequest.java, v 0.1 2022年09月18日 11:51 moji Exp $
 * @date 2022/09/18
 */
@Data
public class DataSourceBaseRequest {

    /**
     * 数据源id
     */
    @NotNull
    private Long dataSourceId;

    /**
     * DB名称
     */
    @NotNull
    private String databaseName;
}
