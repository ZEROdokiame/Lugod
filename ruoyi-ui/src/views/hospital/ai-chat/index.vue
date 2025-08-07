<template>
  <div class="ai-chat-container">
    <div class="chat-header">
      <h3>
        <i class="el-icon-chat-dot-square"></i>
        医疗AI助手
      </h3>
      <div class="connection-status">
        <span :class="['status-dot', connectionStatus]"></span>
        {{ statusText }}
      </div>
    </div>

    <div class="chat-messages" ref="messagesContainer">
      <div
        v-for="(message, index) in messages"
        :key="index"
        :class="['message', message.type]"
      >
        <div class="message-content">
          <div class="message-text" v-html="formatMessage(message.message)"></div>
          <div class="message-time">{{ formatTime(message.timestamp) }}</div>
        </div>
      </div>

      <!-- 正在输入指示器 -->
      <div v-if="isThinking" class="message ai thinking">
        <div class="message-content">
          <div class="typing-indicator">
            <span></span>
            <span></span>
            <span></span>
          </div>
        </div>
      </div>
    </div>

    <div class="chat-input">
      <el-input
        v-model="inputMessage"
        type="textarea"
        :rows="3"
        placeholder="请输入您的健康问题..."
        :disabled="!isConnected || isSending"
        @keydown.enter.ctrl="sendMessage"
        maxlength="500"
        show-word-limit
      ></el-input>

      <div class="input-actions">
        <el-button
          type="primary"
          icon="el-icon-s-promotion"
          :loading="isSending"
          :disabled="!inputMessage.trim() || !isConnected"
          @click="sendMessage"
        >
          发送 (Ctrl+Enter)
        </el-button>

        <el-button
          icon="el-icon-delete"
          @click="clearMessages"
        >
          清空对话
        </el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AiChat',
  data() {
    return {
      websocket: null,
      messages: [],
      inputMessage: '',
      isConnected: false,
      isSending: false,
      isThinking: false,
      reconnectAttempts: 0,
      maxReconnectAttempts: 3
    }
  },

  computed: {
    connectionStatus() {
      return this.isConnected ? 'connected' : 'disconnected'
    },

    statusText() {
      return this.isConnected ? '已连接' : '未连接'
    }
  },

  mounted() {
    this.initWebSocket()
  },

  beforeDestroy() {
    this.closeWebSocket()
  },

  methods: {
    /**
     * 初始化WebSocket连接
     */
    initWebSocket() {
      try {
        // 直连hospital模块，端口9204
        const wsUrl = `ws://localhost:9204/ws/ai-chat`

        this.websocket = new WebSocket(wsUrl)

        this.websocket.onopen = this.onWebSocketOpen
        this.websocket.onmessage = this.onWebSocketMessage
        this.websocket.onerror = this.onWebSocketError
        this.websocket.onclose = this.onWebSocketClose

      } catch (error) {
        console.error('WebSocket初始化失败:', error)
        this.$message.error('聊天服务连接失败')
      }
    },

    /**
     * WebSocket连接成功
     */
    onWebSocketOpen() {
      console.log('WebSocket连接成功')
      this.isConnected = true
      this.reconnectAttempts = 0
      this.$message.success('AI助手已连接')
    },

    /**
     * 接收WebSocket消息
     */
    onWebSocketMessage(event) {
      try {
        const message = JSON.parse(event.data)

        if (message.type === 'thinking') {
          this.isThinking = true
          return
        }

        this.isThinking = false
        this.messages.push(message)
        this.$nextTick(() => {
          this.scrollToBottom()
        })

        if (message.type === 'ai') {
          this.isSending = false
        }

      } catch (error) {
        console.error('解析消息失败:', error)
      }
    },

    /**
     * WebSocket错误处理
     */
    onWebSocketError(error) {
      console.error('WebSocket错误:', error)
      this.isConnected = false
      this.$message.error('聊天服务连接异常')
    },

    /**
     * WebSocket连接关闭
     */
    onWebSocketClose() {
      console.log('WebSocket连接关闭')
      this.isConnected = false
      this.isSending = false
      this.isThinking = false

      // 自动重连
      if (this.reconnectAttempts < this.maxReconnectAttempts) {
        setTimeout(() => {
          this.reconnectAttempts++
          console.log(`尝试重连 ${this.reconnectAttempts}/${this.maxReconnectAttempts}`)
          this.initWebSocket()
        }, 3000)
      } else {
        this.$message.error('聊天服务连接断开，请刷新页面重试')
      }
    },

    /**
     * 发送消息
     */
    sendMessage() {
      if (!this.inputMessage.trim() || !this.isConnected || this.isSending) {
        return
      }

      const userMessage = {
        message: this.inputMessage.trim(),
        type: 'user',
        timestamp: Date.now()
      }

      // 添加用户消息到界面
      this.messages.push(userMessage)

      // 发送到WebSocket
      this.websocket.send(JSON.stringify(userMessage))

      // 清空输入框并设置发送状态
      this.inputMessage = ''
      this.isSending = true

      this.$nextTick(() => {
        this.scrollToBottom()
      })
    },

    /**
     * 清空对话
     */
    clearMessages() {
      this.$confirm('确定要清空所有对话记录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.messages = []
        this.$message.success('对话记录已清空')
      }).catch(() => {})
    },

    /**
     * 关闭WebSocket连接
     */
    closeWebSocket() {
      if (this.websocket) {
        this.websocket.close()
        this.websocket = null
      }
    },

    /**
     * 滚动到底部
     */
    scrollToBottom() {
      const container = this.$refs.messagesContainer
      container.scrollTop = container.scrollHeight
    },

    /**
     * 格式化消息内容
     */
    formatMessage(message) {
      return message.replace(/\n/g, '<br>')
    },

    /**
     * 格式化时间
     */
    formatTime(timestamp) {
      if (!timestamp) return ''
      return new Date(timestamp).toLocaleTimeString()
    }
  }
}
</script>

