package com.alibaba.dbhub.server.web.api.controller.ai;

import com.alibaba.dbhub.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dbhub.server.tools.common.util.OpenAiClientUtils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author moji
 * @version SqlAiController.java, v 0.1 2023年03月20日 20:09 moji Exp $
 * @date 2023/03/20
 */
@RestController
@RequestMapping("/api/open/ai")
public class SqlAiController {

    /**
     * 输入自然语言，返回SQL样例
     *
     * @param apiKey
     * @param prompt
     * @return
     */
    @GetMapping("/get_sql")
    public DataResult<String> get(String apiKey, String prompt) {
        return DataResult.of(OpenAiClientUtils.request(apiKey, prompt));
    }
}
