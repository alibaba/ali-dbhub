package com.alibaba.dbhub.server.start.web;

import com.alibaba.easytools.log.constant.LogConstants;
import com.taobao.eagleeye.EagleEye;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 桥阶层日志拦截器
 *
 * @author 是仪
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LogOncePerRequestFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            MDC.put(LogConstants.EAGLEEYE_TRACE_ID, EagleEye.getTraceId());
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(LogConstants.EAGLEEYE_TRACE_ID);
        }
    }
}
