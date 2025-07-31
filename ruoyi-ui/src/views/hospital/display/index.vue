<template>
  <div class="display-container">
    <div class="header">
      <div class="hospital-name">医院叫号大屏幕</div>
      <div class="time">{{ currentTime }}</div>
    </div>

    <div class="content">
      <el-row :gutter="20">
        <el-col :span="12" v-for="dept in deptList" :key="dept.deptId">
          <div class="dept-panel">
            <div class="dept-header">
              <span class="dept-name">{{ dept.deptName }}</span>
              <span class="waiting-count">等待人数：{{ dept.waitingCount }}</span>
            </div>
            <div class="queue-list">
              <div class="current-number" v-if="dept.currentPatient">
                <div class="number-label">正在就诊</div>
                <div class="number">{{ dept.currentPatient.queueNumber }}</div>
                <div class="patient-name">{{ dept.currentPatient.patientName }}</div>
              </div>
              <div class="waiting-list">
                <div class="waiting-label">等待队列</div>
                <div class="waiting-numbers">
                  <div class="waiting-item" v-for="patient in dept.waitingList" :key="patient.patientId">
                    {{ patient.queueNumber }}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import { getDisplayInfo } from "@/api/hospital/display";

export default {
  name: "Display",
  data() {
    return {
      currentTime: '',
      deptList: [],
      timer: null
    };
  },
  created() {
    this.updateTime();
    this.timer = setInterval(() => {
      this.updateTime();
      this.getDisplayData();
    }, 1000);
    this.getDisplayData();
  },
  beforeDestroy() {
    if (this.timer) {
      clearInterval(this.timer);
    }
  },
  methods: {
    updateTime() {
      this.currentTime = new Date().toLocaleString();
    },
    getDisplayData() {
      getDisplayInfo().then(response => {
        this.deptList = response.data;
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.display-container {
  height: 100vh;
  background: #f0f2f5;
  padding: 20px;

  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 20px;
    background: #fff;
    margin-bottom: 20px;

    .hospital-name {
      font-size: 32px;
      font-weight: bold;
      color: #1890ff;
    }

    .time {
      font-size: 24px;
      color: #666;
    }
  }

  .dept-panel {
    background: #fff;
    padding: 20px;
    margin-bottom: 20px;
    border-radius: 4px;

    .dept-header {
      display: flex;
      justify-content: space-between;
      margin-bottom: 20px;

      .dept-name {
        font-size: 24px;
        font-weight: bold;
      }

      .waiting-count {
        font-size: 18px;
        color: #ff4d4f;
      }
    }

    .current-number {
      text-align: center;
      margin-bottom: 20px;

      .number-label {
        font-size: 18px;
        color: #666;
      }

      .number {
        font-size: 48px;
        font-weight: bold;
        color: #1890ff;
        margin: 10px 0;
      }

      .patient-name {
        font-size: 24px;
      }
    }

    .waiting-list {
      .waiting-label {
        font-size: 18px;
        color: #666;
        margin-bottom: 10px;
      }

      .waiting-numbers {
        display: flex;
        flex-wrap: wrap;
        gap: 10px;

        .waiting-item {
          padding: 10px 20px;
          background: #f5f5f5;
          border-radius: 4px;
          font-size: 18px;
        }
      }
    }
  }
}
</style>
