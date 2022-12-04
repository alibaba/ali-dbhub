package com.alibaba.dbhub.server.web.api.controller.log.manual.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author moji
 * @version DdlCreateRequest.java, v 0.1 2022年09月18日 11:13 moji Exp $
 * @date 2022/09/18
 */
@Data
public class OperationUpdateRequest {

    /**
     * 主键
     */
    @NotNull
    private Long id;

    /**
     * 文件别名
     */
    private String name;

    /**
     * ddl内容
     */
    @NotNull
    private String ddl;

    /**
     * 更新状态 DRAFT/RELEASE
     */
    private String status;
}
