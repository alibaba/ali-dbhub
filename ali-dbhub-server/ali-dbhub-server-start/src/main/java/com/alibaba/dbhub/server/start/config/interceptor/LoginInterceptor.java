/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alibaba.dbhub.server.start.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author jipengfei
 * @version : interceptor.java
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        //
        //String requestURL = request.getRequestURI();
        //if ("OPTIONS".equals(request.getMethod().toString())) {
        //    return true;
        //}
        //String token = request.getHeader("token");
        //Integer code;
        //if (StringUtils.isNotBlank(token)) {
        //    return true;
        //} else {
        //    response.sendRedirect("/login.html");
        //    return false;
        //}
        return true;
    }

}
