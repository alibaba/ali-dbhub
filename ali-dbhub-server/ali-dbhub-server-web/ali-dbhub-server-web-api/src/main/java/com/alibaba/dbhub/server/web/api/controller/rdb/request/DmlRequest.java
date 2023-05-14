package com.alibaba.dbhub.server.web.api.controller.rdb.request;

import javax.validation.constraints.NotNull;

import com.alibaba.dbhub.server.web.api.controller.data.source.request.DataSourceBaseRequest;
import com.alibaba.dbhub.server.web.api.controller.data.source.request.DataSourceConsoleRequestInfo;

import lombok.Data;

/**
 * @author moji
 * @version TableManageRequest.java, v 0.1 2022年09月16日 17:55 moji Exp $
 * @date 2022/09/16
 */
@Data
public class DmlRequest extends DataSourceBaseRequest implements DataSourceConsoleRequestInfo {

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
}
