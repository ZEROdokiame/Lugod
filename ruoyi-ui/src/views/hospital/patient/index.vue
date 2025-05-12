<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="患者姓名" prop="patientName">
        <el-input
          v-model="queryParams.patientName"
          placeholder="请输入患者姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="身份证号" prop="idCard">
        <el-input
          v-model="queryParams.idCard"
          placeholder="请输入身份证号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系电话" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入联系电话"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="就诊状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="就诊状态" clearable>
          <el-option
            v-for="dict in dict.type.hospital_patient_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="20" class="mb20">
      <el-col :span="24" :lg="6">
        <el-card shadow="hover" class="statistic-card">
          <div slot="header" class="clearfix">
            <span>患者状态统计</span>
          </div>
          <div class="status-stats">
            <el-row :gutter="10">
              <el-col :span="8" v-for="(item, index) in statusStats" :key="index">
                <div :class="['status-item', 'status-color-' + index]">
                  <div class="status-number">{{ item.count || 0 }}</div>
                  <div class="status-label">{{ item.label }}</div>
                </div>
              </el-col>
            </el-row>
          </div>
        </el-card>
      </el-col>
      <el-col :span="24" :lg="18">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['hospital:patient:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['hospital:patient:edit']"
            >修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['hospital:patient:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['hospital:patient:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="patientList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="患者ID" align="center" prop="patientId" v-if="false" />
          <el-table-column label="患者姓名" align="center" prop="patientName" width="120" />
          <el-table-column label="性别" align="center" prop="gender" width="80">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.gender"/>
            </template>
          </el-table-column>
          <el-table-column label="年龄" align="center" prop="age" width="80" />
          <el-table-column label="身份证号" align="center" prop="idCard" width="180" />
          <el-table-column label="联系电话" align="center" prop="phone" width="120" />
          <el-table-column label="地址" align="center" prop="address" :show-overflow-tooltip="true" />
          <el-table-column label="就诊状态" align="center" prop="status" width="100">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.hospital_patient_status" :value="scope.row.status"/>
            </template>
          </el-table-column>
          <el-table-column label="排队号码" align="center" prop="queueNumber" width="100" />
          <el-table-column label="叫号时间" align="center" prop="callTime" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.callTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['hospital:patient:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['hospital:patient:remove']"
              >删除</el-button>
              <el-dropdown @command="(command) => handleCommand(command, scope.row)" v-if="scope.row.status !== '4'">
                <span class="el-dropdown-link">
                  更多<i class="el-icon-arrow-down el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="triage" v-if="scope.row.status === '0'">分诊</el-dropdown-item>
                  <el-dropdown-item command="call" v-if="scope.row.status === '1'">叫号</el-dropdown-item>
                  <el-dropdown-item command="finish" v-if="scope.row.status === '2'">完成就诊</el-dropdown-item>
                  <el-dropdown-item command="cancel">取消就诊</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
        </el-table>
        
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>

    <!-- 添加或修改患者对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="患者姓名" prop="patientName">
              <el-input v-model="form.patientName" placeholder="请输入患者姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="form.gender" placeholder="请选择性别">
                <el-option
                  v-for="dict in dict.type.sys_user_sex"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="年龄" prop="age">
              <el-input-number v-model="form.age" :min="0" :max="120" controls-position="right" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="身份证号" prop="idCard">
              <el-input v-model="form.idCard" placeholder="请输入身份证号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="就诊状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择就诊状态">
                <el-option
                  v-for="dict in dict.type.hospital_patient_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" type="textarea" placeholder="请输入地址" />
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="紧急联系人" prop="emergencyContact">
              <el-input v-model="form.emergencyContact" placeholder="请输入紧急联系人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="紧急电话" prop="emergencyPhone">
              <el-input v-model="form.emergencyPhone" placeholder="请输入紧急联系电话" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 分诊对话框 -->
    <el-dialog title="患者分诊" :visible.sync="triageOpen" width="500px" append-to-body :close-on-click-modal="false">
      <el-form ref="triageForm" :model="triageForm" :rules="triageRules" label-width="100px">
        <el-form-item label="患者" prop="patientName">
          <el-input v-model="triageForm.patientName" disabled />
        </el-form-item>
        <el-form-item label="科室" prop="deptId">
          <el-select v-model="triageForm.deptId" placeholder="请选择科室">
            <el-option
              v-for="dept in deptOptions"
              :key="dept.deptId"
              :label="dept.deptName"
              :value="dept.deptId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="医生" prop="doctorId">
          <el-select v-model="triageForm.doctorId" placeholder="请选择医生">
            <el-option
              v-for="doctor in doctorOptions"
              :key="doctor.userId"
              :label="doctor.userName"
              :value="doctor.userId"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitTriageForm">确 定</el-button>
        <el-button @click="triageOpen = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPatient, getPatient, delPatient, addPatient, updatePatient, exportPatient, getPatientStatusStats } from "@/api/hospital/patient";

export default {
  name: "Patient",
  dicts: ['sys_user_sex', 'hospital_patient_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 患者表格数据
      patientList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 分诊对话框
      triageOpen: false,
      // 状态统计
      statusStats: [
        { label: '未就诊', count: 0 },
        { label: '已分诊', count: 0 },
        { label: '就诊中', count: 0 },
        { label: '已就诊', count: 0 },
        { label: '已取消', count: 0 }
      ],
      // 部门选项
      deptOptions: [],
      // 医生选项
      doctorOptions: [],
      // 分诊表单
      triageForm: {
        patientId: null,
        patientName: '',
        deptId: null,
        doctorId: null
      },
      // 分诊表单验证
      triageRules: {
        deptId: [
          { required: true, message: "请选择科室", trigger: "change" }
        ],
        doctorId: [
          { required: true, message: "请选择医生", trigger: "change" }
        ]
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        patientName: null,
        idCard: null,
        phone: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        patientName: [
          { required: true, message: "患者姓名不能为空", trigger: "blur" }
        ],
        gender: [
          { required: true, message: "性别不能为空", trigger: "change" }
        ],
        age: [
          { required: true, message: "年龄不能为空", trigger: "blur" }
        ],
        phone: [
          { required: true, message: "联系电话不能为空", trigger: "blur" },
          { pattern: /^1[3-9]\d{9}$/, message: "请输入正确的手机号码", trigger: "blur" }
        ],
        idCard: [
          { pattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/, message: "请输入正确的身份证号码", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getStatusStats();
  },
  methods: {
    /** 查询患者列表 */
    getList() {
      this.loading = true;
      listPatient(this.queryParams).then(response => {
        this.patientList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 获取状态统计 */
    getStatusStats() {
      getPatientStatusStats().then(response => {
        if (response.data) {
          this.statusStats = [
            { label: '未就诊', count: response.data['0'] || 0 },
            { label: '已分诊', count: response.data['1'] || 0 },
            { label: '就诊中', count: response.data['2'] || 0 },
            { label: '已就诊', count: response.data['3'] || 0 },
            { label: '已取消', count: response.data['4'] || 0 }
          ];
        }
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        patientId: null,
        patientName: null,
        gender: "0",
        age: null,
        idCard: null,
        phone: null,
        address: null,
        emergencyContact: null,
        emergencyPhone: null,
        status: "0",
        queueNumber: null,
        callTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加患者";
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.patientId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const patientId = row.patientId || this.ids[0];
      getPatient(patientId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改患者";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.patientId != null) {
            updatePatient(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.getStatusStats();
            });
          } else {
            addPatient(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.getStatusStats();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const patientIds = row.patientId || this.ids;
      this.$modal.confirm('是否确认删除患者编号为"' + patientIds + '"的数据项？').then(function() {
        return delPatient(patientIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
        this.getStatusStats();
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.$modal.confirm('是否确认导出所有患者数据项？').then(() => {
        this.exportLoading = true;
        return exportPatient(this.queryParams);
      }).then(response => {
        this.$download.excel(response, '患者数据');
        this.exportLoading = false;
      }).catch(() => {});
    },
    /** 处理分诊、叫号等操作 */
    handleCommand(command, row) {
      if (command === 'triage') {
        this.triageForm = {
          patientId: row.patientId,
          patientName: row.patientName,
          deptId: null,
          doctorId: null
        };
        this.triageOpen = true;
        // 获取科室和医生列表（模拟数据，实际应从后端获取）
        this.deptOptions = [
          { deptId: 1, deptName: '内科' },
          { deptId: 2, deptName: '外科' },
          { deptId: 3, deptName: '儿科' },
          { deptId: 4, deptName: '妇科' },
          { deptId: 5, deptName: '眼科' }
        ];
        
        this.doctorOptions = [
          { userId: 1, userName: '张医生' },
          { userId: 2, userName: '李医生' },
          { userId: 3, userName: '王医生' }
        ];
      } else if (command === 'call') {
        this.$modal.confirm('确认叫号患者 "' + row.patientName + '" 吗？').then(() => {
          const data = {
            patientId: row.patientId,
            status: '2',  // 就诊中
            callTime: new Date()
          };
          updatePatient(data).then(() => {
            this.$modal.msgSuccess("叫号成功");
            this.getList();
            this.getStatusStats();
          });
        });
      } else if (command === 'finish') {
        this.$modal.confirm('确认完成患者 "' + row.patientName + '" 的就诊吗？').then(() => {
          const data = {
            patientId: row.patientId,
            status: '3'  // 已就诊
          };
          updatePatient(data).then(() => {
            this.$modal.msgSuccess("操作成功");
            this.getList();
            this.getStatusStats();
          });
        });
      } else if (command === 'cancel') {
        this.$modal.confirm('确认取消患者 "' + row.patientName + '" 的就诊吗？').then(() => {
          const data = {
            patientId: row.patientId,
            status: '4'  // 已取消
          };
          updatePatient(data).then(() => {
            this.$modal.msgSuccess("操作成功");
            this.getList();
            this.getStatusStats();
          });
        });
      }
    },
    /** 提交分诊表单 */
    submitTriageForm() {
      this.$refs["triageForm"].validate(valid => {
        if (valid) {
          const data = {
            patientId: this.triageForm.patientId,
            status: '1',  // 已分诊
            deptId: this.triageForm.deptId,
            doctorId: this.triageForm.doctorId
          };
          updatePatient(data).then(() => {
            this.$modal.msgSuccess("分诊成功");
            this.triageOpen = false;
            this.getList();
            this.getStatusStats();
          });
        }
      });
    }
  }
};
</script>

<style scoped>
.statistic-card {
  margin-bottom: 20px;
  background-color: #f9f9f9;
}

.status-stats {
  padding: 10px 0;
}

.status-item {
  text-align: center;
  padding: 10px;
  margin-bottom: 10px;
  border-radius: 6px;
  background-color: #f5f7fa;
  transition: all 0.3s;
}

.status-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.09);
}

.status-number {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  line-height: 1.2;
}

.status-label {
  font-size: 13px;
  color: #666;
  margin-top: 5px;
}

.status-color-0 .status-number { color: #409EFF; }
.status-color-1 .status-number { color: #67C23A; }
.status-color-2 .status-number { color: #E6A23C; }
.status-color-3 .status-number { color: #409EFF; }
.status-color-4 .status-number { color: #909399; }

.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
  margin-left: 10px;
}

.mb20 {
  margin-bottom: 20px;
}
</style>
