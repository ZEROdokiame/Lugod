<template>
  <div class="app-container">
    <el-form :model="queryParams"
             ref="queryForm"
             size="small"
             :inline="true"
             v-show="showSearch"
             label-width="120px">
      <el-form-item label="上下架"
                    prop="status">
        <el-select v-model="queryParams.status"
                   style="width: 100%"
                   placeholder="是否选择上下架">
          <el-option v-for="item in options"
                     :key="item.code"
                     :label="item.name"
                     :value="item.code"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="商户名称"
                    prop="merchantName">
        <el-input v-model="queryParams.merchantName"
                  placeholder="请输入商户名称"
                  clearable
                  @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="商户形式"
                    prop="merchantType">
        <el-select v-model="queryParams.merchantType"
                   style="width: 100%"
                   placeholder="请选择">
          <el-option v-for="item in typeoptions"
                     :key="item.code"
                     :label="item.name"
                     :value="item.code"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="商户备注"
                    prop="remark">
        <el-input v-model="queryParams.remark"
                  placeholder="请输入商户备注"
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
      <el-col :span="1.5">
        <el-button type="warning"
                   plain
                   icon="el-icon-s-marketing"
                   size="mini"
                   :disabled="multiple"
                   @click="handleDelete">批量上下架</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="info"
                   plain
                   icon="el-icon-set-up"
                   size="mini"
                   :disabled="multiple"
                   @click="handleDelete">批量修改配置</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:merchant:export']"
          >导出</el-button
        >
      </el-col> -->
      <!-- <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar> -->
    </el-row>

    <el-table v-loading="loading"
              :data="merchantList"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection"
                       width="55"
                       align="center" />
      <el-table-column label="ID"
                       align="center"
                       prop="id" />
      <el-table-column label="商户名称"
                       align="center"
                       prop="merchantName" />
      <el-table-column label="LOGO"
                       align="center">
        <template slot-scope="scope">
          <el-image style="width: 100px; height: 100px"
                    :src="scope.row.logo"
                    fit="fill"></el-image>
        </template>
      </el-table-column>
      <el-table-column label="商户链接(双击复制)"
                       align="center"
                       prop="merchantCompany">
        <template slot-scope="scope">
          <p @dblclick="copyText(scope.row.merchantCompany)">{{ scope.row.merchantCompany }}</p>
        </template>
      </el-table-column>
      <el-table-column label="是否上下架"
                       align="center"
                       prop="status">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status == '1' ? 'success' : 'danger'"
                  disable-transitions>{{ scope.row.status == '1' ? '上架' : '下架'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="商户形式"
                       align="center"
                       prop="merchantType">
        <template slot-scope="scope">
          {{ typeoptions[scope.row.merchantType - 1].name}}
        </template>
      </el-table-column>
      <el-table-column label="余额"
                       align="center"
                       prop="score" />
      <el-table-column label="商户备注"
                       align="center"
                       prop="remark" />
      <el-table-column label="商户唯一编号"
                       align="center"
                       prop="id" />
      <el-table-column label="申请限制数"
                       align="center"
                       prop="applyLimit" />
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
          <el-button size="small"
                     type="danger">下架</el-button>
          <el-button size="small"
                     type="primary"
                     @click="handleUpdate(scope.row)">修改</el-button>
          <el-button size="small"
                     type="warning">充值</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0"
                :total="total"
                :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize"
                @pagination="getList" />

    <!-- 添加或修改商户对话框 -->
    <el-dialog :title="title"
               :visible.sync="open"
               width="70"
               append-to-body>
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="120px">
        <el-form-item label="商户形式"
                      prop="merchantType">
          <el-radio-group v-model="form.merchantType">
            <el-radio :label="1">H5</el-radio>
            <el-radio :label="2">联登</el-radio>
            <el-radio :label="3">半流程</el-radio>
            <el-radio :label="4">全流程</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="商户名称"
                      prop="merchantName">
          <el-input v-model="form.merchantName"
                    placeholder="请输入商户名称" />
        </el-form-item>
        <el-form-item label="商户描述"
                      prop="merchantDescribe">
          <el-input v-model="form.merchantDescribe"
                    placeholder="请输入商户描述" />
        </el-form-item>
        <el-form-item label="日利率"
                      prop="merchantCompany">
          <el-input v-model="form.merchantCompany"
                    type="number" />
        </el-form-item>
        <el-form-item label="标签"
                      prop="label">
          <el-input v-model="form.label"
                    maxlength="8"
                    show-word-limit />
        </el-form-item>
        <el-form-item label="机构主体"
                      prop="limitNum">
          <el-input v-model="form.limitNum"
                    maxlength="50"
                    show-word-limit />
        </el-form-item>
        <el-form-item label="关联商户"
                      prop="merchantName">
          <el-select v-model="form.merchantName"
                     style="width: 100%"
                     placeholder="是否选择关联商户">
            <el-option v-for="item in MerchantList"
                       :key="item.id"
                       :label="item.merchantName"
                       :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商户LOGO"
                      prop="logo">
          <el-upload :action="action"
                     :headers="uploadHeader"
                     class="avatar-uploader"
                     :show-file-list="false"
                     :on-success="handleAvatarSuccess"
                     :before-upload="beforeAvatarUpload">
            <img v-if="form.logo"
                 :src="form.logo"
                 class="avatar" />
            <i v-else
               class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="上下架"
                      prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :label="0">上架</el-radio>
            <el-radio :label="1">下架</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="是否开启余额监控"
                      prop="isBalanceMonitoring"
                      label-width="160px">
          <el-radio-group v-model="form.isBalanceMonitoring">
            <el-radio :label="1">开启</el-radio>
            <el-radio :label="2">不开启</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="注册页地址"
                      prop="registUrl">
          <el-input v-model="form.registUrl" />
        </el-form-item>

        <el-form-item label="撞库URL"
                      prop="hitUrl">
          <el-input v-model="form.hitUrl" />
        </el-form-item>

        <div class="title">
          <span>商户展示配置</span>
        </div>

        <el-form-item label="是否开启定量"
                      prop="limitType"
                      label-width="160px">
          <el-radio-group v-model="form.limitType">
            <el-radio :label="1">开启</el-radio>
            <el-radio :label="2">关闭</el-radio>
          </el-radio-group>
          <el-input style="margin-top: 20px"
                    v-if="form.limitType == 1"
                    v-model="form.balanceMonitoring"
                    placeholder="请输入定量" />
        </el-form-item>
        <el-form-item label="执行时段"
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
        <el-form-item label="渠道限定"
                      prop="channelLimitType">
          <el-radio-group v-model="form.channelLimitType">
            <el-radio :label="0">不限制</el-radio>
            <el-radio :label="1">准入渠道</el-radio>
            <el-radio :label="2">禁入渠道</el-radio>
          </el-radio-group>
          <el-select v-model="form.channelLimit"
                     placeholder="请选择渠道"
                     clearable
                     multiple
                     style="width:100%;margin-top:20px"
                     v-if="form.channelLimitType == 1 || form.channelLimitType == 2"
                     filterable>
            <el-option v-for="item in merchantChannelList"
                       :key="item.id"
                       :label="item.channelName"
                       :value="item.id + ''"></el-option>
          </el-select>
        </el-form-item>

        <div class="title"
             style="margin-top: 20px">
          <span>前筛配置</span>
        </div>

        <el-form-item label="年龄限制">
          <el-input v-model="form.ageLimitStart"
                    style="width: 200px" />
          -
          <el-input v-model="form.ageLimitEnd"
                    style="width: 200px" />
        </el-form-item>
        <el-form-item label="禁用手机号段(前三位)"
                      prop="phoneLimit"
                      label-width="120px">
          <el-input v-model="form.phoneLimit"
                    type="textarea"
                    :rows="2"
                    placeholder="请输入手机号段,多个号段以因为逗号隔开" />
        </el-form-item>
        <el-form-item label="空值是否通过"
                      prop="ispass">
          <el-radio-group v-model="form.ispass">
            <el-radio :label="1">通过</el-radio>
            <el-radio :label="0">不通过</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="资质条件筛选"
                      prop="customerInfoFilterType">
          <el-radio-group v-model="form.customerInfoFilterType">
            <el-radio :label="1">满足其一</el-radio>
            <el-radio :label="2">满足全部条件</el-radio>
            <el-radio :label="0">不筛选</el-radio></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="社保">
          <!-- <el-checkbox-group v-model="form.socialSecurity"
                             size="small">
            <el-checkbox border
                         v-for="city in shebao"
                         :label="city"
                         :key="city"
                         style="margin-right:10px">{{city}}</el-checkbox>
          </el-checkbox-group> -->
          <el-button @click="btnchanges('socialSecurityNo')"
                     :type="form.socialSecurityNo == 1 ?'primary':''">无社保</el-button>
          <el-button @click="btnchanges('socialSecurityLow')"
                     :type="form.socialSecurityLow == 1 ?'primary':''">社保未满6个月</el-button>
          <el-button @click="btnchanges('socialSecurityHigh')"
                     :type="form.socialSecurityHigh == 1 ?'primary':''">社保6个月以上</el-button>
        </el-form-item>
        <el-form-item label="公积金">
          <!-- <el-checkbox-group v-model="form.accumulationFund"
                             size="small">
            <el-checkbox border
                         v-for="city in shebao"
                         :label="city"
                         :key="city"
                         style="margin-right:10px">{{city}}</el-checkbox>
          </el-checkbox-group> -->
          <el-button @click="btnchanges('accumulationFundLow')"
                     :type="form.accumulationFundLow == 1 ?'primary':''">公积金未满6个月</el-button>
          <el-button @click="btnchanges('accumulationFundHigh')"
                     :type="form.accumulationFundHigh == 1 ?'primary':''">公积金满6个月以上</el-button>
        </el-form-item>
        <el-form-item label="车产">
          <!-- <el-checkbox-group v-model="form.car"
                             size="small">
            <el-checkbox border
                         v-for="city in chechan"
                         :label="city"
                         :key="city"
                         style="margin-right:10px">{{city}}</el-checkbox>
          </el-checkbox-group> -->
          <el-button @click="btnchanges('carNo')"
                     :type="form.carNo == 1 ?'primary':''">无车</el-button>
          <el-button @click="btnchanges('carHave')"
                     :type="form.carHave == 1 ?'primary':''">有车</el-button>
        </el-form-item>
        <el-form-item label="房产">
          <!-- <el-checkbox-group v-model="form.house"
                             size="small">
            <el-checkbox border
                         v-for="city in fangchan"
                         :label="city"
                         :key="city"
                         style="margin-right:10px">{{city}}</el-checkbox>
          </el-checkbox-group> -->
          <el-button @click="btnchanges('hourseNo')"
                     :type="form.hourseNo == 1 ?'primary':''">本地无房</el-button>
          <el-button @click="btnchanges('hourseFullPayment')"
                     :type="form.hourseFullPayment == 1 ?'primary':''">本地全款房</el-button>
          <el-button @click="btnchanges('hourseMortgaging')"
                     :type="form.hourseMortgaging == 1 ?'primary':''">本地按揭</el-button>
        </el-form-item>
        <el-form-item label="保单">
          <!-- <el-checkbox-group v-model="form.guaranteeSlip"
                             size="small">
            <el-checkbox border
                         v-for="city in baodan"
                         :label="city"
                         :key="city"
                         style="margin-right:10px">{{city}}</el-checkbox>
          </el-checkbox-group> -->
          <el-button @click="btnchanges('guaranteeSlipLow')"
                     :type="form.guaranteeSlipLow == 1 ?'primary':''">保单缴纳不满一年</el-button>
          <el-button @click="btnchanges('guaranteeSlipCentre')"
                     :type="form.guaranteeSlipCentre == 1 ?'primary':''">保单缴纳一年以上</el-button>
          <el-button @click="btnchanges('guaranteeSlipHigh')"
                     :type="form.guaranteeSlipHigh == 1 ?'primary':''">保单缴纳2年以上</el-button>
        </el-form-item>
        </el-form-item>
        <el-form-item label="职业">
          <!-- <el-checkbox-group v-model="form.worker"
                             size="small">
            <el-checkbox border
                         v-for="city in zhiye"
                         :label="city"
                         :key="city"
                         style="margin-right:10px">{{city}}</el-checkbox>
          </el-checkbox-group> -->
          <el-button @click="btnchanges('officeWorker')"
                     :type="form.officeWorker == 1 ?'primary':''">上班族</el-button>
          <el-button @click="btnchanges('civilServant')"
                     :type="form.civilServant == 1 ?'primary':''">公务员</el-button>
          <el-button @click="btnchanges('privatePropertyOwners')"
                     :type="form.privatePropertyOwners == 1 ?'primary':''">私营业主</el-button>
          <el-button @click="btnchanges('selfEmployedPerson')"
                     :type="form.selfEmployedPerson == 1 ?'primary':''">个体户</el-button>
          <el-button @click="btnchanges('otherOccupations')"
                     :type="form.otherOccupations == 1 ?'primary':''">其他职业</el-button>
        </el-form-item>
        <!-- <el-form-item label="学历"
                      prop="education">
          <el-checkbox-group v-model="form.education"
                             size="small">
            <el-checkbox border
                         v-for="city in xueli"
                         :label="city"
                         :key="city"
                         style="margin-right:10px">{{city}}</el-checkbox>
          </el-checkbox-group>
        </el-form-item> -->
        <el-form-item label="白条可用额度">
          <!-- <el-checkbox-group v-model="form.baiTiao"
                             size="small">
            <el-checkbox border
                         v-for="city in edu"
                         :label="city"
                         :key="city"
                         style="margin-right:10px">{{city}}</el-checkbox>
          </el-checkbox-group> -->
          <el-button @click="btnchanges('baiTiaoLow')"
                     :type="form.baiTiaoLow == 1 ?'primary':''">5000以下</el-button>
          <el-button @click="btnchanges('baiTiaoMiddle')"
                     :type="form.baiTiaoMiddle == 1 ?'primary':''">5000-10000</el-button>
          <el-button @click="btnchanges('baiTiaoHigh')"
                     :type="form.baiTiaoHigh == 1 ?'primary':''">10000以上</el-button>
        </el-form-item>
        <el-form-item label="花呗可用额度">
          <!-- <el-checkbox-group v-model="form.huaBei"
                             size="small">
            <el-checkbox border
                         v-for="city in edu"
                         :label="city"
                         :key="city"
                         style="margin-right:10px">{{city}}</el-checkbox>
          </el-checkbox-group> -->
          <el-button @click="btnchanges('huaBeiLow')"
                     :type="form.huaBeiLow == 1 ?'primary':''">5000以下</el-button>
          <el-button @click="btnchanges('huaBeiMiddle')"
                     :type="form.huaBeiMiddle == 1 ?'primary':''">5000-10000</el-button>
          <el-button @click="btnchanges('huaBeiHigh')"
                     :type="form.huaBeiHigh == 1 ?'primary':''">10000以上</el-button>
        </el-form-item>
        <el-form-item label="芝麻分>="
                      prop="zhiMa">
          <el-input v-model="form.zhiMa"
                    placeholder=""
                    style="width:200px" />
          分
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
  listMerchant,
  getMerchant,
  delMerchant,
  addMerchant,
  updateMerchant,
  getAllMerchantList
} from "@/api/system/merchant";
import {
  getMerchantChannelList
} from "@/api/system/customer";
import { getToken } from '@/utils/auth'

export default {
  name: "Merchant",
  data () {
    return {
      // action:process.env.NODE_ENV === "production" ? "/file/upload" : "/dev-api/file/upload",
      action: "/dev-api/file/upload",
      uploadHeader: { 'Authorization': getToken() },
      // shebao: ['缴纳未满6个月', '缴纳6个月以上'],
      // chechan: ['有车'],
      // fangchan: ['有按揭房', '有全款房'],
      // baodan: ['缴纳不足1年', '缴纳1年以上', '缴纳2年以上'],
      // zhiye: ['上班族', '公务员/事业耽误', '私营业主', '个体户', '其他职业'],
      // xueli: ['初中及以下', '高中', '中专', '大专', '本科', '研究生及以上'],
      // edu: ['5000以下', '5000-10000', '大于10000'],
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
      MerchantList: [],
      typeoptions: [
        { name: 'H5', code: '1' },
        { name: '联登', code: '2' },
        { name: '半流程', code: '3' },
        { name: '全流程', code: '4' },
      ],
      isIndeterminate: false,
      options: [
        { name: "否", code: 0 },
        { name: "是", code: 1 },
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
      // 商户表格数据
      merchantList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: { period: [] },
      merchantChannelList: [],
      // 表单校验
      rules: {
        merchantType: [
          { required: true, message: "请选择商户形式", trigger: "change" },
        ],
        merchantName: [
          { required: true, message: "请输入商户名称", trigger: "blur" },
        ],
        merchantCompany: [
          { required: true, message: "请输入日利率", trigger: "blur" },
        ],
        limitNum: [{ required: true, message: "机构主体不能为空", trigger: "blur" }],
        // logo: [{ required: true, message: "logo不能为空", trigger: "blur" }],
        status: [
          {
            required: true,
            message: "请选择上下架",
            trigger: "change",
          },
        ],
        registUrl: [
          {
            required: true,
            message: "注册页地址不能为空",
            trigger: "blur",
          },
        ],
        hitUrl: [
          {
            required: true,
            message: "撞库URL不能为空",
            trigger: "blur",
          },
        ],
        period: [
          { required: true, message: "请选择执行时段", trigger: "change" },
        ],
        channelLimitType: [
          { required: true, message: "请选择渠道限定", trigger: "change" },
        ],
        ispass: [
          { required: true, message: "请选择空值是否通过", trigger: "change" },
        ]
      },
    };
  },
  created () {
    this.getMerchantList()
    this.getMerchantChannel()
    this.getList();
  },
  methods: {
    //获取渠道
    getMerchantChannel () {
      getMerchantChannelList().then(res => {
        this.merchantChannelList = res.data
      })
    },
    btnchanges (key) {
      if (this.form[key] == 1) {
        this.form[key] = 0
      } else {
        this.form[key] = 1
      }
    },
    // MerchantList
    getMerchantList () {
      getAllMerchantList().then(res => {
        this.MerchantList = res.data
      })
    },
    async copyText (text) {
      try {
        // 阻止默认的双击选中文本行为
        event.preventDefault();
        // 使用Clipboard API复制文本到剪贴板
        await navigator.clipboard.writeText(text);
        // 可以在这里提示用户复制成功
        this.$modal.msgSuccess("文本已复制到剪贴板");
      } catch (error) {
        // 处理错误情况，例如用户拒绝剪贴板权限
        console.error('复制失败', error);
      }
    },
    // 时间是否全选
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
    // 上传图片
    handleAvatarSuccess (res, file) {
      console.log(res.data.name, file);
      // this.form.htmlLocation = URL.createObjectURL(file.raw);
      this.$set(this.form, "logo", res.data.url);
    },
    beforeAvatarUpload (file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
    /** 查询商户列表 */
    getList () {
      // this.loading = true;
      listMerchant(this.queryParams).then((response) => {
        this.merchantList = response.rows;
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
        period: [],
        id: null,
        logo: "",
        socialSecurityNo: 0,
        socialSecurityLow: 0,
        socialSecurityHight: 0,
        accumulationFundLow: 0,
        accumulationFundHight: 0,
        carNo: 0,
        carHave: 0,
        hourseNo: 0,
        hourseFullPayment: 0,
        hourseMortgaging: 0,
        guaranteeSlipLow: 0,
        guaranteeSlipCentre: 0,
        guaranteeSlipHigt: 0,
        officeWorker: 0,
        civilServant: 0,
        privatePropertyOwners: 0,
        selfEmployedPerson: 0,
        otherOccupations: 0,
        baiTiaoLow: 0,
        baiTiaoMiddle: 0,
        baiTiaoHigh: 0,
        huaBeiLow: 0,
        huaBeiMiddle: 0,
        huaBeiHigh: 0,
        zhiMa: null,
        channelLimit: []
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
      this.open = true;
      this.title = "添加商户";
    },
    /** 修改按钮操作 */
    handleUpdate (row) {
      this.reset();
      const id = row.id || this.ids;
      getMerchant(id).then((response) => {
        this.form = response.data;
        this.form.period = this.form.period ? this.form.period.split(",") : [];
        this.form.channelLimit = this.form.channelLimit ? this.form.channelLimit.split(",") : [];
        let checkedCount = this.form.period.length;
        this.checkAll = checkedCount === this.cityOptions.length;
        this.isIndeterminate =
          checkedCount > 0 && checkedCount < this.cityOptions.length;
        this.open = true;
        this.title = "修改商户";
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
            if (params.channelLimit) {
              params.channelLimit = params.channelLimit.join(",");
            }
            updateMerchant(params).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            let params = { ...this.form };
            if (params.period) {
              params.period = params.period.join(",");
            }
            if (params.channelLimit) {
              params.channelLimit = params.channelLimit.join(",");
            }
            addMerchant(params).then((response) => {
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
        .confirm('是否确认删除商户编号为"' + ids + '"的数据项？')
        .then(function () {
          return delMerchant(ids);
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
        "system/merchant/export",
        {
          ...this.queryParams,
        },
        `merchant_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>

<style scoped>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.title {
  width: 100%;
  background: #d7d7d7;
  margin: 20px 0;
  font-size: 16px;
  font-weight: 700;
  text-align: left;
}
</style>
