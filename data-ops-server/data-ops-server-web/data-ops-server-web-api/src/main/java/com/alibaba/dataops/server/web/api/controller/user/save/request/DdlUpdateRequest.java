package com.alibaba.dataops.server.web.api.controller.user.save.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author moji
 * @version DdlCreateRequest.java, v 0.1 2022年09月18日 11:13 moji Exp $
 * @date 2022/09/18
 */
@Data
public class DdlUpdateRequest {

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
}
