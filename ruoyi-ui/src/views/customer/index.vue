<template>
  <div class="app-container">
    <el-form :model="queryParams"
             ref="queryForm"
             size="small"
             :inline="true"
             v-show="showSearch"
             label-width="68px">
      <el-form-item label=""
                    prop="phone">
        <el-input v-model="queryParams.phone"
                  placeholder="请输入手机号或者姓名"
                  clearable
                  @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label=""
                    prop="name">
        <el-select v-model="queryParams.merchantName"
                   placeholder="请选择机构公司(可输入)"
                   clearable
                   filterable>
          <el-option v-for="item in MerchantList"
                     :key="item.id"
                     :label="item.merchantName"
                     :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label=""
                    prop="acturlName">
        <el-select v-model="queryParams.channelName"
                   placeholder="请选择渠道(可输入)"
                   clearable
                   filterable>
          <el-option v-for="item in merchantChannelList"
                     :key="item.id"
                     :label="item.channelName"
                     :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label=""
                    prop="time">
        <el-date-picker v-model="queryParams.time"
                        type="daterange"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label=""
                    prop="city">
        <el-input v-model="queryParams.city"
                  placeholder="请输入筛选城市"
                  clearable
                  @keyup.enter.native="handleQuery" />
      </el-form-item>

      <el-form-item>
        <el-button type="primary"
                   icon="el-icon-search"
                   size="mini"
                   @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh"
                   size="mini"
                   @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10"
            class="mb8">
      <!-- <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:customer:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:customer:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:customer:remove']"
          >删除</el-button
        >
      </el-col> -->
      <el-col :span="1.5">
        <el-button type="warning"
                   plain
                   icon="el-icon-download"
                   size="mini"
                   @click="handleExport">导出</el-button>
      </el-col>
      <!-- <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar> -->
    </el-row>

    <el-table v-loading="loading"
              :data="customerList"
              @selection-change="handleSelectionChange">
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <!-- <el-table-column label="${comment}" align="center" prop="id" /> -->
      <el-table-column label="姓名"
                       align="center"
                       prop="acturlName" />
      <el-table-column label="手机号"
                       align="center"
                       prop="phone" />
      <el-table-column label="分配机构"
                       align="center"
                       prop="socialSecurity">
        <template slot-scope="scope">
          {{findMerchant(scope.row.channelId)}}
        </template>
      </el-table-column>
      <el-table-column label="所属城市"
                       align="center"
                       prop="city" />
      <el-table-column label="所属渠道"
                       align="center"
                       prop="channelId">
        <template slot-scope="scope">
          {{findChannel(scope.row.channelId)}}
        </template>
      </el-table-column>
      <el-table-column label="推送时间"
                       align="center"
                       prop="updateTime"
                       width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="点击时间"
                       align="center"
                       prop="createTime"
                       width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作"
                       align="center"
                       class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini"
                     type="text"
                     @click="handleUpdate(scope.row)">详细资料</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0"
                :total="total"
                :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize"
                @pagination="getList" />

    <!-- 添加或修改客户信息对话框 -->
    <el-dialog title="用户详细资料"
               :visible.sync="open"
               width="40%"
               append-to-body>
      <el-row :gutter="10"
              style="padding: 20px 0; font-size: 14px">
        <el-col :span="12"> 姓名:{{ form.acturlName }} </el-col>
        <el-col :span="8"> 性别:{{ form.sex == 0 ? "男" : "女" }} </el-col>
      </el-row>
      <el-row :gutter="10"
              style="
          background-color: #e6e8f3;
          padding: 20px 0;
          text-align: center;
          font-size: 14px;
        "
              type="flex"
              justify="space-around">
        <!-- <el-col :span="4">
          <div>姓名</div>
          <div style="margin-top: 15px">程先生</div>
        </el-col>
        <el-col :span="4">
          <div>性别</div>
          <div style="margin-top: 15px">男</div>
        </el-col> -->
        <el-col :span="4">
          <div>所属城市</div>
          <div style="margin-top: 15px">{{ form.city }}</div>
        </el-col>
        <el-col :span="4">
          <div>年龄</div>
          <div style="margin-top: 15px">{{ form.age }}</div>
        </el-col>
        <el-col :span="4">
          <div>职业</div>
          <div style="margin-top: 15px">{{ form.career }}</div>
        </el-col>
      </el-row>
      <el-row :gutter="10"
              style="padding: 20px 0">
        <h3>用户资质</h3>
      </el-row>
      <el-row :gutter="10"
              style="
          background-color: #e6e8f3;
          padding: 20px 0;
          text-align: center;
          font-size: 14px;
        "
              type="flex"
              justify="space-around">
        <el-col :span="4">
          <div style="color: #9c9c9c">社保</div>
          <div style="margin-top: 15px">
            {{ shebao[this.form.socialSecurity] }}
          </div>
        </el-col>
        <el-col :span="4">
          <div style="color: #9c9c9c">公积金</div>
          <div style="margin-top: 15px">
            {{ shebao[this.form.accumulationFund] }}
          </div>
        </el-col>
        <el-col :span="4">
          <div style="color: #9c9c9c">房产情况</div>
          <div style="margin-top: 15px">{{ fangchan[this.form.hourse] }}</div>
        </el-col>
        <el-col :span="4">
          <div style="color: #9c9c9c">车辆情况</div>
          <div style="margin-top: 15px">{{ chechan[form.car] }}</div>
        </el-col>
        <el-col :span="4">
          <div style="color: #9c9c9c">学历</div>
          <div style="margin-top: 15px">{{ xueli[form.education] }}</div>
        </el-col>
      </el-row>
      <el-row :gutter="10"
              style="
          background-color: #e6e8f3;
          padding: 20px 0;
          text-align: center;
          font-size: 14px;
        "
              type="flex"
              justify="space-around">
        <el-col :span="4">
          <div style="color: #9c9c9c">芝麻分</div>
          <div style="margin-top: 15px">{{ form.zhiMa || 0}}分</div>
        </el-col>
        <el-col :span="4">
          <div style="color: #9c9c9c">保单</div>
          <div style="margin-top: 15px">{{baodan[this.form.guaranteeSlip]}}</div>
        </el-col>
        <el-col :span="4">
          <div style="color: #9c9c9c">月收入</div>
          <div style="margin-top: 15px">{{this.form.income || 0}}</div>
        </el-col>
        <el-col :span="4">
          <div style="color: #9c9c9c">京东白条可用额度</div>
          <div style="margin-top: 15px">{{ edu[this.form.baiTiao] }}</div>
        </el-col>
        <el-col :span="4">
          <div style="color: #9c9c9c">蚂蚁花呗可用额度</div>
          <div style="margin-top: 15px">{{ edu[this.form.huaBei] }}</div>
        </el-col>
      </el-row>
      <!-- <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div> -->
    </el-dialog>
  </div>
