<template>
  <div class="app-container">
    <el-table v-loading="loading" :data="borrowList">
      <el-table-column label="图书名称" align="center" prop="bookName" />
      <el-table-column label="借阅日期" align="center" prop="borrowDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.borrowDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="应还日期" align="center" prop="dueDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.borrow_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="续借次数" align="center" prop="renewCount" />
      <el-table-column label="罚款金额" align="center" prop="fineAmount" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-refresh"
            @click="handleRenew(scope.row)"
            v-if="scope.row.status === '1' && scope.row.renewCount < 2"
          >续借</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
          >查看详情</el-button>
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

    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" label-width="100px">
        <el-form-item label="图书名称">
          <el-input v-model="form.bookName" :disabled="true"/>
        </el-form-item>
        <el-form-item label="借阅日期">
          <el-input v-model="form.borrowDate" :disabled="true"/>
        </el-form-item>
        <el-form-item label="应还日期">
          <el-input v-model="form.dueDate" :disabled="true"/>
        </el-form-item>
        <el-form-item label="状态">
          <dict-tag :options="dict.type.borrow_status" :value="form.status"/>
        </el-form-item>
        <el-form-item label="续借次数">
          <el-input v-model="form.renewCount" :disabled="true"/>
        </el-form-item>
        <el-form-item label="罚款金额">
          <el-input v-model="form.fineAmount" :disabled="true"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { myBorrow, portalRenew } from "@/api/portal/index";

export default {
  name: "MyBorrow",
  dicts: ['borrow_status'],
  data() {
    return {
      loading: false,
      total: 0,
      borrowList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      title: "",
      open: false,
      form: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      myBorrow(this.queryParams).then(response => {
        this.borrowList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    cancel() {
      this.open = false;
    },
    handleRenew(row) {
      this.$modal.confirm('确认续借图书《' + row.bookName + '》吗？').then(function () {
        return portalRenew(row.borrowId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("续借成功");
      }).catch(() => {
      });
    },
    handleDetail(row) {
      this.form = {
        bookName: row.bookName,
        borrowDate: this.parseTime(row.borrowDate, '{y}-{m}-{d}'),
        dueDate: this.parseTime(row.dueDate, '{y}-{m}-{d}'),
        status: row.status,
        renewCount: row.renewCount,
        fineAmount: row.fineAmount
      };
      this.open = true;
      this.title = "借阅详情";
    }
  }
};
</script>
