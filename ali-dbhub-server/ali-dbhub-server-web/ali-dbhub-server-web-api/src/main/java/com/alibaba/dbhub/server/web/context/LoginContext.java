/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alibaba.dbhub.server.web.context;

/**
 * @author jipengfei
 * @version : LoginContext.java
 */
public class LoginContext {

    private static final ThreadLocal<SSOUser> SSO_USER = new ThreadLocal();

    public LoginContext() {
    }

    public static SSOUser getSSOUser() {
        return SSO_USER.get();
    }

    public static void setSSOUser(SSOUser user) {
        SSO_USER.set(user);
    }

    public static void setSSOUser(Long userId,String userName) {
        SSOUser ssoUser = new SSOUser();
        ssoUser.setUserId(userId);
        ssoUser.setUserName(userName);
        SSO_USER.set(ssoUser);
    }

}