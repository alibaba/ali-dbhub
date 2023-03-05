/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alibaba.dbhub.server.web.context;

import lombok.Data;

/**
 * @author jipengfei
 * @version : SSOUser.java
 */
@Data
public class SSOUser {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;


}