<style scoped>
.ai-chat-container {
  display: flex;
  flex-direction: column;
  height: 600px;
  border: 1px solid #e4e7ed;
  border-radius: 8px;
  background: #fff;
}

.chat-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 20px;
  border-bottom: 1px solid #e4e7ed;
  background: #f5f7fa;
}

.chat-header h3 {
  margin: 0;
  color: #303133;
  font-size: 16px;
}

.connection-status {
  display: flex;
  align-items: center;
  font-size: 12px;
  color: #909399;
}

.status-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  margin-right: 6px;
}

.status-dot.connected {
  background: #67c23a;
}

.status-dot.disconnected {
  background: #f56c6c;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 16px;
  background: #d8f0d8;
}

.message {
  margin-bottom: 16px;
  display: flex;
}

.message.user {
  justify-content: flex-end;
}

.message.ai, .message.thinking {
  justify-content: flex-start;
}

.message-content {
  max-width: 70%;
  padding: 12px 16px;
  border-radius: 12px;
  position: relative;
}

.message.user .message-content {
  background: #409eff;
  color: white;
}

.message.ai .message-content, .message.thinking .message-content {
  background: white;
  border: 1px solid #e4e7ed;
  color: #303133;
}

.message-text {
  line-height: 1.5;
  word-wrap: break-word;
}

.message-time {
  font-size: 11px;
  opacity: 0.7;
  margin-top: 4px;
  text-align: right;
}

.message.ai .message-time {
  text-align: left;
}

.typing-indicator {
  display: flex;
  align-items: center;
  height: 20px;
}

.typing-indicator span {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: #909399;
  margin: 0 2px;
  animation: typing 1.4s infinite ease-in-out;
}

.typing-indicator span:nth-child(1) {
  animation-delay: -0.32s;
}

.typing-indicator span:nth-child(2) {
  animation-delay: -0.16s;
}

@keyframes typing {
  0%, 80%, 100% {
    transform: scale(0.8);
    opacity: 0.5;
  }
  40% {
    transform: scale(1);
    opacity: 1;
  }
}

.chat-input {
  padding: 16px;
  border-top: 1px solid #e4e7ed;
}

.input-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 12px;
}

/* 滚动条样式 */
.chat-messages::-webkit-scrollbar {
  width: 6px;
}

.chat-messages::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 3px;
}

.chat-messages::-webkit-scrollbar-thumb {
  background: #c1c1c1;
  border-radius: 3px;
}

.chat-messages::-webkit-scrollbar-thumb:hover {
  background: #a8a8a8;
}
</style>
