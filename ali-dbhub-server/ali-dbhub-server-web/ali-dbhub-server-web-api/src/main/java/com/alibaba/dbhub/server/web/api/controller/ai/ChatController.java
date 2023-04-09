package com.alibaba.dbhub.server.web.api.controller.ai;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.dbhub.server.domain.api.param.TableQueryParam;
import com.alibaba.dbhub.server.domain.api.service.TableService;
import com.alibaba.dbhub.server.domain.support.model.TableColumn;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.tools.base.excption.BusinessException;
import com.alibaba.dbhub.server.tools.base.excption.CommonErrorEnum;
import com.alibaba.dbhub.server.web.api.aspect.ConnectionInfoAspect;
import com.alibaba.dbhub.server.web.api.controller.ai.config.LocalCache;
import com.alibaba.dbhub.server.web.api.controller.ai.converter.ChatConverter;
import com.alibaba.dbhub.server.web.api.controller.ai.listener.OpenAIEventSourceListener;
import com.alibaba.dbhub.server.web.api.controller.ai.request.ChatQueryRequest;
import com.alibaba.fastjson2.JSON;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.model.Model;
import com.theokanning.openai.service.OpenAiService;
import com.unfbx.chatgpt.OpenAiStreamClient;
import com.unfbx.chatgpt.entity.chat.Message;
import com.unfbx.chatgpt.entity.completions.Completion;
import com.unfbx.chatgpt.exception.BaseException;
import com.unfbx.chatgpt.exception.CommonError;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@ConnectionInfoAspect
@RequestMapping("/api/ai")
@Slf4j
public class ChatController {

    private final OpenAiStreamClient openAiStreamClient;

    @Autowired
    private TableService tableService;

    @Autowired
    private ChatConverter chatConverter;

    @Value("${chatgpt.context.length}")
    private Integer contextLength;

    public ChatController(OpenAiStreamClient openAiStreamClient) {
        this.openAiStreamClient = openAiStreamClient;
    }

    /**
     * 问答对话模型
     *
     * @param msg
     * @param headers
     * @return
     * @throws IOException
     */
    @GetMapping("/chat1")
    @CrossOrigin
    public SseEmitter chat(@RequestParam("message") String msg, @RequestHeader Map<String, String> headers)
        throws IOException {
        //默认30秒超时,设置为0L则永不超时
        SseEmitter sseEmitter = new SseEmitter(0L);
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

    /**
     * SQL转换模型
     *
     * @param queryRequest
     * @param headers
     * @return
     * @throws IOException
     */
    @GetMapping("/chat")
    @CrossOrigin
    public SseEmitter completions(ChatQueryRequest queryRequest, @RequestHeader Map<String, String> headers)
        throws IOException {
        //默认30秒超时,设置为0L则永不超时
        SseEmitter sseEmitter = new SseEmitter(0L);
        String uid = headers.get("uid");
        if (StrUtil.isBlank(uid)) {
            throw new BusinessException(CommonErrorEnum.COMMON_SYSTEM_ERROR);
        }

        //提示消息不得为空
        if (StringUtils.isBlank(queryRequest.getMessage())) {
            throw new BusinessException(CommonErrorEnum.PARAM_ERROR);
        }

        String messageContext = (String)LocalCache.CACHE.get(uid);
        String msg = queryRequest.getMessage();
        Message currentMessage = Message.builder().content(msg).role(Message.Role.USER).build();
        List<Message> messages = new ArrayList<>();
        if (StrUtil.isNotBlank(messageContext)) {
            messages = JSONUtil.toList(messageContext, Message.class);
            if (messages.size() >= contextLength) {
                messages = messages.subList(1, contextLength);
            }
        }
        messages.add(currentMessage);
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

        // 查询schema信息
        String dataSourceType = DbTypeEnum.MYSQL.getCode();
        TableQueryParam queryParam = chatConverter.chat2tableQuery(queryRequest);
        Map<String, List<TableColumn>> tableColumns = buildTableColumn(queryParam);
        List<String> tableSchemas = tableColumns.entrySet().stream().map(
            entry -> String.format("%s(%s)", entry.getKey(),
                entry.getValue().stream().map(TableColumn::getName).collect(
                    Collectors.joining(", ")))).collect(Collectors.toList());
        String properties = String.join("\n#", tableSchemas);
        String prompt = CollectionUtils.isNotEmpty(tableSchemas) ? String.format(
            "### %s SQL tables, with their properties:\n#\n# %s\n#\n### %s", dataSourceType, properties,
            msg) : String.format("### %s", msg);
        currentMessage.setContent(prompt);

        // 获取返回结果
        OpenAIEventSourceListener openAIEventSourceListener = new OpenAIEventSourceListener(sseEmitter);
        Completion completion = Completion.builder().model("text-davinci-003").maxTokens(150).stream(true).stop(
            Lists.newArrayList("#", ";")).user(uid).prompt(prompt).build();
        openAiStreamClient.streamCompletions(completion, openAIEventSourceListener);
        messages.get(messages.size() - 1).setContent(msg);
        LocalCache.CACHE.put(uid, JSONUtil.toJsonStr(messages), LocalCache.TIMEOUT);
        return sseEmitter;

    }

    private Map<String, List<TableColumn>> buildTableColumn(TableQueryParam tableQueryParam) {
        List<TableColumn> tableColumns = tableService.queryColumns(tableQueryParam);
        StringBuilder prompt = new StringBuilder();
        if (CollectionUtils.isEmpty(tableColumns)) {
            return Maps.newHashMap();
        }
        return tableColumns.stream().collect(
            Collectors.groupingBy(TableColumn::getTableName, Collectors.toList()));

    }


    public static void main(String[] args) {
        OpenAiService openAiService = new OpenAiService("XXXXX");


        CompletionRequest completionRequest = CompletionRequest.builder()
            .model("cushman:2020-05-03")
            .prompt("SELECT * FROM users WHERE age > 18 AND gender = 'female'")
            .echo(true)
            .build();

        List<Model> models =  openAiService.listModels();
        System.out.println(JSON.toJSONString(models));
        openAiService.createCompletion(completionRequest).getChoices().forEach(choice -> {
            System.out.println(choice.getText());
        });


    }
}
