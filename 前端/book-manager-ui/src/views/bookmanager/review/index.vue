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
      <el-form-item label="评分" prop="rating">
        <el-select v-model="queryParams.rating" placeholder="请选择评分" clearable>
          <el-option label="5星" value="5" />
          <el-option label="4星" value="4" />
          <el-option label="3星" value="3" />
          <el-option label="2星" value="2" />
          <el-option label="1星" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option label="显示" value="1" />
          <el-option label="隐藏" value="0" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="reviewList">
      <el-table-column label="评论ID" align="center" prop="reviewId" />
      <el-table-column label="图书名称" align="center" prop="bookName" show-overflow-tooltip />
      <el-table-column label="读者" align="center" prop="readerName" />
      <el-table-column label="评分" align="center" prop="rating" width="180">
        <template slot-scope="scope">
          <book-rating :value="scope.row.rating" :readonly="true" />
        </template>
      </el-table-column>
      <el-table-column label="评论内容" align="center" prop="content" show-overflow-tooltip min-width="200" />
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === '1' ? 'success' : 'info'">{{ scope.row.status === '1' ? '显示' : '隐藏' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="评论时间" align="center" prop="createTime" width="180" />
      <el-table-column label="操作" align="center" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            :icon="scope.row.status === '1' ? 'el-icon-hide' : 'el-icon-view'"
            :type="scope.row.status === '1' ? 'warning' : 'success'"
            @click="handleToggleStatus(scope.row)"
          >{{ scope.row.status === '1' ? '隐藏' : '显示' }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
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

    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="图书名称">
          <el-input v-model="form.bookName" :disabled="true" />
        </el-form-item>
        <el-form-item label="读者">
          <el-input v-model="form.readerName" :disabled="true" />
        </el-form-item>
        <el-form-item label="评分">
          <book-rating :value="form.rating" :readonly="true" :show-text="true" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio label="1">显示</el-radio>
            <el-radio label="0">隐藏</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="评论内容">
          <el-input type="textarea" v-model="form.content" :disabled="true" :rows="6" />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listReview, updateReview, delReview } from "@/api/bookmanager/review";
import BookRating from "@/components/BookRating";

export default {
  name: "Review",
  components: { BookRating },
  data() {
    return {
      loading: false,
      showSearch: true,
      total: 0,
      reviewList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bookName: null,
        rating: null,
        status: null
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
      listReview(this.queryParams).then(response => {
        this.reviewList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleDetail(row) {
      this.form = {
        reviewId: row.reviewId,
        bookName: row.bookName,
        readerName: row.readerName,
        rating: row.rating,
        content: row.content,
        status: row.status
      };
      this.title = "评论详情";
      this.open = true;
    },
    handleToggleStatus(row) {
      const newStatus = row.status === '1' ? '0' : '1';
      const statusText = newStatus === '1' ? '显示' : '隐藏';
      this.$modal.confirm('确认' + statusText + '该评论？').then(() => {
        updateReview({ reviewId: row.reviewId, status: newStatus }).then(() => {
          this.$modal.msgSuccess(statusText + "成功");
          this.getList();
        });
      }).catch(() => {});
    },
    handleDelete(row) {
      this.$modal.confirm('确认删除该评论？').then(() => {
        delReview(row.reviewId).then(() => {
          this.$modal.msgSuccess("删除成功");
          this.getList();
        });
      }).catch(() => {});
    },
    cancel() {
      this.open = false;
    },
    submitForm() {
      updateReview(this.form).then(() => {
        this.$modal.msgSuccess("修改成功");
        this.open = false;
        this.getList();
      });
    }
  }
};
</script>
