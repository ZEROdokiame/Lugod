package com.ruoyi.hospital.websocket;

import com.ruoyi.hospital.domain.AiChatMessage;
import com.ruoyi.hospital.service.AiChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

/**
 * AI聊天WebSocket处理器
 *
 * @author Lugod
 */
@Component
public class AiChatWebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(AiChatWebSocketHandler.class);

    @Autowired
    private AiChatService aiChatService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // 存储活跃的WebSocket会话
    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String sessionId = session.getId();
        sessions.put(sessionId, session);
        logger.info("WebSocket连接建立，会话ID: {}", sessionId);

        // 发送欢迎消息
        AiChatMessage welcomeMessage = new AiChatMessage(
            "您好！我是您的医疗AI助手，很高兴为您服务。请问有什么健康问题需要咨询吗？",
            "ai"
        );
        sendMessage(session, welcomeMessage);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            String payload = message.getPayload();
            logger.info("收到消息: {}", payload);

            // 解析用户消息
            AiChatMessage userMessage = objectMapper.readValue(payload, AiChatMessage.class);

            // 验证消息内容
            if (userMessage.getMessage() == null || userMessage.getMessage().trim().isEmpty()) {
                AiChatMessage errorMessage = new AiChatMessage("请输入有效的问题。", "ai");
                sendMessage(session, errorMessage);
                return;
            }

            // 发送"正在思考"状态
            AiChatMessage thinkingMessage = new AiChatMessage("正在思考中，请稍等...", "thinking");
            sendMessage(session, thinkingMessage);

            // 调用AI服务获取回复
            aiChatService.sendMessage(userMessage.getMessage())
                .subscribe(
                    aiReply -> {
                        try {
                            AiChatMessage aiMessage = new AiChatMessage(aiReply, "ai");
                            sendMessage(session, aiMessage);
                        } catch (Exception e) {
                            logger.error("发送AI回复失败", e);
                        }
                    },
                    error -> {
                        logger.error("AI服务调用失败", error);
                        try {
                            AiChatMessage errorMessage = new AiChatMessage(
                                "抱歉，AI服务暂时不可用，请稍后重试。", "ai"
                            );
                            sendMessage(session, errorMessage);
                        } catch (Exception e) {
                            logger.error("发送错误消息失败", e);
                        }
                    }
                );

        } catch (Exception e) {
            logger.error("处理WebSocket消息失败", e);
            AiChatMessage errorMessage = new AiChatMessage("消息处理失败，请重试。", "ai");
            sendMessage(session, errorMessage);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String sessionId = session.getId();
        sessions.remove(sessionId);
        logger.info("WebSocket连接关闭，会话ID: {}, 状态: {}", sessionId, status);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.error("WebSocket传输错误，会话ID: {}", session.getId(), exception);
        sessions.remove(session.getId());
    }

    /**
     * 发送消息到客户端
     */
    private void sendMessage(WebSocketSession session, AiChatMessage message) {
        try {
            if (session.isOpen()) {
                String messageJson = objectMapper.writeValueAsString(message);
                session.sendMessage(new TextMessage(messageJson));
            }
        } catch (Exception e) {
            logger.error("发送WebSocket消息失败", e);
        }
    }
}
