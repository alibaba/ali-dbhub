package com.alibaba.dbhub.server.tools.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.alibaba.fastjson2.JSON;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author moji
 * @version HttpUtils.java, v 0.1 2023年03月20日 19:22 moji Exp $
 * @date 2023/03/20
 */
@Slf4j
public class OpenAiClientUtils {

    private static String API_URL = "https://api.openai.com/v1/chat/completions";

    /**
     * 查询openai
     *
     * @param apiKey    openai官网的apikey
     * @param prompt    提示信息
     * @return
     * @throws Exception
     */
    public static String request(String apiKey, String prompt) {
        if (StringUtils.isBlank(apiKey) || StringUtils.isBlank(prompt)) {
            return null;
        }
        // 构建模型参数
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("model", "code-davinci-002");
        paramMap.put("temperature", "0");
        paramMap.put("max_tokens", 150);
        paramMap.put("top_p", "1.0");
        paramMap.put("frequency_penalty", 0.0);
        paramMap.put("presence_penalty", 0.0);
        paramMap.put("stop", Lists.newArrayList("#", ";"));
        paramMap.put("prompt", prompt);
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            // 设置请求秘钥
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);

            // 发送请求参数
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            PrintWriter printWriter = new PrintWriter(os);
            printWriter.write(JSON.toJSONString(paramMap));
            os.flush();

            // 获取返回结果
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (Exception exception) {
            log.error("请求openai接口异常", exception);
        }
        return null;
    }
}
