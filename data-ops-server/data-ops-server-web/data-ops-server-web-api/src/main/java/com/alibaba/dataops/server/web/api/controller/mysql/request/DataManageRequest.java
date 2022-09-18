package com.alibaba.dataops.server.web.api.controller.mysql.request;

import javax.validation.constraints.NotNull;

import com.alibaba.dataops.server.web.api.controller.connection.request.DataSourceBaseRequest;

import lombok.Data;

/**
 * @author moji
 * @version TableManageRequest.java, v 0.1 2022年09月16日 17:55 moji Exp $
 * @date 2022/09/16
 */
@Data
public class DataManageRequest extends DataSourceBaseRequest {

    /**
     * sql语句
     */
    @NotNull
    private String sql;
}
