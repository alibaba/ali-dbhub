package com.alibaba.dataops.server.web.api.controller.mysql.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.alibaba.dataops.server.web.api.controller.connection.request.DataSourceBaseRequest;

import lombok.Data;

/**
 * @author moji
 * @version ConnectionQueryRequest.java, v 0.1 2022年09月16日 14:23 moji Exp $
 * @date 2022/09/16
 */
@Data
public class DataExportRequest extends DataSourceBaseRequest {

    /**
     * 导出sql语句
     */
    @NotNull
    private String sql;

    /**
     * 导出行ID列表
     */
    private List<Long> exportIds;
}
