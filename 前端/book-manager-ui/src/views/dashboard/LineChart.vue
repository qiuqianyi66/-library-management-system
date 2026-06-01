<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import * as echarts from 'echarts'
require('echarts/theme/macarons')
import resize from './mixins/resize'
import { getBorrowTrend } from '@/api/dashboard'

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '350px'
    }
  },
  data() {
    return {
      chart: null
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
    this.fetchData()
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    fetchData() {
      getBorrowTrend().then(res => {
        const data = res.data || []
        this.setOptions(data)
      }).catch(() => {
        this.setOptions([])
      })
    },
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
    },
    setOptions(data) {
      const dates = data.map(item => item.date)
      const counts = data.map(item => item.count)
      this.chart.setOption({
        xAxis: {
          type: 'category',
          data: dates,
          boundaryGap: false,
          axisTick: {
            show: false
          }
        },
        grid: {
          left: 10,
          right: 20,
          bottom: 20,
          top: 30,
          containLabel: true
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          },
          padding: [5, 10]
        },
        yAxis: {
          type: 'value',
          name: '借阅数量',
          axisTick: {
            show: false
          }
        },
        series: [{
          name: '借阅量',
          type: 'line',
          smooth: true,
          symbol: 'circle',
          symbolSize: 8,
          itemStyle: {
            color: '#2563eb'
          },
          lineStyle: {
            color: '#2563eb',
            width: 3
          },
          areaStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: 'rgba(37, 99, 235, 0.3)' },
              { offset: 1, color: 'rgba(37, 99, 235, 0.05)' }
            ])
          },
          data: counts,
          animationDuration: 2000,
          animationEasing: 'cubicInOut'
        }]
      })
    }
  }
}
</script>
