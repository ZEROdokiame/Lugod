<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="药材名称" prop="medicineName">
        <el-input
          v-model="queryParams.medicineName"
          placeholder="请输入药材名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="药材编码" prop="medicineCode">
        <el-input
          v-model="queryParams.medicineCode"
          placeholder="请输入药材编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商" prop="supplier">
        <el-input
          v-model="queryParams.supplier"
          placeholder="请输入供应商"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="验收状态" prop="acceptanceStatus">
        <el-select v-model="queryParams.acceptanceStatus" placeholder="请选择验收状态" clearable>
          <el-option
            v-for="dict in dict.type.acceptance_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="库存状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择库存状态" clearable>
          <el-option
            v-for="dict in dict.type.storage_status"
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['hospital:storage:add']"
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
          v-hasPermi="['hospital:storage:edit']"
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
          v-hasPermi="['hospital:storage:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['hospital:storage:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['hospital:storage:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="storageList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="药材图片" align="center" prop="imageUrl" width="100">
        <template slot-scope="scope">
          <el-image
            v-if="scope.row.imageUrl"
            :src="scope.row.imageUrl"
            :preview-src-list="[scope.row.imageUrl]"
            fit="cover"
            style="width: 60px; height: 60px; border-radius: 4px;"
          >
            <div slot="error" class="image-slot">
              <i class="el-icon-picture-outline"></i>
            </div>
          </el-image>
          <div v-else class="image-slot" style="width: 60px; height: 60px; display: flex; align-items: center; justify-content: center; border: 1px dashed #d9d9d9; border-radius: 4px;">
            <i class="el-icon-picture-outline" style="font-size: 20px; color: #c0c4cc;"></i>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="药材编码" align="center" prop="medicineCode" />
      <el-table-column label="药材名称" align="center" prop="medicineName" />
      <el-table-column label="规格" align="center" prop="specification" />
      <el-table-column label="当前库存" align="center" prop="quantity">
        <template slot-scope="scope">
          <span :class="scope.row.quantity <= scope.row.minStock ? 'text-danger' : ''">
            {{ scope.row.quantity }}{{ scope.row.unit || '' }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="最小库存" align="center" prop="minStock">
        <template slot-scope="scope">
          {{ scope.row.minStock }}{{ scope.row.unit || '' }}
        </template>
      </el-table-column>
      <el-table-column label="单价" align="center" prop="unitPrice">
        <template slot-scope="scope">
          ¥{{ scope.row.unitPrice }}
        </template>
      </el-table-column>
      <el-table-column label="供应商" align="center" prop="supplier" />
      <el-table-column label="批次号" align="center" prop="batchNumber" />
      <el-table-column label="验收状态" align="center" prop="acceptanceStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.acceptance_status" :value="scope.row.acceptanceStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="有效期" align="center" prop="expiryDate" width="120">
        <template slot-scope="scope">
          <span v-html="expiredFormat(scope.row)"></span>
        </template>
      </el-table-column>
      <el-table-column label="库存状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.storage_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <div style="display: flex; flex-direction: column; gap: 5px;">
            <!-- 验收按钮单独一行 -->
            <div v-if="scope.row.acceptanceStatus === 0">
              <el-button
                size="small"
                type="success"
                icon="el-icon-check"
                @click="handleAcceptance(scope.row)"
                v-hasPermi="['hospital:storage:edit']"
                style="width: 80px;"
              >验收</el-button>
            </div>
            <!-- 修改和删除同一行 -->
            <div style="display: flex; justify-content: center; gap: 5px;">
              <el-button
                size="small"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['hospital:storage:edit']"
                style="width: 60px;"
              >修改</el-button>
              <el-button
                size="small"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['hospital:storage:remove']"
                style="width: 60px;"
              >删除</el-button>
            </div>
          </div>
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

    <!-- 添加或修改药材库存管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="药材名称" prop="medicineName">
              <el-input v-model="form.medicineName" placeholder="请输入药材名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="药材编码" prop="medicineCode">
              <el-input v-model="form.medicineCode" placeholder="请输入药材编码" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="药材规格" prop="specification">
              <el-input v-model="form.specification" placeholder="请输入药材规格" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单位" prop="unit">
              <el-select v-model="form.unit" placeholder="请选择单位">
                <el-option label="盒" value="盒" />
                <el-option label="瓶" value="瓶" />
                <el-option label="袋" value="袋" />
                <el-option label="支" value="支" />
                <el-option label="粒" value="粒" />
                <el-option label="片" value="片" />
                <el-option label="克" value="克" />
                <el-option label="毫升" value="毫升" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="库存数量" prop="quantity">
              <el-input-number v-model="form.quantity" :min="0" placeholder="请输入当前库存数量" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最小库存" prop="minStock">
              <el-input-number v-model="form.minStock" :min="0" placeholder="请输入最小库存预警值" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="单价" prop="unitPrice">
              <el-input-number v-model="form.unitPrice" :precision="2" :min="0" placeholder="请输入单价" style="width: 100%" />
            </el-form-item>
          </el-col>
          <!-- 隐藏验收状态选项，只能通过操作列验收 -->
          <!--
          <el-col :span="12">
            <el-form-item label="验收状态" prop="acceptanceStatus">
              <el-radio-group v-model="form.acceptanceStatus">
                <el-radio
                  v-for="dict in dict.type.acceptance_status"
                  :key="dict.value"
                  :label="parseInt(dict.value)"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          -->
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="生产厂家" prop="manufacturer">
              <el-input v-model="form.manufacturer" placeholder="请输入生产厂家" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="供应商" prop="supplier">
              <el-input v-model="form.supplier" placeholder="请输入供应商" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="批次号" prop="batchNumber">
              <el-input v-model="form.batchNumber" placeholder="请输入批次号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="存储位置" prop="storageLocation">
              <el-input v-model="form.storageLocation" placeholder="请输入存储位置" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="生产日期" prop="productionDate">
              <el-date-picker
                clearable
                v-model="form.productionDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择生产日期"
                style="width: 100%">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="有效期" prop="expiryDate">
              <el-date-picker
                clearable
                v-model="form.expiryDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择有效期"
                style="width: 100%">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="form.acceptanceStatus === 1">
          <el-col :span="12">
            <el-form-item label="验收人" prop="acceptanceBy">
              <el-input v-model="form.acceptanceBy" placeholder="请输入验收人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="验收时间" prop="acceptanceTime">
              <el-date-picker
                clearable
                v-model="form.acceptanceTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择验收时间"
                style="width: 100%">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="药材图片" prop="imageUrl">
              <el-upload
                class="upload-demo"
                :action="uploadImgUrl"
                :on-success="handleImageSuccess"
                :on-error="handleImageError"
                :before-upload="beforeImageUpload"
                :show-file-list="false"
                :headers="headers"
                accept="image/*">
                <el-image
                  v-if="form.imageUrl"
                  :src="form.imageUrl"
                  fit="cover"
                  style="width: 120px; height: 120px; border-radius: 6px; cursor: pointer;"
                >
                  <div slot="error" class="image-slot">
                    <i class="el-icon-picture-outline"></i>
                  </div>
                </el-image>
                <div v-else class="upload-image-slot">
                  <i class="el-icon-plus"></i>
                  <div>点击上传</div>
                </div>
              </el-upload>
              <div class="upload-tip">支持 jpg、png格式，大小不超过 2MB</div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="库存状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.storage_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 药材库存导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的药材数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listStorage, getStorage, delStorage, addStorage, updateStorage } from "@/api/hospital/storage";
import { getToken } from "@/utils/auth";

export default {
  name: "Storage",
  dicts: ['storage_status', 'acceptance_status'],
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
      // 药材库存管理表格数据
      storageList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 上传文件服务器地址
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/file/upload",
      // 上传的图片服务器地址
      imgHost: process.env.VUE_APP_BASE_API,
      // 上传文件的请求头
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        medicineName: null,
        medicineCode: null,
        supplier: null,
        acceptanceStatus: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        medicineName: [
          { required: true, message: "药材名称不能为空", trigger: "blur" }
        ],
        medicineCode: [
          { required: true, message: "药材编码不能为空", trigger: "blur" }
        ],
        quantity: [
          { required: true, message: "库存数量不能为空", trigger: "blur" }
        ],
        unit: [
          { required: true, message: "单位不能为空", trigger: "blur" }
        ],
        unitPrice: [
          { required: true, message: "单价不能为空", trigger: "blur" }
        ],
        // 文件上传相关的规则
        file: [
          { required: true, message: "请上传药材库存数据文件", trigger: "change" }
        ]
      },
      // 文件上传相关的参数
      upload: {
        open: false,
        title: "导入药材库存",
        url: process.env.VUE_APP_BASE_API + "/hospital/storage/importData",
        headers: {
          Authorization: "Bearer " + getToken(),
        },
        isUploading: false,
        updateSupport: false, // 是否支持更新已存在数据
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询药材库存管理列表 */
    getList() {
      this.loading = true;
      listStorage(this.queryParams).then(response => {
        this.storageList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        id: null,
        medicineName: null,
        medicineCode: null,
        specification: null,
        quantity: null,
        minStock: null,
        unit: null,
        unitPrice: null,
        imageUrl: null,
        acceptanceStatus: 0,
        acceptanceBy: null,
        acceptanceTime: null,
        manufacturer: null,
        supplier: null,
        batchNumber: null,
        productionDate: null,
        expiryDate: null,
        storageLocation: null,
        status: 0,
        delFlag: null
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加药材";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getStorage(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改药材";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStorage(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStorage(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除药材编号为"' + ids + '"的数据项？').then(function() {
        return delStorage(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('hospital/storage/export', {
        ...this.queryParams
      }, `storage_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.open = true;
    },
    /** 有效期格式化，过期显示红色 */
    expiredFormat(row) {
      if (!row.expiryDate) return '';
      const now = new Date();
      const expiryDate = new Date(row.expiryDate);
      const diffTime = expiryDate - now;
      const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

      if (diffDays < 0) {
        return `<span style="color: red;">${row.expiryDate} (已过期)</span>`;
      } else if (diffDays <= 30) {
        return `<span style="color: orange;">${row.expiryDate} (${diffDays}天后过期)</span>`;
      } else {
        return row.expiryDate;
      }
    },
    /** 上传图片前的校验 */
    beforeImageUpload(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png' || file.type === 'image/gif';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$modal.msgError('上传图片只能是 JPG、PNG格式!');
        return false;
      }
      if (!isLt2M) {
        this.$modal.msgError('上传图片大小不能超过 2MB!');
        return false;
      }
      return true;
    },
    /** 图片上传成功回调 */
    handleImageSuccess(response, file) {
      console.log('上传响应:', response); // 添加调试日志
      if (response.code === 200) {
        this.form.imageUrl = response.data?.url || response.url;
        this.$modal.msgSuccess("图片上传成功");
      } else {
        this.$modal.msgError(response.msg || "图片上传失败");
      }
    },
    /** 图片上传失败回调 */
    handleImageError(err) {
      this.$modal.msgError("图片上传失败，请重试");
      console.error('图片上传失败:', err);
    },
    /** 验收按钮操作 */
    handleAcceptance(row) {
      this.$modal.confirm('是否确认验收药材"' + row.medicineName + '"？').then(() => {
        // 构造验收数据
        const acceptanceData = {
          id: row.id,
          acceptanceStatus: 1, // 设置为已验收
          acceptanceBy: this.$store.state.user.name, // 当前用户作为验收人
          acceptanceTime: new Date().toISOString().slice(0, 10) // 当前日期作为验收时间
        };

        // 调用更新接口
        updateStorage(acceptanceData).then(response => {
          this.$modal.msgSuccess("验收成功");
          this.getList(); // 刷新列表
        }).catch(error => {
          this.$modal.msgError("验收失败，请重试");
        });
      }).catch(() => {
        // 用户取消验收
      });
    },
    /** 文件上传进度 */
    handleFileUploadProgress(event, file, fileList) {
      const percent = Math.round((event.loaded / event.total) * 100);
      this.upload.isUploading = true;
      this.$modal.msgInfo(`文件上传中... ${percent}%`);
    },
    /** 文件上传成功回调 */
    handleFileSuccess(response, file, fileList) {
      this.upload.isUploading = false;
      if (response.code === 200) {
        this.$modal.msgSuccess("文件上传成功");
        this.upload.open = false;
        this.getList(); // 刷新列表
      } else {
        this.$modal.msgError(response.msg || "文件上传失败");
      }
    },
    /** 导入模板下载 */
    importTemplate() {
      this.download('hospital/storage/exportTemplate', {}, '药材库存导入模板.xlsx');
    },
    /** 提交文件表单 */
    submitFileForm() {
      // 检查是否选择了文件
      if (this.$refs.upload.uploadFiles.length === 0) {
        this.$modal.msgError('请选择要导入的文件');
        return;
      }

      this.$modal.confirm('确定要导入选中的文件吗？').then(() => {
        this.$refs.upload.submit();
      }).catch(() => {
        // 用户取消导入
      });
    }
  }
};
</script>

<style scoped>
.text-danger {
  color: #f56c6c;
  font-weight: bold;
}

.upload-image-slot {
  width: 120px;
  height: 120px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  background-color: #f5f7fa;
  transition: border-color 0.3s;
}

.upload-image-slot:hover {
  border-color: #409eff;
}

.upload-image-slot i {
  font-size: 28px;
  color: #8c939d;
  margin-bottom: 5px;
}

.upload-image-slot div {
  font-size: 14px;
  color: #8c939d;
}

.upload-tip {
  font-size: 12px;
  color: #909399;
  margin-top: 5px;
  line-height: 1.4;
}

.image-slot {
  display: flex;
  align-items: center;
  justify-content: center;
  color: #c0c4cc;
}
</style>
