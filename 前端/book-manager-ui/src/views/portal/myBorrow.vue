<template>
  <div class="my-library">
    <div class="library-header">
      <h2 class="library-title">📚 我的藏书阁</h2>
      <p class="library-subtitle">共 {{ total }} 本书 · 正在阅读中</p>
    </div>

    <!-- 借阅统计 -->
    <el-row :gutter="16" class="stats-row">
      <el-col :span="6">
        <div class="stat-chip" style="background: rgba(37,99,235,0.1); color: #2563eb;">
          <span class="stat-num">{{ total }}</span> 总借阅
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-chip" style="background: rgba(245,158,11,0.1); color: #f59e0b;">
          <span class="stat-num">{{ borrowingCount }}</span> 在借
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-chip" style="background: rgba(239,68,68,0.1); color: #ef4444;">
          <span class="stat-num">{{ overdueCount }}</span> 逾期
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-chip" style="background: rgba(16,185,129,0.1); color: #10b981;">
          <span class="stat-num">{{ returnedCount }}</span> 已还
        </div>
      </el-col>
    </el-row>

    <!-- 书架网格 -->
    <div v-if="borrowList.length > 0" class="bookshelf">
      <div v-for="(item, idx) in borrowList" :key="idx" class="book-spine" @click="handleDetail(item)">
        <div class="spine-color" :style="{ background: getSpineColor(idx) }"></div>
        <div class="spine-label">{{ item.bookName }}</div>
        <div class="spine-status">
          <span v-if="item.status === 'borrowing'" class="status-dot borrowing"></span>
          <span v-else-if="item.status === 'overdue'" class="status-dot overdue"></span>
          <span v-else class="status-dot returned"></span>
        </div>
      </div>
    </div>

    <el-empty v-else description="你的书架上还没有书，去借一本吧 📖" />

    <!-- 操作面板 -->
    <div class="borrow-list">
      <div v-for="(item, idx) in borrowList" :key="'card-'+idx" class="borrow-card vibe-card-hover" @click="handleDetail(item)">
        <div class="card-left">
          <div class="card-cover" :style="{ background: getSpineColor(idx) }">
            <span class="cover-emoji">{{ getBookEmoji(item) }}</span>
          </div>
        </div>
        <div class="card-middle">
          <div class="card-title">{{ item.bookName }}</div>
          <div class="card-meta">
            借阅: {{ parseTime(item.borrowDate, '{m}-{d}') }}
            · 应还: {{ parseTime(item.dueDate, '{m}-{d}') }}
          </div>
          <div class="card-tags">
            <el-tag :type="item.status === 'borrowing' ? 'warning' : item.status === 'overdue' ? 'danger' : 'success'" size="mini" effect="light">
              {{ item.status === 'borrowing' ? '在借' : item.status === 'overdue' ? '逾期' : '已还' }}
            </el-tag>
            <el-tag v-if="item.renewCount > 0" size="mini" type="info" effect="plain">续借{{ item.renewCount }}次</el-tag>
            <el-tag v-if="item.fineAmount > 0" size="mini" type="danger" effect="plain">罚款 ¥{{ item.fineAmount }}</el-tag>
          </div>
        </div>
        <div class="card-right">
          <el-button
            size="mini"
            type="primary"
            plain
            @click.stop="handleRenew(item)"
            v-if="item.status === 'borrowing' && item.renewCount < maxRenewCount"
          >续借</el-button>
          <div class="due-info" v-if="item.status === 'borrowing' || item.status === 'overdue'">
            <span :class="{ overdue: item.status === 'overdue' }">
              剩 {{ getRemainingDays(item.dueDate) }} 天
            </span>
          </div>
        </div>
      </div>
    </div>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 详情弹窗 -->
    <el-dialog :title="'📖 ' + form.bookName" :visible.sync="open" width="480px" append-to-body class="vibe-dialog">
      <div class="detail-grid">
        <div class="detail-cover-big" :style="{ background: detailColor }">
          <span class="big-emoji">{{ form.emoji }}</span>
        </div>
        <div class="detail-info">
          <div class="detail-row"><label>借阅日期</label><span>{{ form.borrowDate }}</span></div>
          <div class="detail-row"><label>应还日期</label><span>{{ form.dueDate }}</span></div>
          <div class="detail-row"><label>状 态</label>
            <el-tag :type="form.status === 'borrowing' ? 'warning' : form.status === 'overdue' ? 'danger' : 'success'" size="mini">
              {{ form.status === 'borrowing' ? '在借' : form.status === 'overdue' ? '逾期' : '已还' }}
            </el-tag>
          </div>
          <div class="detail-row"><label>续借次数</label><span>{{ form.renewCount }} 次</span></div>
          <div class="detail-row"><label>罚款金额</label><span v-if="form.fineAmount > 0" style="color:#ef4444;">¥{{ form.fineAmount }}</span><span v-else>-</span></div>
        </div>
      </div>
      <div slot="footer">
        <el-button @click="open = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getConfigKey } from "@/api/system/config";
import { myBorrow, portalRenew } from "@/api/portal/index";

