<template>
  <div class="app-container">
    <h2 style="margin-bottom: 20px; color: #303133;">
      📊 我的阅读报告
    </h2>

    <!-- 统计卡片 -->
    <el-row :gutter="20" style="margin-bottom: 20px;">
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-value" style="color: #2563eb;">{{ summary.totalCount }}</div>
            <div class="stat-label">总借阅次数</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-value" style="color: #f59e0b;">{{ summary.currentCount }}</div>
            <div class="stat-label">当前在借</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-value" style="color: #ef4444;">{{ summary.overdueCount }}</div>
            <div class="stat-label">逾期未还</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-value" style="color: #10b981;">{{ summary.returnedCount }}</div>
            <div class="stat-label">已归还</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表 -->
    <el-row :gutter="20">
      <el-col :span="14">
        <el-card shadow="hover">
          <div slot="header"><span>近30天借阅趋势</span></div>
          <div ref="trendChart" style="height: 350px;"></div>
        </el-card>
      </el-col>
      <el-col :span="10">
        <el-card shadow="hover">
          <div slot="header"><span>阅读偏好（按类型）</span></div>
          <div ref="typeChart" style="height: 350px;"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { mySummary, myMonthlyTrend, myTypePreference } from '@/api/portal/stats'

export default {
  name: 'MyReadingReport',
  data() {
    return {
      summary: {
        totalCount: 0,
        currentCount: 0,
        overdueCount: 0,
        returnedCount: 0
      },
      trendChart: null,
      typeChart: null
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initCharts()
    })
    this.fetchData()
  },
  beforeDestroy() {
    this.trendChart?.dispose()
    this.typeChart?.dispose()
  },
  methods: {
    fetchData() {
      mySummary().then(res => {
        this.summary = res.data || this.summary
      })
      myMonthlyTrend().then(res => {
        const data = res.data || []
        this.renderTrendChart(data)
      })
      myTypePreference().then(res => {
        const data = res.data || []
        this.renderTypeChart(data)
      })
    },
    initCharts() {
      this.trendChart = echarts.init(this.$refs.trendChart)
      this.typeChart = echarts.init(this.$refs.typeChart)
    },
    renderTrendChart(data) {
      const dates = data.map(item => item.date)
      const counts = data.map(item => item.count)
      this.trendChart.setOption({
        tooltip: { trigger: 'axis' },
        xAxis: {
          type: 'category',
          data: dates,
          axisTick: { show: false }
        },
        yAxis: {
          type: 'value',
          name: '借阅数量',
          minInterval: 1
        },
        grid: { left: 40, right: 20, bottom: 60, top: 20 },
        series: [{
          data: counts,
          type: 'line',
          smooth: true,
          symbol: 'circle',
          symbolSize: 6,
          lineStyle: { color: '#2563eb', width: 2 },
          areaStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: 'rgba(37, 99, 235, 0.3)' },
              { offset: 1, color: 'rgba(37, 99, 235, 0.05)' }
            ])
          }
        }]
      })
    },
    renderTypeChart(data) {
      if (!data.length) return
      const types = data.map(item => item.type || '未分类')
      const counts = data.map(item => item.count)
      this.typeChart.setOption({
        tooltip: {
          trigger: 'item',
          formatter: '{b} : {c} 次 ({d}%)'
        },
        series: [{
          type: 'pie',
          radius: ['40%', '70%'],
          center: ['50%', '55%'],
          label: { show: true, formatter: '{b}\n{d}%' },
          data: data.map(item => ({
            name: item.type || '未分类',
            value: item.count
          })),
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }]
      })
    }
  }
}
</script>

<style scoped>
.stat-card {
  text-align: center;
  padding: 10px 0;
}
.stat-value {
  font-size: 36px;
  font-weight: bold;
}
.stat-label {
  font-size: 14px;
  color: #909399;
  margin-top: 8px;
}
</style>
