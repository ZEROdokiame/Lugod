package com.ruoyi.hospital.config;

import com.ruoyi.hospital.websocket.AiChatWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebSocket配置
 *
 * @author Lugod
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(aiChatWebSocketHandler(), "/ws/ai-chat")
                .setAllowedOrigins("*"); // 在生产环境中应该配置具体的域名
    }

    @Bean
    public AiChatWebSocketHandler aiChatWebSocketHandler() {
        return new AiChatWebSocketHandler();
    }
}
