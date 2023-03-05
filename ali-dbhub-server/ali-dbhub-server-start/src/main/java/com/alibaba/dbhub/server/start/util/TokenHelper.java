/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alibaba.dbhub.server.start.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.alibaba.dbhub.server.web.context.LoginContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jipengfei
 * @version : TokenHelper.java
 */
public class TokenHelper {
    public static boolean checkToken(String token) throws Exception {
        if (StringUtils.isBlank(token)) {
            return false;
        } else {
            //String userInfo = EncryptUtil.xORDecode(token);
            //if (!StringUtils.isBlank(userInfo) && userInfo.endsWith("mc") && userInfo.length() >= 17) {
            //    int length = userInfo.length();
            //    String expirationTime = userInfo.substring(length - 16, userInfo.length() - 2);
            //    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            //    Date expirationDate = simpleDateFormat.parse(expirationTime);
            //    if (expirationDate.before(new Date())) {
            //        return false;
            //    } else {
            //        String user = userInfo.substring(0, length - 16);
            //        String[] users = user.split("\\|");
            //        LoginContext.setSSOUser(Long.parseLong(users[0]), users[1]);
            //        return true;
            //    }
            //} else {
            //    return false;
            //}
            return false;
        }
    }
}