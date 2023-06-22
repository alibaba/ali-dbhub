package com.alibaba.dbhub.server.start.web;

import java.util.Objects;
import java.util.regex.Pattern;

import com.alibaba.easytools.common.util.EasyStringUtils;
import com.alibaba.easytools.common.util.function.Executor;
import com.alibaba.easytools.log.constant.LogConstants;

import cn.hutool.core.net.NetUtil;
import com.taobao.eagleeye.EagleEye;
import org.slf4j.MDC;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.HttpRequest;

/**
 * 日志工具类
 *
 * @author 是仪
 */
public class LogUtils {
    /**
     * 客户端ip的请求头
     */
    private static String[] CLIENT_IP_HEADERS = {"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP",
        "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};

    /**
     * 换行符
     */
    private static final Pattern LINE_FEED_PATTERN = Pattern.compile("\r|\n");

    /**
     * 去除换行符
     *
     * @param log
     * @return
     */
    public static String removeCrlf(String log) {
        if (Objects.isNull(log)) {
            return null;
        }
        return LINE_FEED_PATTERN.matcher(log).replaceAll("");
    }

    /**
     * 裁切日志
     *
     * @param log
     * @return
     */
    public static String cutLog(Object log) {
        if (Objects.isNull(log)) {
            return null;
        }
        return EasyStringUtils.limitString(removeCrlf(log.toString()), LogConstants.MAX_LOG_LENGTH);
    }

    /**
     * 在上下文中放入trace_id然后执行
     * 用于新建线程运行代码的情况
     *
     * @param executor
     * @return
     */
    public static void executeWithTraceId(String traceId, Executor executor) {
        try {
            MDC.put(LogConstants.EAGLEEYE_TRACE_ID, traceId);
            executor.execute();
        } finally {
            MDC.remove(LogConstants.EAGLEEYE_TRACE_ID);
        }
    }

    /**
     * 返回traceId
     *
     * @return
     */
    public static String traceId() {
        return EagleEye.getTraceId();
    }

    /**
     * 获取客户端ip
     *
     * @param request
     * @return
     */
    public static String getClientIp(HttpRequest request) {
        HttpHeaders httpHeaders = request.getHeaders();
        String ip;
        for (String header : CLIENT_IP_HEADERS) {
            ip = httpHeaders.getFirst(header);
            if (!NetUtil.isUnknown(ip)) {
                return NetUtil.getMultistageReverseProxyIp(ip);
            }
        }
        ip = request.getRemote();
        return NetUtil.getMultistageReverseProxyIp(ip);
    }
}
