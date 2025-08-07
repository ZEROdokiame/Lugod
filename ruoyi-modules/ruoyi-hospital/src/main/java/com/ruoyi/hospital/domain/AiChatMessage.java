package com.ruoyi.hospital.domain;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * AI聊天消息实体
 *
 * @author Lugod
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiChatMessage {

    /**
     * 消息内容
     */
    private String message;

    /**
     * 消息类型：user(用户消息) 或 ai(AI回复)
     */
    private String type;

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 用户ID
     */
    private String userId;

    public AiChatMessage(String message, String type) {
        this.message = message;
        this.type = type;
        this.timestamp = System.currentTimeMillis();
    }
}
