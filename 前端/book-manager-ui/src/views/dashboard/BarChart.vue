<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import * as echarts from 'echarts'
require('echarts/theme/macarons')
import resize from './mixins/resize'
import { getIncomeStats } from '@/api/dashboard'

const animationDuration = 2000

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
      default: '300px'
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
      getIncomeStats().then(res => {
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
      // data 可能是 { monthlyFineIncome: 123 } 对象，转为单条柱状图
      const isObject = !Array.isArray(data)
      const days = isObject ? ['本月'] : data.map(item => item.date || item.day)
      const amounts = isObject ? [data.monthlyFineIncome || 0] : data.map(item => item.amount)
      this.chart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          top: 10,
          left: '3%',
          right: '3%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [{
          type: 'category',
          data: days,
          axisTick: {
            alignWithLabel: true
          }
        }],
        yAxis: [{
          type: 'value',
          name: '金额（元）',
          axisTick: {
            show: false
          }
        }],
        series: [{
          name: '罚款金额',
          type: 'bar',
          barWidth: '50%',
          itemStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: '#f59e0b' },
              { offset: 1, color: '#fbbf24' }
            ]),
            borderRadius: [6, 6, 0, 0]
          },
          data: amounts,
          animationDuration
        }]
      })
    }
  }
}
</script>
