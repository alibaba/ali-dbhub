package com.alibaba.dbhub.server.web.api.controller.user.save.request;

import javax.validation.constraints.NotNull;

import com.alibaba.dbhub.server.domain.data.api.enums.DbTypeEnum;
import com.alibaba.dbhub.server.tools.base.enums.StatusEnum;
import com.alibaba.dbhub.server.web.api.controller.data.source.request.DataSourceBaseRequest;

import lombok.Data;

/**
 * @author moji
 * @version DdlCreateRequest.java, v 0.1 2022年09月18日 11:13 moji Exp $
 * @date 2022/09/18
 */
@Data
public class DdlCreateRequest extends DataSourceBaseRequest {

    /**
     * 文件别名
     */
    private String name;

    /**
     * 保存状态
     * @see StatusEnum
     */
    @NotNull
    private String status;

    /**
     * ddl类型
     * @see DbTypeEnum
     */
    @NotNull
    private String type;

    /**
     * ddl内容
     */
    @NotNull
    private String ddl;
}
