package com.alibaba.dbhub.server.start.web;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.function.Supplier;

import com.alibaba.easytools.log.config.WebLogAppender;
import com.alibaba.easytools.log.constant.LogConstants;
import com.alibaba.easytools.log.utils.LogListenerUtils;
import com.alibaba.easytools.log.utils.LogUtils;

import com.taobao.eagleeye.EagleEye;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.Sink;

/**
 * 日志输出策略
 *
 * @author 是仪
 */
@Slf4j
public class EasyLogSink implements Sink, InitializingBean {

    @Autowired(required = false)
    private WebLogAppender webLogAppender;

    private Supplier<String> customLog;

    @Override
    public void write(final Precorrelation precorrelation, final HttpRequest request) {
    }

    @Override
    public void write(final Correlation correlation, final HttpRequest request, final HttpResponse response) {
        try {
            printLog(correlation, request, response);
        } catch (Exception e) {
            log.error("记录日志异常", e);
        }
    }

    public void printLog(final Correlation correlation, final HttpRequest request, final HttpResponse response)
        throws IOException {
        // 封装log 对象
        WebLog webLog = new WebLog();

        String method = request.getMethod();
        // 路径
        String path = request.getPath();

        webLog.setMethod(method);
        webLog.setPath(LogUtils.cutLog(path));
        webLog.setQuery(LogUtils.cutLog(request.getQuery()));
        webLog.setDuration(correlation.getDuration().toMillis());
        webLog.setStartTime(LocalDateTime.ofInstant(correlation.getStart(), ZoneId.systemDefault()));
        webLog.setEndTime(LocalDateTime.ofInstant(correlation.getEnd(), ZoneId.systemDefault()));
        try {
            webLog.setRequest(LogUtils.cutLog(new String(request.getBody(), StandardCharsets.UTF_8)));
            webLog.setResponse(LogUtils.cutLog(new String(response.getBody(), StandardCharsets.UTF_8)));
        } catch (IOException e) {
            log.warn("获取日志的请求&返回异常，大概率是用户关闭了流。", e);
        }
        webLog.setIp(LogUtils.getClientIp(request));

        String pathAndQuery = path;
        if (StringUtils.isNotBlank(webLog.getQuery())) {
            pathAndQuery += "?" + webLog.getQuery();
        }
        try {
            // 由于拦截器在最外面 可能会没有traceid
            MDC.put(LogConstants.EAGLEEYE_TRACE_ID, EagleEye.getTraceId());
            if (customLog == null) {
                log.info("http : {}|{}|{}|{}|{}", webLog.getMethod(), pathAndQuery, webLog.getDuration(),
                    webLog.getRequest(), webLog.getResponse());
            } else {
                log.info("http : {}|{}|{}|{}|{}|{}", webLog.getMethod(), pathAndQuery, webLog.getDuration(),
                    webLog.getRequest(), webLog.getResponse(), customLog.get());
            }
            LogListenerUtils.afterWebCompletion(correlation, request, response, webLog);
        } finally {
            MDC.remove(LogConstants.EAGLEEYE_TRACE_ID);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (webLogAppender == null || webLogAppender.customLog() == null) {
            return;
        }
        customLog = webLogAppender.customLog();
    }
}
