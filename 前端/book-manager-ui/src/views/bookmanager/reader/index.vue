<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select v-model="queryParams.sex" placeholder="请选择性别" clearable>
          <el-option
            v-for="dict in dict.type.sys_user_sex"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="部门" prop="department">
        <el-select v-model="queryParams.department" placeholder="请选择部门" clearable>
          <el-option
            v-for="dict in dict.type.department_list"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="办卡日期" prop="handleDate">
        <el-date-picker clearable
          v-model="queryParams.handleDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择办卡日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.reader_status"
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
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="readerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="读者ID" align="center" prop="readerId" v-if="false"/>
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="性别" align="center" prop="sex">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.sex"/>
        </template>
      </el-table-column>
      <el-table-column label="部门" align="center" prop="department">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.department_list" :value="scope.row.department"/>
        </template>
      </el-table-column>
      <el-table-column label="办卡日期" align="center" prop="handleDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.handleDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.reader_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['bookmanager:reader:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['bookmanager:reader:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleCheck(scope.row)"
          >查看</el-button>
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

    <!-- 添加或修改读者管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" :disabled="isDisabled"/>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="form.sex">
            <el-radio
              v-for="dict in dict.type.sys_user_sex"
              :key="dict.value"
              :label="dict.value"
              :disabled="isDisabled"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="部门" prop="department">
          <el-select v-model="form.department" placeholder="请选择部门" :disabled="isDisabled">
            <el-option
              v-for="dict in dict.type.department_list"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="办卡日期" prop="handleDate">
          <el-date-picker clearable
            v-model="form.handleDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择办卡日期"
            :disabled="isDisabled">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.reader_status"
              :key="dict.value"
              :label="dict.value"
              :disabled="isDisabled"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" v-if="operation!='check'">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listReader, getReader, delReader, addReader, updateReader } from "@/api/bookmanager/reader";

export default {
  name: "Reader",
  dicts: ['reader_status', 'sys_user_sex', 'department_list'],
  data() {
    return {
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 选中行的姓名数组
      names: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 1,
      // 读者管理表格数据
      readerList: [
        {
          "readerId": 1,
          "name": "李丽",
          "sex": "1",
          "department": "01",
          "handleDate": "2011-09-08",
          "status": "1"
        }
      ],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        sex: null,
        department: null,
        handleDate: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "姓名不能为空", trigger: "blur" }
        ],
        sex: [
          { required: true, message: "性别不能为空", trigger: "change" }
        ],
        department: [
          { required: true, message: "部门不能为空", trigger: "change" }
        ],
        handleDate: [
          { required: true, message: "办卡日期不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ]
      },
      operation: "",
      isDisabled: false
    };
  },
  created() {
    //this.getList();
  },
  methods: {
    /** 查询读者管理列表 */
    getList() {
      this.loading = true;
      listReader(this.queryParams).then(response => {
        this.readerList = response.rows;
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
        readerId: null,
        name: null,
        sex: null,
        department: null,
        handleDate: null,
        status: null
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
      this.ids = selection.map(item => item.readerId)
      this.names = selection.map(item => item.name)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加读者管理";
      this.operation = "add";
      this.isDisabled = false;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const readerId = row.readerId || this.ids
      getReader(readerId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改读者管理";
        this.operation = "update";
        this.isDisabled = false;
      });
    },
    /** 查看按钮操作 */
    handleCheck(row) {
      this.reset();
      const readerId = row.readerId || this.ids
      getReader(readerId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "查看读者";
        this.operation = "check";
        this.isDisabled = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.readerId != null) {
            updateReader(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.operation = "";
              this.getList();
            });
          } else {
            addReader(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.operation = "";
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const readerIds = row.readerId || this.ids;
      const names= row.name || this.names;
      this.$modal.confirm('是否确认删除读者姓名为"' + names + '"的数据项？').then(function () {
        return delReader(readerIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    }
  }
};
</script>
