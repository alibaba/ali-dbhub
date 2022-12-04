package com.alibaba.dbhub.server.domain.api.param;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author moji
 * @version DataSourceExecuteParam.java, v 0.1 2022年10月14日 13:53 moji Exp $
 * @date 2022/10/14
 */
@Data
public class DlExecuteParam {

    /**
     * sql语句
     */
    @NotNull
    private String sql;

    /**
     * 控制台id
     */
    @NotNull
    private Long consoleId;

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
