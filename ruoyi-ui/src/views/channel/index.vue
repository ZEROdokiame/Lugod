<template>
  <div class="app-container">
    <el-form :model="queryParams"
             ref="queryForm"
             size="small"
             :inline="true"
             v-show="showSearch"
             label-width="68px">
      <el-form-item label="渠道"
                    prop="channelName">
        <el-input v-model="queryParams.channelName"
                  placeholder="请输入内容"
                  clearable
                  @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="渠道签名"
                    prop="channelSign">
        <el-input v-model="queryParams.channelSign"
                  placeholder="请输入渠道签名"
                  clearable
                  @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="渠道备注"
                    prop="remark">
        <el-input v-model="queryParams.remark"
                  placeholder="请输入渠道备注"
                  clearable
                  @keyup.enter.native="handleQuery" />
      </el-form-item>
      <!-- <el-form-item label="扣量比" prop="score">
        <el-input
          v-model="queryParams.score"
          placeholder="请输入扣量比"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="推广页名称" prop="htmlName">
        <el-input
          v-model="queryParams.htmlName"
          placeholder="请输入推广页名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="推广页地址" prop="htmlLocation">
        <el-input
          v-model="queryParams.htmlLocation"
          placeholder="请输入推广页地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="可访问IP" prop="ips">
        <el-input
          v-model="queryParams.ips"
          placeholder="请输入可访问IP"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开启关闭时段" prop="period">
        <el-input
          v-model="queryParams.period"
          placeholder="请输入开启关闭时段"
          clearable
          @keyup.enter.native="handleQuery"
        /> 
      </el-form-item>-->
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
      <el-col :span="1.5">
        <el-button type="primary"
                   plain
                   icon="el-icon-plus"
                   size="mini"
                   @click="handleAdd">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success"
                   plain
                   icon="el-icon-edit"
                   size="mini"
                   :disabled="single"
                   @click="handleUpdate">修改</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:channel:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:channel:export']"
          >导出</el-button
        >
      </el-col> -->
      <!-- <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar> -->
    </el-row>

    <el-table v-loading="loading"
              :data="channelList"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection"
                       width="55"
                       align="center" />
      <el-table-column label="ID"
                       align="center"
                       prop="id" />
      <el-table-column label="渠道名称"
                       align="center"
                       prop="channelName" />
      <el-table-column label="渠道签名"
                       align="center"
                       prop="channelSign" />
      <!-- <el-table-column label="扣量比" align="center" prop="score" /> -->
      <!-- <el-table-column label="推广页名称" align="center" prop="htmlName" /> -->
      <el-table-column label="推广地址"
                       align="center"
                       prop="htmlLocation" />
      <!-- <el-table-column label="可访问IP" align="center" prop="ips" /> -->
      <!-- <el-table-column label="开启关闭时段" align="center" prop="period" /> -->
      <el-table-column label="备注"
                       align="center"
                       prop="remark" />
      <el-table-column label="修改时间"
                       align="center"
                       prop="updateTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作"
                       align="center"
                       class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini"
                     type="text"
                     icon="el-icon-edit"
                     @click="handleUpdate(scope.row)">修改</el-button>
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:channel:remove']"
            >删除</el-button
          > -->
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0"
                :total="total"
                :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize"
                @pagination="getList" />

    <!-- 添加或修改渠道配置对话框 -->
    <el-dialog :title="title"
               :visible.sync="open"
               width="70"
               append-to-body>
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="120px">
        <el-form-item label="渠道类型"
                      prop="channelType"
                      :rules="[{ required: true, message: '必填项', trigger: 'change' }]">
          <el-radio-group v-model="form.channelType">
            <el-radio label="1">H5</el-radio>
            <el-radio label="2">联登</el-radio>
            <el-radio label="3">半流程</el-radio>
            <el-radio label="4">全流程</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="渠道名称"
                      prop="channelName"
                      :rules="[{ required: true, message: '必填项', trigger: 'change' }]">
          <el-input v-model="form.channelName"
                    placeholder="请输入渠道名称" />
        </el-form-item>
        <el-form-item label="渠道签名"
                      prop="channelSign"
                      :rules="[{ required: true, message: '必填项', trigger: 'change' }]">
          <el-input v-model="form.channelSign"
                    placeholder="请输入渠道签名"
                    disabled />
        </el-form-item>
        <el-form-item label="扣量比(%)"
                      prop="score"
                      :max="100"
                      :min="0"
                      :rules="[{ required: true, message: '必填项', trigger: 'change' }]">
          <el-input v-model="form.score"
                    placeholder="请输入扣量比"
                    type="number">
            <template slot="append">%</template>
          </el-input>
        </el-form-item>
        <el-form-item label="推广页名称"
                      prop="htmlName"
                      :rules="[{ required: true, message: '必填项', trigger: 'change' }]">
          <el-input v-model="form.htmlName"
                    placeholder="请输入推广页名称" />
        </el-form-item>
        <!-- <el-form-item label="推广页地址" prop="htmlLocation">
          <el-input
            v-model="form.htmlLocation"
            placeholder="请输入推广页地址"
          />
        </el-form-item> -->
        <el-form-item label="注册页模板"
                      prop="htmlLocation"
                      :rules="[{ required: true, message: '必填项', trigger: 'change' }]">
          <el-upload :action="action"
                     :headers="uploadHeader"
                     class="avatar-uploader"
                     :show-file-list="false"
                     :on-success="handleAvatarSuccess">
            <el-button size="small"
                       type="primary">点击上传</el-button>
            <el-input v-model="form.htmlLocation"
                      readonly
                      style="margin-top: 20px" />
          </el-upload>
        </el-form-item>
        <el-form-item label="可访问IP"
                      prop="ips"
                      :rules="[{ required: true, message: '必填项', trigger: 'change' }]">
          <el-input v-model="form.ips"
                    placeholder="请输入可访问IP" />
        </el-form-item>
        <el-form-item label="关闭时段"
                      prop="period">
          <!-- <el-input v-model="form.period" placeholder="请输入开启关闭时段" /> -->
          <el-checkbox :indeterminate="isIndeterminate"
                       @change="handleCheckAllChange"
                       v-model="checkAll">全选时段</el-checkbox>
          <div style="margin: 15px 0"></div>
          <el-checkbox-group v-model="form.period"
                             @change="handleCheckedCitiesChange">
            <el-checkbox v-for="city in cityOptions"
                         :label="city"
                         :key="city">{{ city }}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="备注"
                      prop="remark">
          <el-input v-model="form.remark"
                    placeholder="请输入"
                    type="textarea"
                    :rows="2" />
        </el-form-item>
      </el-form>
      <div slot="footer"
           class="dialog-footer">
        <el-button type="primary"
                   @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listChannel,
  getChannel,
  delChannel,
  addChannel,
  updateChannel,
} from "@/api/system/channel";
import { getToken } from "@/utils/auth";

