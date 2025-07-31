<template>
  <div class="doctor-workspace">
    <el-row :gutter="20">
      <!-- 左侧等待列表 -->
      <el-col :span="8">
        <el-card class="waiting-list">
          <div slot="header">
            <span>等待队列</span>
            <span class="waiting-count">（{{ waitingList.length }}人）</span>
          </div>
          <div v-for="patient in waitingList" :key="patient.patientId" class="patient-item">
            <div class="patient-info">
              <div class="queue-number">{{ patient.queueNumber }}</div>
              <div class="patient-detail">
                <div class="name">{{ patient.patientName }}</div>
                <div class="sub-info">{{ patient.gender === '0' ? '男' : '女' }} {{ patient.age }}岁</div>
              </div>
            </div>
            <el-button type="primary" size="mini" @click="handleCallPatient(patient)">叫号</el-button>
          </div>
          <div v-if="waitingList.length === 0" class="empty-text">
            暂无等待患者
          </div>
        </el-card>
      </el-col>

      <!-- 右侧就诊区 -->
      <el-col :span="16">
        <el-card class="treatment-area">
          <div slot="header">
            <span>当前就诊</span>
          </div>
          <div v-if="currentPatient" class="current-patient">
            <div class="patient-header">
              <div class="patient-base-info">
                <div class="name">{{ currentPatient.patientName }}</div>
                <div class="info-item">
                  <span>性别：{{ currentPatient.gender === '0' ? '男' : '女' }}</span>
                  <span>年龄：{{ currentPatient.age }}岁</span>
                </div>
                <div class="info-item">
                  <span>联系电话：{{ currentPatient.phone }}</span>
                </div>
              </div>
              <div class="operation-buttons">
                <el-button type="success" @click="handleComplete">完成就诊</el-button>
                <el-button type="danger" @click="handleCancel">取消就诊</el-button>
              </div>
            </div>
            <div class="divider"></div>
            <div class="medical-record">
              <!-- 在这里可以添加病历书写等医疗相关的组件 -->
            </div>
          </div>
          <div v-else class="empty-treatment">
            <div class="empty-text">请从左侧列表叫号开始就诊</div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getDoctorQueue, callPatient, completePatient, cancelPatient, getCurrentPatient } from "@/api/hospital/doctor";

export default {
  name: "DoctorWorkspace",
  data() {
    return {
      waitingList: [],
      currentPatient: null,
      timer: null
    };
  },
  created() {
    this.getWaitingList();
    this.getCurrentPatient();
    // 定时刷新等待列表和当前就诊患者
    this.timer = setInterval(() => {
      this.getWaitingList();
      this.getCurrentPatient();
    }, 5000);
  },
  beforeDestroy() {
    if (this.timer) {
      clearInterval(this.timer);
    }
  },
  methods: {
    /** 获取等待列表 */
    getWaitingList() {
      getDoctorQueue().then(response => {
        this.waitingList = response.data;
      });
    },
    /** 叫号 */
    handleCallPatient(patient) {
      this.$modal.confirm('是否叫号患者"' + patient.patientName + '"？').then(() => {
        callPatient(patient.patientId).then(response => {
          this.currentPatient = response.data;
          this.getWaitingList();
          this.$modal.msgSuccess("叫号成功");
        });
      });
    },
    /** 完成就诊 */
    handleComplete() {
      if (!this.currentPatient) return;
      this.$modal.confirm('是否完成当前患者就诊？').then(() => {
        completePatient(this.currentPatient.patientId).then(() => {
          this.currentPatient = null;
          this.getWaitingList();
          this.$modal.msgSuccess("已完成就诊");
        });
      });
    },
    /** 取消就诊 */
    handleCancel() {
      if (!this.currentPatient) return;
      this.$modal.confirm('是否取消当前患者就诊？').then(() => {
        cancelPatient(this.currentPatient.patientId).then(() => {
          this.currentPatient = null;
          this.getWaitingList();
          this.$modal.msgSuccess("已取消就诊");
        });
      });
    },
    /** 获取当前就诊患者 */
    getCurrentPatient() {
      getCurrentPatient().then(response => {
        this.currentPatient = response.data;
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.doctor-workspace {
  padding: 20px;

  .waiting-list {
    .patient-item {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 10px;
      border-bottom: 1px solid #eee;

      .patient-info {
        display: flex;
        align-items: center;

        .queue-number {
          font-size: 18px;
          font-weight: bold;
          color: #1890ff;
          margin-right: 15px;
        }

        .patient-detail {
          .name {
            font-size: 16px;
            margin-bottom: 5px;
          }
          .sub-info {
            font-size: 14px;
            color: #666;
          }
        }
      }
    }
  }

  .treatment-area {
    .current-patient {
      .patient-header {
        display: flex;
        justify-content: space-between;
        align-items: flex-start;
        margin-bottom: 20px;

        .patient-base-info {
          .name {
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 10px;
          }
          .info-item {
            margin-bottom: 5px;
            color: #666;
            span {
              margin-right: 20px;
            }
          }
        }
      }

      .divider {
        height: 1px;
        background: #eee;
        margin: 20px 0;
      }
    }
  }

  .empty-text {
    text-align: center;
    color: #999;
    padding: 30px 0;
  }

  .waiting-count {
    color: #ff4d4f;
    margin-left: 5px;
  }
}
</style>
