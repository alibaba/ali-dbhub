/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alibaba.dbhub.server.web.api.controller.config.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author jipengfei
 * @version : SystemConfigRequest.java
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SystemConfigRequest {

    private String code;

    private String content;
}