<template>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-borrow">
          <svg-icon icon-class="edit" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            今日借阅量
          </div>
          <count-to :start-val="0" :end-val="stats.todayBorrow" :duration="2000" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-return">
          <svg-icon icon-class="checkbox" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            今日归还量
          </div>
          <count-to :start-val="0" :end-val="stats.todayReturn" :duration="2000" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-current">
          <svg-icon icon-class="list" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            当前在借
          </div>
          <count-to :start-val="0" :end-val="stats.currentBorrow" :duration="2000" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-overdue">
          <svg-icon icon-class="bug" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            逾期未还
          </div>
          <count-to :start-val="0" :end-val="stats.overdueCount" :duration="2000" class="card-panel-num" />
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import CountTo from 'vue-count-to'
import { getStats } from '@/api/dashboard'

export default {
  components: {
    CountTo
  },
  data() {
    return {
      stats: {
        todayBorrow: 0,
        todayReturn: 0,
        currentBorrow: 0,
        overdueCount: 0
      },
      timer: null
    }
  },
  mounted() {
    this.fetchStats()
  },
  methods: {
    fetchStats() {
      getStats().then(res => {
        this.stats = res.data
      }).catch(() => {
        this.stats = { todayBorrow: 0, todayReturn: 0, currentBorrow: 0, overdueCount: 0 }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-borrow {
        background: #2563eb;
      }

      .icon-return {
        background: #10b981;
      }

      .icon-current {
        background: #f59e0b;
      }

      .icon-overdue {
        background: #ef4444;
      }
    }

    .icon-borrow {
      color: #2563eb;
    }

    .icon-return {
      color: #10b981;
    }

    .icon-current {
      color: #f59e0b;
    }

    .icon-overdue {
      color: #ef4444;
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width:550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
