<template>
  <div class="app-container">
    <el-card>
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="图书名称" prop="bookName">
          <el-input
            v-model="queryParams.bookName"
            placeholder="请输入图书名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="分类" prop="category">
          <el-select v-model="queryParams.category" placeholder="请选择分类" clearable>
            <el-option
              v-for="dict in dict.type.book_type_list"
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
    </el-card>

    <el-row :gutter="20" v-loading="loading" style="margin-top: 15px;">
      <el-col :span="6" v-for="(book, index) in bookList" :key="index" style="margin-bottom: 15px;">
        <el-card shadow="hover" class="book-card" @click.native="handleDetail(book)">
          <div class="book-cover">
            <img v-if="book.coverUrl" :src="book.coverUrl" class="cover-img" />
            <i v-else class="el-icon-reading default-cover"></i>
          </div>
          <div class="book-info">
            <div class="book-name">{{ book.bookName }}</div>
            <div class="book-author">作者：{{ book.author }}</div>
            <div class="book-publisher">出版社：{{ book.publisher }}</div>
            <div class="book-stock">
              <el-tag v-if="book.stock > 0" type="success" size="small">可借</el-tag>
              <el-tag v-else type="info" size="small">已借完</el-tag>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-empty v-if="!loading && bookList.length === 0" description="暂无图书数据"></el-empty>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" label-width="100px">
        <el-row>
          <el-col :span="8">
            <div class="detail-cover">
              <img v-if="form.coverUrl" :src="form.coverUrl" style="width:100%;" />
              <i v-else class="el-icon-reading" style="font-size: 120px; color: #ccc; display: block; text-align: center;"></i>
            </div>
          </el-col>
          <el-col :span="16">
            <el-form-item label="图书名称">
              <span>{{ form.bookName }}</span>
            </el-form-item>
            <el-form-item label="作者">
              <span>{{ form.author }}</span>
            </el-form-item>
            <el-form-item label="出版社">
              <span>{{ form.publisher }}</span>
            </el-form-item>
            <el-form-item label="ISBN">
              <span>{{ form.isbn }}</span>
            </el-form-item>
            <el-form-item label="分类">
              <span>{{ form.categoryName }}</span>
            </el-form-item>
            <el-form-item label="库存">
              <el-tag v-if="form.stock > 0" type="success" size="small">{{ form.stock }} 册可借</el-tag>
              <el-tag v-else type="info" size="small">暂无库存</el-tag>
            </el-form-item>
            <el-form-item label="简介">
              <span>{{ form.description }}</span>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { searchBooks } from "@/api/portal/index";

export default {
  name: "BookSearch",
  dicts: ['book_type_list'],
  data() {
    return {
      loading: false,
      total: 0,
      bookList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 8,
        bookName: null,
        category: null
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
      searchBooks(this.queryParams).then(response => {
        this.bookList = response.rows;
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
    cancel() {
      this.open = false;
    },
    handleDetail(row) {
      this.form = {
        bookName: row.bookName,
        author: row.author,
        publisher: row.publisher,
        isbn: row.isbn,
        categoryName: row.categoryName,
        stock: row.stock,
        description: row.description,
        coverUrl: row.coverUrl
      };
      this.open = true;
      this.title = "图书详情";
    }
  }
};
</script>

<style scoped>
.book-card {
  cursor: pointer;
  transition: all 0.3s;
}
.book-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}
.book-cover {
  width: 100%;
  height: 180px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f7fa;
  margin-bottom: 10px;
  overflow: hidden;
}
.cover-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.default-cover {
  font-size: 60px;
  color: #c0c4cc;
}
.book-info {
  padding: 0 5px;
}
.book-name {
  font-size: 14px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 8px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.book-author,
.book-publisher {
  font-size: 12px;
  color: #909399;
  margin-bottom: 4px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.book-stock {
  margin-top: 8px;
  text-align: right;
}
.detail-cover {
  min-height: 150px;
  background: #f5f7fa;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
