<template>
  <div class="dashboard-container">
    <panel-group />
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="16">
        <el-card shadow="hover">
          <div slot="header">
            <span>近7天借阅趋势</span>
          </div>
          <line-chart />
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <div slot="header">
            <span>热门图书 TOP10</span>
          </div>
          <pie-chart />
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="8">
        <el-card shadow="hover">
          <div slot="header">
            <span>本月罚款收入</span>
          </div>
          <bar-chart />
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-card shadow="hover">
          <div slot="header">
            <span>各类型图书分布</span>
          </div>
          <raddar-chart />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import PanelGroup from './PanelGroup'
import LineChart from './LineChart'
import PieChart from './PieChart'
import BarChart from './BarChart'
import RaddarChart from './RaddarChart'

export default {
  name: 'Dashboard',
  components: {
    PanelGroup,
    LineChart,
    PieChart,
    BarChart,
    RaddarChart
  }
}
</script>

<style lang="scss" scoped>
.dashboard-container {
  padding: 20px;
}
</style>
