package com.ruoyi.hospital.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.BodyInserters;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.time.Duration;

/**
 * AI聊天服务
 *
 * @author Lugod
 */
@Service
public class AiChatService {

    private static final Logger logger = LoggerFactory.getLogger(AiChatService.class);

    @Value("${ai.openai.api-key:sk-b89db8188ef941aa8c2aed9642deaea8}")
    private String apiKey;

    @Value("${ai.openai.base-url:https://api.deepseek.com/v1}")
    private String baseUrl;

    @Value("${ai.openai.chat.options.model:deepseek-chat}")
    private String model;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public AiChatService() {
        this.webClient = WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * 发送消息到DeepSeek API并获取回复
     *
     * @param userMessage 用户消息
     * @return AI回复
     */
    public Mono<String> sendMessage(String userMessage) {
        try {
            Map<String, Object> requestBody = buildRequestBody(userMessage);

            // 构建完整的API URL
            String apiUrl = baseUrl + "/chat/completions";

            return webClient.post()
                    .uri(apiUrl)
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .body(BodyInserters.fromValue(requestBody))
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(30))
                    .map(this::parseResponse)
                    .onErrorReturn("抱歉，AI服务暂时不可用，请稍后重试。");

        } catch (Exception e) {
            logger.error("发送消息到DeepSeek API失败", e);
            return Mono.just("抱歉，AI服务发生错误，请稍后重试。");
        }
    }

    /**
     * 构建请求体
     */
    private Map<String, Object> buildRequestBody(String userMessage) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("max_tokens", 2000);
        requestBody.put("temperature", 0.7);
        requestBody.put("stream", false);

        // 构建消息列表
        Map<String, String> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", "你是一个专业的医疗助手，请用简体中文回答用户的医疗健康相关问题。请提供准确、专业且易于理解的医疗建议。");

        Map<String, String> userMsg = new HashMap<>();
        userMsg.put("role", "user");
        userMsg.put("content", userMessage);

        requestBody.put("messages", List.of(systemMessage, userMsg));

        return requestBody;
    }

    /**
     * 解析API响应
     */
    private String parseResponse(String response) {
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode choices = jsonNode.get("choices");

            if (choices != null && choices.isArray() && choices.size() > 0) {
                JsonNode firstChoice = choices.get(0);
                JsonNode message = firstChoice.get("message");
                if (message != null) {
                    JsonNode content = message.get("content");
                    if (content != null) {
                        return content.asText();
                    }
                }
            }

            // 如果解析失败，返回错误信息
            JsonNode error = jsonNode.get("error");
            if (error != null) {
                String errorMessage = error.get("message").asText();
                logger.error("DeepSeek API错误: {}", errorMessage);
                return "AI服务返回错误: " + errorMessage;
            }

            return "抱歉，未能获取到有效的AI回复。";

        } catch (Exception e) {
            logger.error("解析DeepSeek API响应失败", e);
            return "抱歉，解析AI回复时发生错误。";
        }
    }
}
