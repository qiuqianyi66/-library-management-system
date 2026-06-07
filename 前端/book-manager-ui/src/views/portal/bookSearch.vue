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
        <el-form-item label="分类" prop="type">
          <el-select v-model="queryParams.type" placeholder="请选择分类" clearable>
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
            <div class="book-rating-line">
              <book-rating :value="book.avgRating || 0" :readonly="true" />
              <span class="rating-count">{{ book.ratingCount || 0 }}人评</span>
            </div>
            <div class="book-author">作者：{{ book.author }}</div>
            <div class="book-publisher">出版社：{{ book.press }}</div>
            <div class="book-stock">
              <el-tag v-if="book.stockCount > 0" type="success" size="small">可借</el-tag>
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

    <!-- 图书详情弹窗 -->
    <el-dialog :title="title" :visible.sync="open" width="750px" append-to-body>
      <el-tabs v-model="activeTab">
        <el-tab-pane label="图书信息" name="info">
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
                <el-form-item label="评分">
                  <book-rating :value="form.avgRating || 0" :readonly="true" :show-text="true" />
                  <span style="margin-left: 8px; color: #909399;">({{ form.ratingCount || 0 }}人评价)</span>
                </el-form-item>
                <el-form-item label="作者">
                  <span>{{ form.author }}</span>
                </el-form-item>
                <el-form-item label="出版社">
                  <span>{{ form.press }}</span>
                </el-form-item>
                <el-form-item label="ISBN">
                  <span>{{ form.isbn }}</span>
                </el-form-item>
                <el-form-item label="分类">
                  <span>{{ form.type }}</span>
                </el-form-item>
                <el-form-item label="库存">
                  <el-tag v-if="form.stockCount > 0" type="success" size="small">{{ form.stockCount }} 册可借</el-tag>
                  <el-tag v-else type="info" size="small">暂无库存</el-tag>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="简介">
              <span>{{ form.description }}</span>
            </el-form-item>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="读者评论" name="reviews">
          <div class="review-summary" v-if="reviews.length > 0">
            <div class="avg-rating">
              <book-rating :value="avgRating" :readonly="true" />
              <span class="avg-text">{{ avgRating }} 分 · {{ reviews.length }} 条评论</span>
            </div>
          </div>
          <div v-if="reviews.length === 0" style="text-align:center; padding: 30px; color: #909399;">
            暂无评论，快来评分吧！
          </div>

          <div v-for="(review, idx) in reviews" :key="idx" class="review-item">
            <div class="review-header">
              <span class="review-user">{{ review.readerName || '匿名读者' }}</span>
              <book-rating :value="review.rating" :readonly="true" />
              <span class="review-time">{{ review.createTime }}</span>
            </div>
            <div class="review-content">{{ review.content }}</div>
          </div>

          <el-divider></el-divider>
          <div class="submit-review">
            <h4>写评论</h4>
            <div style="margin-bottom: 10px;">
              <book-rating v-model="newReview.rating" />
            </div>
            <el-input
              type="textarea"
              v-model="newReview.content"
              placeholder="分享你的阅读体验..."
              :rows="3"
              maxlength="500"
              show-word-limit
            />
            <el-button type="primary" style="margin-top: 10px;" @click="submitReview">提交评论</el-button>
          </div>
        </el-tab-pane>

        <el-tab-pane label="相关推荐" name="recommend">
          <div v-if="recommendList.length === 0" style="text-align:center; padding: 30px; color: #909399;">
            暂无相关推荐
          </div>
          <el-row :gutter="20">
            <el-col :span="12" v-for="(item, idx) in recommendList" :key="idx" style="margin-bottom: 15px;">
              <el-card shadow="hover" class="recommend-card">
                <div class="recommend-name">{{ item.book_name }}</div>
                <div class="recommend-author">{{ item.author }}</div>
              </el-card>
            </el-col>
          </el-row>
        </el-tab-pane>
      </el-tabs>

      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { searchBooks } from "@/api/portal/index";
import { getBookReviews, submitReview } from "@/api/portal/review";
import { recommendByBook } from "@/api/portal/recommend";
import BookRating from "@/components/BookRating";

export default {
  name: "BookSearch",
  components: { BookRating },
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
        type: null
      },
      title: "",
      open: false,
      form: {},
      activeTab: 'info',
      reviews: [],
      avgRating: 0,
      newReview: {
        bookId: null,
        rating: 5,
        content: ''
      },
      recommendList: []
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
      this.form = { ...row };
      this.newReview = { bookId: row.bookId, rating: 5, content: '' };
      this.activeTab = 'info';
      this.open = true;
      this.title = "📖 " + row.bookName;

      // 加载评论
      this.loadReviews(row.bookId);
      // 加载推荐
      this.loadRecommend(row.bookId);
    },
    loadReviews(bookId) {
      getBookReviews(bookId).then(res => {
        const data = res.data || {};
        this.reviews = data.reviews || [];
        this.avgRating = data.avgRating || 0;
        // 更新当前选中图书的评分显示
        this.form.avgRating = data.avgRating;
        this.form.ratingCount = data.ratingCount;
      }).catch(() => {
        this.reviews = [];
        this.avgRating = 0;
      });
    },
    loadRecommend(bookId) {
      recommendByBook(bookId).then(res => {
        this.recommendList = res.data || [];
      }).catch(() => {
        this.recommendList = [];
      });
    },
    submitReview() {
      if (!this.newReview.content.trim()) {
        this.$message.warning('请输入评论内容')
        return
      }
      submitReview({
        bookId: this.form.bookId,
        rating: this.newReview.rating,
        content: this.newReview.content
      }).then(() => {
        this.$message.success('评论提交成功！')
        this.newReview.content = ''
        this.loadReviews(this.form.bookId)
      })
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
  margin-bottom: 6px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.book-rating-line {
  display: flex;
  align-items: center;
  margin-bottom: 4px;
}
.rating-count {
  font-size: 12px;
  color: #909399;
  margin-left: 4px;
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
  margin-top: 6px;
  text-align: right;
}
.detail-cover {
  min-height: 150px;
  background: #f5f7fa;
  display: flex;
  align-items: center;
  justify-content: center;
}
/* 评论区域 */
.review-summary {
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid #ebeef5;
}
.avg-rating {
  display: flex;
  align-items: center;
}
.avg-text {
  font-size: 16px;
  color: #303133;
  margin-left: 12px;
}
.review-item {
  padding: 12px 0;
  border-bottom: 1px solid #f2f2f2;
}
.review-header {
  display: flex;
  align-items: center;
  margin-bottom: 6px;
}
.review-user {
  font-weight: bold;
  margin-right: 10px;
}
.review-time {
  font-size: 12px;
  color: #c0c4cc;
  margin-left: 10px;
}
.review-content {
  color: #606266;
  line-height: 1.6;
}
.submit-review {
  padding-top: 10px;
}
/* 推荐卡片 */
.recommend-card {
  cursor: pointer;
}
.recommend-name {
  font-weight: bold;
  color: #303133;
  margin-bottom: 4px;
}
.recommend-author {
  font-size: 12px;
  color: #909399;
}
</style>
