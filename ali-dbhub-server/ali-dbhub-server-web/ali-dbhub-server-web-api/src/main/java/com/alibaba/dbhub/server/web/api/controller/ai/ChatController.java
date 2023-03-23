package com.alibaba.dbhub.server.web.api.controller.ai;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.dbhub.server.web.api.controller.ai.config.LocalCache;
import com.alibaba.dbhub.server.web.api.controller.ai.listener.OpenAIEventSourceListener;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.unfbx.chatgpt.OpenAiStreamClient;
import com.unfbx.chatgpt.entity.chat.Message;
import com.unfbx.chatgpt.exception.BaseException;
import com.unfbx.chatgpt.exception.CommonError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * 描述：
 *
 * @author https:www.unfbx.com
 * @date 2023-03-01
 */
@RestController
@RequestMapping("/api/ai")
@Slf4j
public class ChatController {

    private final OpenAiStreamClient openAiStreamClient;

    public ChatController(OpenAiStreamClient openAiStreamClient) {
        this.openAiStreamClient = openAiStreamClient;
    }

    @GetMapping("/chat")
    @CrossOrigin
    public SseEmitter chat(@RequestParam("message") String msg, @RequestHeader Map<String, String> headers)
        throws IOException {
        //默认30秒超时,设置为0L则永不超时
        SseEmitter sseEmitter = new SseEmitter(0l);
        String uid = headers.get("uid");
        if (StrUtil.isBlank(uid)) {
            throw new BaseException(CommonError.SYS_ERROR);
        }
        String messageContext = (String)LocalCache.CACHE.get(uid);
        List<Message> messages = new ArrayList<>();
        if (StrUtil.isNotBlank(messageContext)) {
            messages = JSONUtil.toList(messageContext, Message.class);
            if (messages.size() >= 10) {
                messages = messages.subList(1, 10);
            }
            Message currentMessage = Message.builder().content(msg).role(Message.Role.USER).build();
            messages.add(currentMessage);
        } else {
            Message currentMessage = Message.builder().content(msg).role(Message.Role.USER).build();
            messages.add(currentMessage);
        }
        sseEmitter.send(SseEmitter.event().id(uid).name("连接成功！！！！").data(LocalDateTime.now()).reconnectTime(3000));
        sseEmitter.onCompletion(() -> {
            log.info(LocalDateTime.now() + ", uid#" + uid + ", on completion");
        });
        sseEmitter.onTimeout(
            () -> log.info(LocalDateTime.now() + ", uid#" + uid + ", on timeout#" + sseEmitter.getTimeout()));
        sseEmitter.onError(
            throwable -> {
                try {
                    log.info(LocalDateTime.now() + ", uid#" + "765431" + ", on error#" + throwable.toString());
                    sseEmitter.send(SseEmitter.event().id("765431").name("发生异常！").data(throwable.getMessage())
                        .reconnectTime(3000));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        );
        OpenAIEventSourceListener openAIEventSourceListener = new OpenAIEventSourceListener(sseEmitter);
        openAiStreamClient.streamChatCompletion(messages, openAIEventSourceListener);
        LocalCache.CACHE.put(uid, JSONUtil.toJsonStr(messages), LocalCache.TIMEOUT);
        return sseEmitter;
    }
}
