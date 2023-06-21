/** alibaba.com Inc. Copyright (c) 2004-2023 All Rights Reserved. */
package com.alibaba.dbhub.server.web.api.controller.ai.response;

import com.unfbx.chatgpt.entity.common.Usage;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * @author jipengfei
 * @version : ChatCompletionResponse.java
 */
@Data
public class ChatCompletionResponse implements Serializable {
    @Serial private static final long serialVersionUID = 4968922211204353592L;
    private String id;
    private String object;
    private long created;
    private String model;
    private List<ChatChoice> choices;
    private Usage usage;
}
