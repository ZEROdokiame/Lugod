package com.ruoyi.hospital.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * AI聊天服务
 *
 * @author Lugod
 */
@Service
@Slf4j
public class AiChatService {


    @Value("${ai.openai.api-key:}")
    private String apiKey;

    @Value("${ai.openai.base-url:https://api.deepseek.com/v1}")
    private String baseUrl;

    @Value("${ai.openai.chat.options.model:deepseek-chat}")
    private String model;

    private final ChatClient chatClient;

    public AiChatService() {
        // 手动创建 OpenAI API 客户端
        OpenAiApi openAiApi = new OpenAiApi(baseUrl, apiKey);

        // 创建聊天选项
        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
                .withModel(model)
                .withTemperature(0.7f)
                .withMaxTokens(2000)
                .build();

        // 创建聊天模型
        OpenAiChatModel chatModel = new OpenAiChatModel(openAiApi, chatOptions);

        // 创建聊天客户端
        this.chatClient = ChatClient.builder(chatModel).build();
    }

    /**
     * 发送消息到DeepSeek API并获取回复
     *
     * @param userMessage 用户消息
     * @return AI回复
     */
    public Mono<String> sendMessage(String userMessage) {
        return Mono.fromCallable(() -> {
            try {
                return chatClient.prompt()
                        .system("你是一个专业的医疗助手，请用简体中文回答用户的医疗健康相关问题。请提供准确、专业且易于理解的医疗建议。")
                        .user(userMessage)
                        .call()
                        .content();
            } catch (Exception e) {
                log.error("发送消息到DeepSeek API失败", e);
                throw new RuntimeException("AI服务调用失败", e);
            }
        })
        .timeout(Duration.ofSeconds(30))
        .onErrorReturn("抱歉，AI服务暂时不可用，请稍后重试。");
    }
}