</template>

<script>
import {
  listCustomer,
  getCustomer,
  delCustomer,
  addCustomer,
  updateCustomer,
  getAllMerchantList,
  getMerchantChannelList
} from "@/api/system/customer";

export default {
  name: "Customer",
  data () {
    return {
      options: [
        { name: "是", code: 1 },
        { name: "否", code: 0 },
      ],
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
      // 客户信息表格数据
      customerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        time: [],
      },
      // 表单参数
      form: {},
      shebao: ['未缴纳', "缴纳未满6个月", "缴纳6个月以上"],
      chechan: ['无车产', '有车产'],
      fangchan: ["本地无房", "本地全款房", "本地按揭房"],
      baodan: ["无保单", '缴纳不足一年', "缴纳1年以上", "缴纳2年以上"],
      zhiye: ["上班族", "公务员/事业单位", "私营业主", "个体户", "其他职业"],
      xueli: ["初中及以下", "高中", "中专", "大专", "本科", "研究生及以上"],
      zhimafen: ['无芝麻分', '600分以下', '600-650分', '650-700分', "700分以上"],
      edu: ["5000以下", "5000-10000", "大于10000"],
      MerchantList: [],
      merchantChannelList: []
    };
  },
  created () {
    this.getMerchantList()
    this.getMerchantChannel()
    this.getList();
  },
  methods: {
    findChannel (id) {
      let str = '-'
      let index = this.merchantChannelList.findIndex(item => item.id == id)
      if (index != -1) {
        str = this.merchantChannelList[index].channelName
      }
      return str
    },
    findMerchant (id) {
      let str = '-'
      let index = this.MerchantList.findIndex(item => item.id == id)
      if (index != -1) {
        str = this.MerchantList[index].merchantName
      }
      return str
    },
    //获取渠道
    getMerchantChannel () {
      getMerchantChannelList().then(res => {
        this.merchantChannelList = res.data
      })
    },
    //获取机构 商户
    getMerchantList () {
      getAllMerchantList().then(res => {
        this.MerchantList = res.data
      })
    },
    getList () {
      this.loading = true;
      listCustomer(this.queryParams).then((response) => {
        this.customerList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel () {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset () {
      this.form = {};
    },
    /** 搜索按钮操作 */
    handleQuery () {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery () {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange (selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd () {
      this.reset();
      this.open = true;
      this.title = "用户详细资料";
    },
    /** 修改按钮操作 */
    handleUpdate (row) {
      this.reset();
      const id = row.id || this.ids;
      getCustomer(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "用户详细资料";
      });
    },
    /** 提交按钮 */
    submitForm () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateCustomer(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCustomer(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete (row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除客户信息编号为"' + ids + '"的数据项？')
        .then(function () {
          return delCustomer(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport () {
      this.download(
        "system/customer/export",
        {
          ...this.queryParams,
        },
        `customer_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