export default {
  name: "MyLibrary",
  data() {
    return {
      loading: false,
      total: 0,
      borrowList: [],
      queryParams: { pageNum: 1, pageSize: 20 },
      maxRenewCount: 2,
      open: false,
      form: {},
      detailColor: '#2563eb',
      bookColors: ['#2563eb','#7c3aed','#059669','#d97706','#dc2626','#0891b2','#4f46e5','#be185d','#65a30d','#0d9488']
    }
  },
  computed: {
    borrowingCount() { return this.borrowList.filter(b => b.status === 'borrowing').length },
    overdueCount() { return this.borrowList.filter(b => b.status === 'overdue').length },
    returnedCount() { return this.borrowList.filter(b => b.status === 'returned').length }
  },
  created() { this.getList(); this.loadConfig() },
  methods: {
    getList() {
      this.loading = true
      myBorrow(this.queryParams).then(r => {
        this.borrowList = r.rows || []
        this.total = r.total || 0
        this.loading = false
      })
    },
    loadConfig() {
      getConfigKey('borrow.renew_max_count').then(r => {
        if (r.data) this.maxRenewCount = parseInt(r.data)
      })
    },
    getSpineColor(i) { return this.bookColors[i % this.bookColors.length] },
    getBookEmoji(b) {
      if (b.status === 'overdue') return '⚠️'
      if (b.renewCount > 1) return '📖'
      return '📚'
    },
    getRemainingDays(date) {
      if (!date) return '--'
      const diff = new Date(date) - new Date()
      return Math.ceil(diff / (1000*60*60*24))
    },
    handleRenew(row) {
      this.$modal.confirm('续借《' + row.bookName + '》？').then(() =>
        portalRenew(row.borrowId)
      ).then(() => {
        this.$vibe.success('续借成功！')
        this.getList()
      }).catch(() => {})
    },
    handleDetail(row) {
      this.form = {
        bookName: row.bookName,
        borrowDate: this.parseTime(row.borrowDate, '{y}-{m}-{d}'),
        dueDate: this.parseTime(row.dueDate, '{y}-{m}-{d}'),
        status: row.status,
        renewCount: row.renewCount,
        fineAmount: row.fineAmount,
        emoji: this.getBookEmoji(row)
      }
      this.detailColor = this.bookColors[this.borrowList.indexOf(row) % this.bookColors.length]
      this.open = true
    },
    cancel() { this.open = false }
  }
}
</script>

<style scoped>
.my-library { padding: 20px; max-width: 1000px; margin: 0 auto; }
.library-header { margin-bottom: 20px; }
.library-title { font-size: 22px; color: var(--vibe-text); margin: 0; }
.library-subtitle { font-size: 14px; color: var(--vibe-text-muted); margin: 4px 0 0; }

/* 统计条 */
.stats-row { margin-bottom: 20px; }
.stat-chip {
  text-align: center; padding: 12px 8px; border-radius: 12px;
  font-size: 13px; font-weight: 500;
}
.stat-num { font-size: 22px; font-weight: bold; margin-right: 4px; }

/* 书架 */
.bookshelf {
  display: flex; gap: 8px; padding: 20px 10px; margin-bottom: 24px;
  background: var(--vibe-bg-hover); border-radius: 16px;
  overflow-x: auto; min-height: 150px;
  border: 1px solid var(--vibe-border-light);
}
.book-spine {
  flex-shrink: 0; width: 36px; border-radius: 6px;
  display: flex; flex-direction: column; align-items: center;
  cursor: pointer; transition: transform 0.2s; position: relative;
  overflow: hidden;
}
.book-spine:hover { transform: translateY(-6px); }
.spine-color {
  width: 100%; height: 100px; border-radius: 6px;
  transition: box-shadow 0.3s;
}
.book-spine:hover .spine-color { box-shadow: 0 4px 12px rgba(0,0,0,0.2); }
.spine-label {
  writing-mode: vertical-rl; font-size: 11px; color: var(--vibe-text-secondary);
  margin-top: 6px; max-height: 60px; overflow: hidden;
  text-overflow: ellipsis; letter-spacing: 2px;
}
.spine-status { margin-top: 4px; }
.status-dot { display: inline-block; width: 8px; height: 8px; border-radius: 50%; }
.status-dot.borrowing { background: #f59e0b; }
.status-dot.overdue { background: #ef4444; animation: pulse 1.5s infinite; }
.status-dot.returned { background: #10b981; }

/* 借阅卡片列表 */
.borrow-list { display: flex; flex-direction: column; gap: 10px; }
.borrow-card {
  display: flex; align-items: center; gap: 16px;
  padding: 14px 18px;
  background: var(--vibe-card-bg);
  border: var(--vibe-card-border);
  border-radius: 14px;
  box-shadow: var(--vibe-card-shadow);
  cursor: pointer;
}
.card-left { flex-shrink: 0; }
.card-cover {
  width: 48px; height: 64px; border-radius: 8px;
  display: flex; align-items: center; justify-content: center;
}
.cover-emoji { font-size: 24px; }
.card-middle { flex: 1; min-width: 0; }
.card-title { font-weight: 600; font-size: 15px; color: var(--vibe-text); margin-bottom: 4px; }
.card-meta { font-size: 12px; color: var(--vibe-text-muted); margin-bottom: 6px; }
.card-tags { display: flex; gap: 4px; flex-wrap: wrap; }
.card-right { text-align: center; flex-shrink: 0; }
.due-info { font-size: 12px; color: var(--vibe-text-muted); margin-top: 6px; text-align: center; }
.due-info .overdue { color: #ef4444; font-weight: bold; }

/* 详情弹窗 */
.detail-grid { display: flex; gap: 20px; }
.detail-cover-big {
  width: 100px; height: 130px; border-radius: 12px;
  display: flex; align-items: center; justify-content: center;
  flex-shrink: 0;
}
.big-emoji { font-size: 40px; }
.detail-info { flex: 1; }
.detail-row { display: flex; padding: 8px 0; border-bottom: 1px solid var(--vibe-border-light); }
.detail-row label { width: 70px; color: var(--vibe-text-muted); font-size: 13px; }
.detail-row span { color: var(--vibe-text); font-size: 13px; }

@keyframes pulse { 0%,100% { opacity: 1; } 50% { opacity: 0.4; } }
</style>
