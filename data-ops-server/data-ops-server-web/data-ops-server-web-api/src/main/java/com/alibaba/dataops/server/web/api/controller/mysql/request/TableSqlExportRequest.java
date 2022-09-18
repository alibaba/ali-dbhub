package com.alibaba.dataops.server.web.api.controller.mysql.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author moji
 * @version ConnectionQueryRequest.java, v 0.1 2022年09月16日 14:23 moji Exp $
 * @date 2022/09/16
 */
@Data
public class TableSqlExportRequest {

    /**
     * 连接id
     */
    @NotNull
    private Long connectionId;

    /**
     * DB名称
     */
    @NotNull
    private String databaseName;

    /**
     * 表名称
     */
    @NotNull
    private String tableName;

}
