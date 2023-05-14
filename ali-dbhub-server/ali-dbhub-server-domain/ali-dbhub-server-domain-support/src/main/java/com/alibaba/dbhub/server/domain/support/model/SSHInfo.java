/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.model;

import lombok.Data;

/**
 * @author jipengfei
 * @version : SSHInfo.java
 */
@Data
public class SSHInfo {

    private boolean use;

    private String hostName;

    private String port;

    private String userName;

    private String localPort;

    private String authenticationType;

    private String password;

    private String keyFile;

    private String passphrase;
}