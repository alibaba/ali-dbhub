package com.alibaba.dataops.server.web.api.controller.data.source.request;


import javax.validation.constraints.NotNull;

import com.alibaba.dataops.server.tools.base.enums.DataSourceTypeEnum;

import lombok.Data;

/**
 * @author moji
 * @version ConnectionCreateRequest.java, v 0.1 2022年09月16日 14:23 moji Exp $
 * @date 2022/09/16
 */
@Data
public class DataSourceAttachRequest {

    /**
     * 主键id
     */
    @NotNull
    private Long id;

}
