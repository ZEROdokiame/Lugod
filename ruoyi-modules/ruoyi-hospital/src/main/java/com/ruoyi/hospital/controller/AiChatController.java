package com.ruoyi.hospital.controller;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.hospital.domain.AiChatMessage;
import com.ruoyi.hospital.service.AiChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AI聊天控制器
 *
 * @author Lugod
 */
@Tag(name = "AI聊天接口", description = "AI医疗助手聊天接口")
@RestController
@RequestMapping("/hospital/ai-chat")
public class AiChatController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AiChatController.class);

    @Autowired
    private AiChatService aiChatService;

    /**
     * 发送消息到AI（HTTP接口，用于测试或非WebSocket场景）
     */
    @Operation(summary = "发送消息到AI", description = "通过HTTP接口发送消息到AI助手")
    @PostMapping("/send")
    public AjaxResult sendMessage(@RequestBody AiChatMessage message) {
        try {
            if (message.getMessage() == null || message.getMessage().trim().isEmpty()) {
                return AjaxResult.error("消息内容不能为空");
            }

            // 调用AI服务
            String aiReply = aiChatService.sendMessage(message.getMessage()).block();

            AiChatMessage response = new AiChatMessage(aiReply, "ai");
            return AjaxResult.success(response);

        } catch (Exception e) {
            logger.error("AI聊天接口调用失败", e);
            return AjaxResult.error("AI服务暂时不可用，请稍后重试");
        }
    }

    /**
     * 获取WebSocket连接信息
     */
    @Operation(summary = "获取WebSocket连接信息", description = "获取AI聊天WebSocket端点信息")
    @GetMapping("/websocket-info")
    public AjaxResult getWebSocketInfo() {
        return AjaxResult.success()
                .put("endpoint", "/ws/ai-chat")
                .put("description", "AI聊天WebSocket端点");
    }
}