export default {
  name: "Channel",
  data () {
    return {
      action: "/dev-api/file/upload",

      uploadHeader: { Authorization: getToken() },
      //关闭时间是否全选
      checkAll: false,
      checkedCities: [],
      cityOptions: [
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "11",
        "12",
        "13",
        "14",
        "15",
        "16",
        "17",
        "18",
        "19",
        "20",
        "21",
        "22",
        "23",
      ],
      isIndeterminate: false,
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
      // 渠道配置表格数据
      channelList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        channelName: null,
        channelSign: null,
        score: null,
        htmlName: null,
        htmlLocation: null,
        ips: null,
        period: null,
      },
      options: [], //推广页模板
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created () {
    this.getList();
  },
  methods: {
    // 上传html
    handleAvatarSuccess (res, file) {
      console.log(res.data.name, file);
      // this.form.htmlLocation = URL.createObjectURL(file.raw);
      this.$set(this.form, "htmlLocation", res.data.url);
    },
    //随机字符串
    randomString (len, an) {
      an = an && an.toLowerCase();
      var str = "",
        i = 0,
        min = an == "a" ? 10 : 0,
        max = an == "n" ? 10 : 62;
      for (; i++ < len;) {
        var r = (Math.random() * (max - min) + min) << 0;
        str += String.fromCharCode((r += r > 9 ? (r < 36 ? 55 : 61) : 48));
      }
      return str;
    },
    // 关闭时间是否全选
    handleCheckAllChange (val) {
      this.form.period = val ? this.cityOptions : [];
      this.isIndeterminate = false;
    },
    handleCheckedCitiesChange (value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.cityOptions.length;
      this.isIndeterminate =
        checkedCount > 0 && checkedCount < this.cityOptions.length;
    },
    /** 查询渠道配置列表 */
    getList () {
      this.loading = true;
      listChannel(this.queryParams).then((response) => {
        this.channelList = response.rows;
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
      this.form = {
        id: null,
        channelName: null,
        channelSign: null,
        score: null,
        htmlName: null,
        htmlLocation: null,
        ips: null,
        period: [],
        createTime: null,
        updateTime: null,
        remark: null,
      };
      this.resetForm("form");
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
      this.form.channelSign = this.randomString(16);
      this.open = true;
      this.title = "添加渠道配置";
    },
    /** 修改按钮操作 */
    handleUpdate (row) {
      this.reset();
      const id = row.id || this.ids;
      getChannel(id).then((response) => {
        this.form = response.data;
        this.form.period = this.form.period.split(",");
        //全选时段状态
        let checkedCount = this.form.period.length;
        this.checkAll = checkedCount === this.cityOptions.length;
        this.isIndeterminate =
          checkedCount > 0 && checkedCount < this.cityOptions.length;
        this.open = true;
        this.title = "修改渠道配置";
      });
    },
    /** 提交按钮 */
    submitForm () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            let params = { ...this.form };
            if (params.period) {
              params.period = params.period.join(",");
            }
            updateChannel(params).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            let params = { ...this.form };
            if (params.period) {
              params.period = params.period.join(",");
            }
            addChannel(params).then((response) => {
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
        .confirm('是否确认删除渠道配置编号为"' + ids + '"的数据项？')
        .then(function () {
          return delChannel(ids);
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
        "system/channel/export",
        {
          ...this.queryParams,
        },
        `channel_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
