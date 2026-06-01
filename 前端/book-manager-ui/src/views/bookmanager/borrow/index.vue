<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="读者姓名" prop="readerName">
        <el-input
          v-model="queryParams.readerName"
          placeholder="请输入读者姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="图书名称" prop="bookName">
        <el-input
          v-model="queryParams.bookName"
          placeholder="请输入图书名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="借阅状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择借阅状态" clearable>
          <el-option
            v-for="dict in dict.type.borrow_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="借阅日期">
        <el-date-picker
          v-model="dateRange"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd"
          style="width: 240px"
        >
        </el-date-picker>
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
          @click="handleBorrow"
        >借书</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="borrowList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="借阅ID" align="center" prop="borrowId" v-if="false"/>
      <el-table-column label="读者姓名" align="center" prop="readerName" />
      <el-table-column label="图书名称" align="center" prop="bookName" show-overflow-tooltip />
      <el-table-column label="借阅日期" align="center" prop="borrowDate" width="110">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.borrowDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="应还日期" align="center" prop="dueDate" width="110">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实际归还日期" align="center" prop="returnDate" width="110">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.returnDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.borrow_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="续借次数" align="center" prop="renewCount" />
      <el-table-column label="罚款金额" align="center" prop="fineAmount" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="220">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
          >查看详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-refresh-left"
            v-if="scope.row.status === '0' || scope.row.status === '2'"
            @click="handleReturn(scope.row)"
          >还书</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-refresh"
            v-if="scope.row.status === '0' && scope.row.renewCount < 2"
            @click="handleRenew(scope.row)"
          >续借</el-button>
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

    <!-- 借书弹窗 -->
    <el-dialog :title="'借书'" :visible.sync="borrowOpen" width="550px" append-to-body>
      <el-form ref="borrowForm" :model="borrowFormData" :rules="borrowRules" label-width="80px">
        <el-form-item label="选择读者" prop="readerId">
          <el-select
            v-model="borrowFormData.readerId"
            filterable
            remote
            reserve-keyword
            placeholder="请输入读者姓名搜索"
            :remote-method="searchReader"
            :loading="readerLoading"
            @change="handleReaderChange"
            style="width: 100%"
          >
            <el-option
              v-for="reader in readerOptions"
              :key="reader.readerId"
              :label="reader.name"
              :value="reader.readerId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="读者信息" v-if="selectedReader.name">
          <el-tag>姓名：{{ selectedReader.name }}</el-tag>
          <el-tag type="info" style="margin-left: 10px">当前在借：{{ selectedReader.borrowCount || 0 }} 本</el-tag>
        </el-form-item>
        <el-form-item label="选择图书" prop="bookId">
          <el-select
            v-model="borrowFormData.bookId"
            filterable
            remote
            reserve-keyword
            placeholder="请输入图书名称搜索"
            :remote-method="searchBook"
            :loading="bookLoading"
            @change="handleBookChange"
            style="width: 100%"
          >
            <el-option
              v-for="book in bookOptions"
              :key="book.bookId"
              :label="'《' + book.bookName + '》'"
              :value="book.bookId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="图书信息" v-if="selectedBook.bookName">
          <el-tag>书名：《{{ selectedBook.bookName }}》</el-tag>
          <el-tag :type="selectedBook.stock > 0 ? 'success' : 'danger'" style="margin-left: 10px">库存：{{ selectedBook.stock }}</el-tag>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitBorrow">确 定</el-button>
        <el-button @click="borrowOpen = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 查看详情弹窗 -->
    <el-dialog :title="'借阅详情'" :visible.sync="detailOpen" width="500px" append-to-body>
      <el-descriptions :column="1" border>
        <el-descriptions-item label="借阅ID">{{ detailForm.borrowId }}</el-descriptions-item>
        <el-descriptions-item label="读者姓名">{{ detailForm.readerName }}</el-descriptions-item>
        <el-descriptions-item label="图书名称">{{ detailForm.bookName }}</el-descriptions-item>
        <el-descriptions-item label="借阅日期">{{ parseTime(detailForm.borrowDate, '{y}-{m}-{d}') }}</el-descriptions-item>
        <el-descriptions-item label="应还日期">{{ parseTime(detailForm.dueDate, '{y}-{m}-{d}') }}</el-descriptions-item>
        <el-descriptions-item label="实际归还日期">{{ parseTime(detailForm.returnDate, '{y}-{m}-{d}') }}</el-descriptions-item>
        <el-descriptions-item label="续借次数">{{ detailForm.renewCount }}</el-descriptions-item>
        <el-descriptions-item label="罚款金额">{{ detailForm.fineAmount }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <dict-tag :options="dict.type.borrow_status" :value="detailForm.status"/>
        </el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailOpen = false">关 闭</el-button>
      </div>
    </el-dialog>

    <!-- 还书确认弹窗 -->
    <el-dialog :title="'还书确认'" :visible.sync="returnOpen" width="450px" append-to-body>
      <el-descriptions :column="1" border>
        <el-descriptions-item label="借阅ID">{{ returnForm.borrowId }}</el-descriptions-item>
        <el-descriptions-item label="读者姓名">{{ returnForm.readerName }}</el-descriptions-item>
        <el-descriptions-item label="图书名称">{{ returnForm.bookName }}</el-descriptions-item>
        <el-descriptions-item label="借阅日期">{{ parseTime(returnForm.borrowDate, '{y}-{m}-{d}') }}</el-descriptions-item>
        <el-descriptions-item label="应还日期">{{ parseTime(returnForm.dueDate, '{y}-{m}-{d}') }}</el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="confirmReturn">确认还书</el-button>
        <el-button @click="returnOpen = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listBorrow, getBorrow, borrowBook, returnBook, renewBook } from "@/api/bookmanager/borrow";
import { listReader } from "@/api/bookmanager/reader";
import { listBook } from "@/api/bookmanager/book";

export default {
  name: "Borrow",
  dicts: ['borrow_status'],
  data() {
    return {
      loading: false,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 1,
      borrowList: [],
      title: "",
      open: false,
      dateRange: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        readerName: null,
        bookName: null,
        status: null,
        beginDate: null,
        endDate: null
      },
      borrowOpen: false,
      borrowFormData: {
        readerId: null,
        bookId: null
      },
      borrowRules: {
        readerId: [
          { required: true, message: "请选择读者", trigger: "change" }
        ],
        bookId: [
          { required: true, message: "请选择图书", trigger: "change" }
        ]
      },
      readerLoading: false,
      readerOptions: [],
      bookLoading: false,
      bookOptions: [],
      selectedReader: {},
      selectedBook: {},
      detailOpen: false,
      detailForm: {},
      returnOpen: false,
      returnForm: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      if (this.dateRange && this.dateRange.length === 2) {
        this.queryParams.beginDate = this.dateRange[0];
        this.queryParams.endDate = this.dateRange[1];
      } else {
        this.queryParams.beginDate = null;
        this.queryParams.endDate = null;
      }
      listBorrow(this.queryParams).then(response => {
        this.borrowList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.borrowId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleBorrow() {
      this.borrowFormData = {
        readerId: null,
        bookId: null
      };
      this.selectedReader = {};
      this.selectedBook = {};
      this.readerOptions = [];
      this.bookOptions = [];
      this.borrowOpen = true;
    },
    searchReader(query) {
      if (query !== '') {
        this.readerLoading = true;
        listReader({ name: query, pageNum: 1, pageSize: 10 }).then(response => {
          this.readerOptions = response.rows;
          this.readerLoading = false;
        });
      } else {
        this.readerOptions = [];
      }
    },
    handleReaderChange(readerId) {
      const reader = this.readerOptions.find(r => r.readerId === readerId);
      if (reader) {
        this.selectedReader = reader;
      } else {
        this.selectedReader = {};
      }
    },
    searchBook(query) {
      if (query !== '') {
        this.bookLoading = true;
        listBook({ bookName: query, pageNum: 1, pageSize: 10 }).then(response => {
          this.bookOptions = response.rows;
          this.bookLoading = false;
        });
      } else {
        this.bookOptions = [];
      }
    },
    handleBookChange(bookId) {
      const book = this.bookOptions.find(b => b.bookId === bookId);
      if (book) {
        this.selectedBook = book;
      } else {
        this.selectedBook = {};
      }
    },
    submitBorrow() {
      this.$refs["borrowForm"].validate(valid => {
        if (valid) {
          borrowBook(this.borrowFormData).then(response => {
            this.$modal.msgSuccess("借书成功");
            this.borrowOpen = false;
            this.getList();
          });
        }
      });
    },
    handleDetail(row) {
      getBorrow(row.borrowId).then(response => {
        this.detailForm = response.data;
        this.detailOpen = true;
      });
    },
    handleReturn(row) {
      this.returnForm = { ...row };
      this.returnOpen = true;
    },
    confirmReturn() {
      returnBook(this.returnForm.borrowId).then(response => {
        this.$modal.msgSuccess("还书成功");
        this.returnOpen = false;
        this.getList();
      });
    },
    handleRenew(row) {
      this.$modal.confirm('确认续借图书"' + row.bookName + '"吗？续借后将延长借阅时间。').then(function () {
        return renewBook(row.borrowId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("续借成功");
      }).catch(() => {
      });
    }
  }
};
</script>
