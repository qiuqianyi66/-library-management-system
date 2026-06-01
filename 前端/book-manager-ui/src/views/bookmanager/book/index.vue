<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="图书名称" prop="bookName">
        <el-input
          v-model="queryParams.bookName"
          placeholder="请输入图书名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="ISBN" prop="isbn">
        <el-input
          v-model="queryParams.isbn"
          placeholder="请输入ISBN"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择类型" clearable>
          <el-option
            v-for="dict in dict.type.book_type_list"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input
          v-model="queryParams.author"
          placeholder="请输入作者"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
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

    <el-table v-loading="loading" :data="bookList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="图书ID" align="center" prop="bookId" v-if="false"/>
      <el-table-column label="图书名称" align="center" prop="bookName" show-overflow-tooltip />
      <el-table-column label="ISBN" align="center" prop="isbn" />
      <el-table-column label="类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.book_type_list" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="作者" align="center" prop="author" />
      <el-table-column label="出版社" align="center" prop="publisher" show-overflow-tooltip />
      <el-table-column label="价格" align="center" prop="price" />
      <el-table-column label="库存" align="center" prop="stock" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            :icon="scope.row.status === '0' ? 'el-icon-top' : 'el-icon-bottom'"
            @click="handleChangeStatus(scope.row)"
          >{{ scope.row.status === '0' ? '下架' : '上架' }}</el-button>
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

    <el-dialog :title="title" :visible.sync="open" width="650px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="图书名称" prop="bookName">
              <el-input v-model="form.bookName" placeholder="请输入图书名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="ISBN" prop="isbn">
              <el-input v-model="form.isbn" placeholder="请输入ISBN" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="作者" prop="author">
              <el-input v-model="form.author" placeholder="请输入作者" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出版社" prop="publisher">
              <el-input v-model="form.publisher" placeholder="请输入出版社" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="分类" prop="type">
              <el-select v-model="form.type" placeholder="请选择分类">
                <el-option
                  v-for="dict in dict.type.book_type_list"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="价格" prop="price">
              <el-input-number v-model="form.price" :precision="2" :min="0" placeholder="请输入价格" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="库存数量" prop="stock">
              <el-input-number v-model="form.stock" :min="0" placeholder="请输入库存数量" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="书架位置" prop="shelfLocation">
              <el-input v-model="form.shelfLocation" placeholder="请输入书架位置" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="出版日期" prop="publishDate">
              <el-date-picker clearable
                v-model="form.publishDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择出版日期"
                style="width: 100%">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="封面图片" prop="coverImage">
              <image-upload v-model="form.coverImage"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="简介" prop="description">
              <el-input v-model="form.description" type="textarea" placeholder="请输入图书简介" :rows="3" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listBook, getBook, delBook, addBook, updateBook, changeBookStatus } from "@/api/bookmanager/book";

export default {
  name: "Book",
  dicts: ['book_type_list', 'sys_normal_disable'],
  data() {
    return {
      loading: false,
      ids: [],
      names: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 1,
      bookList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bookName: null,
        isbn: null,
        type: null,
        author: null,
        status: null
      },
      form: {},
      rules: {
        bookName: [
          { required: true, message: "图书名称不能为空", trigger: "blur" }
        ],
        isbn: [
          { required: true, message: "ISBN不能为空", trigger: "blur" }
        ],
        author: [
          { required: true, message: "作者不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "分类不能为空", trigger: "change" }
        ],
        price: [
          { required: true, message: "价格不能为空", trigger: "blur" }
        ],
        stock: [
          { required: true, message: "库存数量不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      listBook(this.queryParams).then(response => {
        this.bookList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    reset() {
      this.form = {
        bookId: null,
        bookName: null,
        isbn: null,
        author: null,
        publisher: null,
        type: null,
        price: null,
        stock: null,
        shelfLocation: null,
        publishDate: null,
        coverImage: null,
        description: null,
        status: "0"
      };
      this.resetForm("form");
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.bookId)
      this.names = selection.map(item => item.bookName)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加图书";
    },
    handleUpdate(row) {
      this.reset();
      const bookId = row.bookId || this.ids
      getBook(bookId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改图书";
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.bookId != null) {
            updateBook(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBook(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    handleDelete(row) {
      const bookIds = row.bookId || this.ids;
      const names = row.bookName || this.names;
      this.$modal.confirm('是否确认删除图书名称为"' + names + '"的数据项？').then(function () {
        return delBook(bookIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    handleChangeStatus(row) {
      let statusText = row.status === '0' ? '下架' : '上架';
      let newStatus = row.status === '0' ? '1' : '0';
      this.$modal.confirm('确认要"' + statusText + '"图书"' + row.bookName + '"吗？').then(function () {
        return changeBookStatus({ bookId: row.bookId, status: newStatus });
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(statusText + "成功");
      }).catch(() => {
      });
    }
  }
};
</script>
