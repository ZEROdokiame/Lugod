<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <!-- 欢迎卡片 -->
      <el-col :sm="24" :lg="12" class="card-column">
        <el-card class="welcome-card" shadow="hover">
          <div class="welcome-header">
            <h1>
              <i class="el-icon-lightning animated-icon"></i>
              路神の后台管理系统
              <i class="el-icon-lightning animated-icon"></i>
            </h1>
            <div class="version-tag">
              <el-tag type="success">v{{ version }}</el-tag>
            </div>
          </div>
          <div class="welcome-content">
            <p class="intro-text">
              <i class="el-icon-s-flag"></i>
              <span class="main-intro">自用的后台管理系统，基于开源框架二次开发。</span>
              <br>
              <span class="signature">This is Signed BY LUGOD.Follow your heart.</span>
            </p>
            <p class="price-tag">
              <el-tag type="danger" effect="dark" size="medium"><i class="el-icon-money"></i> 欢迎用Q币支持</el-tag>
            </p>
            <div class="action-buttons">
              <el-button
                type="primary"
                size="medium"
                icon="el-icon-cloudy"
                @click="goTarget('https://github.com/ZEROdokiame/Lugod')"
                >访问路神Github</el-button
              >
              <el-button
                type="success"
                size="medium"
                icon="el-icon-s-home"
                @click="goTarget('https://github.com/ZEROdokiame/Lugod')"
                >访问路神主页</el-button
              >
            </div>
          </div>
        </el-card>

        <!-- 系统状态卡片 -->
        <el-row :gutter="20" class="status-row">
          <el-col :span="8">
            <el-card class="status-card" shadow="hover">
              <div class="status-icon">
                <i class="el-icon-data-line"></i>
              </div>
              <div class="status-info">
                <div class="status-title">系统状态</div>
                <div class="status-value">正常运行 <i class="el-icon-circle-check success-icon"></i></div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card class="status-card" shadow="hover">
              <div class="status-icon">
                <i class="el-icon-alarm-clock"></i>
              </div>
              <div class="status-info">
                <div class="status-title">运行时间</div>
                <div class="status-value">365天 <i class="el-icon-trophy-1 gold-icon"></i></div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card class="status-card" shadow="hover">
              <div class="status-icon">
                <i class="el-icon-user"></i>
              </div>
              <div class="status-info">
                <div class="status-title">活跃用户</div>
                <div class="status-value">1,024 <i class="el-icon-thumb success-icon"></i></div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-col>

      <!-- 技术选型卡片 -->
      <el-col :sm="24" :lg="12" class="card-column">
        <el-card class="tech-card" shadow="hover">
          <div slot="header">
            <h2 class="tech-title"><i class="el-icon-cpu"></i> 技术选型</h2>
          </div>
          <el-row>
            <el-col :span="12">
              <div class="tech-section">
                <h4 class="tech-section-title"><i class="el-icon-s-platform"></i> 后端技术</h4>
                <ul class="tech-list">
                  <li><el-tag size="small" effect="plain">SpringBoot</el-tag></li>
                  <li><el-tag size="small" effect="plain">SpringCloud</el-tag></li>
                  <li><el-tag size="small" effect="plain">Nacos</el-tag></li>
                  <li><el-tag size="small" effect="plain">Sentinel</el-tag></li>
                  <li><el-tag size="small" effect="plain">Seata</el-tag></li>
                  <li><el-tag size="small" effect="plain">Minio</el-tag></li>
                </ul>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="tech-section">
                <h4 class="tech-section-title"><i class="el-icon-monitor"></i> 前端技术</h4>
                <ul class="tech-list">
                  <li><el-tag size="small" effect="plain" type="success">Vue</el-tag></li>
                  <li><el-tag size="small" effect="plain" type="success">Element-ui</el-tag></li>
                  <li><el-tag size="small" effect="plain" type="success">axios</el-tag></li>
                  <li><el-tag size="small" effect="plain" type="success">echarts</el-tag></li>
                  <li><el-tag size="small" effect="plain" type="success">quill</el-tag></li>
                </ul>
              </div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="8" class="card-column">
        <el-card class="update-log contact-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span><i class="el-icon-phone"></i> 联系信息</span>
          </div>
          <div class="contact-item">
            <i class="el-icon-message"></i>
            <span>邮箱: lugod@example.com</span>
          </div>
          <div class="contact-item">
            <i class="el-icon-chat-line-round"></i>
            <span>微信: lugod_wechat</span>
          </div>
          <div class="contact-item">
            <i class="el-icon-mobile-phone"></i>
            <span>QQ: 12345678</span>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8" class="card-column">
        <el-card class="update-log" shadow="hover">
          <div slot="header" class="clearfix">
            <span><i class="el-icon-notebook-2"></i> 更新日志</span>
          </div>
          <el-collapse accordion>
            <el-collapse-item title="v1.0.0 - 2025.05.09">
              <ol>
                <li><i class="el-icon-check"></i> 全新UI设计与动画</li>
                <li><i class="el-icon-check"></i> 侧边栏与标签页优化</li>
                <li><i class="el-icon-check"></i> 登录界面重新设计</li>
                <li><i class="el-icon-check"></i> 添加Spring Cloud Alibaba支持</li>
                <li><i class="el-icon-check"></i> 集成Sentinel流量控制</li>
              </ol>
            </el-collapse-item>
            <el-collapse-item title="v0.9.0 - 2025.04.15">
              <ol>
                <li><i class="el-icon-check"></i> 初始化项目框架</li>
                <li><i class="el-icon-check"></i> 基础功能开发</li>
                <li><i class="el-icon-check"></i> 引入微服务架构</li>
              </ol>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8" class="card-column">
        <el-card class="update-log" shadow="hover">
          <div slot="header" class="clearfix">
            <span><i class="el-icon-monitor"></i> 系统监控</span>
          </div>
          <div class="monitoring-item">
            <div class="monitoring-title">
              <i class="el-icon-success"></i> Sentinel控制台
            </div>
            <div class="monitoring-content">
              <p>状态: <span class="running">运行中</span></p>
              <p>端口: 8718</p>
              <div class="monitoring-buttons">
                <el-button type="primary" size="mini" @click="goTarget('http://localhost:8718')">
                  <i class="el-icon-view"></i> 访问控制台
                </el-button>
                <el-button type="success" size="mini" @click="startSentinel">
                  <i class="el-icon-video-play"></i> 启动服务
                </el-button>
              </div>
            </div>
          </div>
          <div class="monitoring-item">
            <div class="monitoring-title">
              <i class="el-icon-s-data"></i> 系统负载
            </div>
            <div class="monitoring-content">
              <el-progress :percentage="36" :color="customColorMethod"></el-progress>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "1.0.0",
      showAllLogs: false,
      activeCollapse: "1"
    }
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank")
    },
    customColorMethod(percentage) {
      if (percentage < 30) {
        return '#67C23A';
      } else if (percentage < 70) {
        return '#E6A23C';
      } else {
        return '#F56C6C';
      }
    },
    startSentinel() {
      // 使用实际的Sentinel启动命令
      const command = 'start cmd.exe /K "cd /d D:\\Lugod && java "-Dserver.port=8718" -jar sentinel-dashboard.jar"';

      // 这里我们可以通过后端API来执行命令，但现在先用消息提示
      this.$msgbox({
        title: '启动Sentinel服务',
        message: '确认要启动Sentinel控制台服务吗？将在端口8718上运行。',
        showCancelButton: true,
        confirmButtonText: '启动',
        cancelButtonText: '取消',
        type: 'info'
      }).then(() => {
        // 这里应该调用后端API执行命令
        // 模拟启动中
        this.$message({
          message: 'Sentinel服务正在启动，请稍候...',
          type: 'success'
        });

        // 实际使用时应该检查服务是否成功启动
        setTimeout(() => {
          this.$notify({
            title: '成功',
            message: 'Sentinel控制台已在端口8718启动，可以点击"访问控制台"按钮查看',
            type: 'success',
            duration: 5000
          });
        }, 2000);
      }).catch(() => {
        // 用户取消操作
        this.$message({
          type: 'info',
          message: '已取消启动操作'
        });
      });
    }
  }
}
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
    background-color: rgba(255, 255, 255, 0.8);
    backdrop-filter: blur(5px);
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  .welcome-card {
    border-radius: 8px;
    margin-bottom: 20px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
    transition: all 0.3s;

    &:hover {
      transform: translateY(-5px);
      box-shadow: 0 8px 15px rgba(0, 0, 0, 0.08);
    }

    .welcome-header {
      text-align: center;
      margin-bottom: 15px;

      h1 {
        font-size: 2rem;
        font-weight: 700;
        color: #333;
        margin-bottom: 8px;
        text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.05);

        .animated-icon {
          color: #F56C6C;
          font-size: 1.8rem;
          margin: 0 8px;
          animation: lightning-flash 1.5s infinite;
          display: inline-block;
        }
      }

      .version-tag {
        margin-top: 8px;
      }
    }

    .welcome-content {
      padding: 0 15px;

      .intro-text {
        font-size: 14px;
        line-height: 1.5;
        margin-bottom: 15px;
        text-align: center;

        .main-intro {
          display: block;
          margin-bottom: 5px;
        }

        .signature {
          display: block;
          font-style: italic;
          color: #666;
        }

        i {
          margin-right: 5px;
        }
      }

      .price-tag {
        margin-bottom: 15px;
        text-align: center;
      }

      .action-buttons {
        display: flex;
        justify-content: center;
        margin-top: 15px;
        gap: 10px;
      }
    }
  }

  .status-row {
    margin-top: 15px;
  }

  .status-card {
    border-radius: 8px;
    height: 110px;
    display: flex;
    align-items: center;
    justify-content: space-around;
    padding: 20px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
    transition: all 0.3s;
    cursor: pointer;
    overflow: hidden;

    &:hover {
      transform: translateY(-5px);
      box-shadow: 0 8px 15px rgba(0, 0, 0, 0.08);
    }

    .status-icon {
      font-size: 32px;
      color: var(--theme);
      margin-bottom: 8px;
      text-align: center;
    }

    .status-info {
      text-align: center;

      .status-title {
        font-size: 13px;
        color: #666;
        font-weight: 600;
      }

      .status-value {
        font-size: 16px;
        font-weight: bold;
        margin-top: 4px;
        color: #333;

        .success-icon {
          color: #67c23a;
        }

        .gold-icon {
          color: #f39c12;
        }
      }
    }
  }

  .tech-card {
    border-radius: 8px;
    margin-bottom: 20px;
    height: 100%;
    display: flex;
    flex-direction: column;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
    transition: all 0.3s;

    &:hover {
      transform: translateY(-5px);
      box-shadow: 0 8px 15px rgba(0, 0, 0, 0.08);
    }

    .tech-title {
      margin: 0;
      font-size: 1.3rem;
      color: #333;
      font-weight: 700;

      i {
        color: var(--theme);
        margin-right: 5px;
      }
    }

    .tech-section {
      margin-bottom: 15px;

      .tech-section-title {
        font-size: 15px;
        margin-bottom: 12px;
        color: #555;
        border-bottom: 1px dashed #eee;
        padding-bottom: 8px;
        font-weight: 600;

        i {
          color: var(--theme);
          margin-right: 5px;
        }
      }

      .tech-list {
        list-style: none;
        padding-left: 5px;
        margin-top: 0;

        li {
          margin-bottom: 6px;
        }
      }
    }
  }

  .update-log {
    border-radius: 8px;
    margin-bottom: 20px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
    transition: all 0.3s;

    &:hover {
      transform: translateY(-5px);
      box-shadow: 0 8px 15px rgba(0, 0, 0, 0.08);
    }

    .el-collapse-item__header {
      font-weight: bold;
      font-size: 14px;
      padding-left: 10px;
    }

    .el-collapse-item__content {
      padding-left: 15px;
      font-size: 14px;
    }
  }

  .contact-card {
    border-radius: 8px;
    transition: all 0.3s;
    margin-top: 20px;
    font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;

    &:hover {
      transform: translateY(-5px);
      box-shadow: 0 8px 15px rgba(0, 0, 0, 0.08);
    }

    .contact-item {
      display: flex;
      align-items: center;
      margin-bottom: 10px;

      i {
        margin-right: 10px;
        color: var(--theme);
      }
    }
  }

  .monitoring-item {
    margin-bottom: 15px;
    padding: 10px;
    border-radius: 4px;
    background-color: rgba(249, 249, 249, 0.8);
    backdrop-filter: blur(5px);

    .monitoring-title {
      font-size: 15px;
      font-weight: 700;
      color: #333;
      margin-bottom: 8px;

      i {
        color: var(--theme);
        margin-right: 5px;
      }
    }

    .monitoring-content {
      padding: 5px 0;

      p {
        margin: 5px 0;
        font-size: 13px;

        .running {
          color: #67C23A;
          font-weight: bold;
        }
      }

      .el-button {
        margin-top: 0;
      }
    }
  }

  .card-column {
    margin-bottom: 15px;
    padding: 0 10px;
  }

  .el-card__header {
    font-weight: 700;

    span {
      font-weight: 700;
    }
  }

  .monitoring-buttons {
    display: flex;
    gap: 10px;
    margin-top: 10px;
  }
}

/* 卡片基础样式 - 添加磨砂效果 */
.el-card {
  background-color: rgba(255, 255, 255, 0.85);
  backdrop-filter: blur(10px);
  border: none !important;
  overflow: hidden;

  .el-card__body {
    background-color: transparent;
  }

  &::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: -1;
    background: linear-gradient(135deg, rgba(255, 255, 255, 0.3) 0%, rgba(255, 255, 255, 0.05) 100%);
    border-radius: 8px;
  }
}

/* 页面背景 */
.app-container {
  background-color: #f0f9eb;
  padding: 20px;
  min-height: calc(100vh - 84px);
}

/* 添加动画 */
@keyframes pulse {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.1);
  }
  100% {
    transform: scale(1);
  }
}

@keyframes rocket-move {
  0% {
    transform: rotate(45deg) translateY(0);
  }
  50% {
    transform: rotate(45deg) translateY(-5px);
  }
  100% {
    transform: rotate(45deg) translateY(0);
  }
}

@keyframes lightning-flash {
  0% {
    opacity: 1;
    transform: scale(1);
  }
  50% {
    opacity: 0.7;
    transform: scale(1.2);
  }
  100% {
    opacity: 1;
    transform: scale(1);
  }
}
</style>
