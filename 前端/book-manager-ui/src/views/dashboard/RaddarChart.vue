<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import * as echarts from 'echarts'
require('echarts/theme/macarons')
import resize from './mixins/resize'
import { getStats } from '@/api/dashboard'
import request from '@/utils/request'

const animationDuration = 3000

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
      // 获取各类型图书分布
      request({
        url: '/bookmanager/book/list',
        method: 'get',
        params: { pageSize: 999 }
      }).then(res => {
        const books = res.rows || []
        const typeMap = {}
        books.forEach(b => {
          const t = b.type || '未分类'
          typeMap[t] = (typeMap[t] || 0) + 1
        })
        const types = Object.keys(typeMap)
        const counts = types.map(t => typeMap[t])
        this.setOptions(types, counts)
      }).catch(() => {
        this.setOptions(['文学', '科技', '教育', '社科', '艺术'], [0, 0, 0, 0, 0])
      })
    },
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
    },
    setOptions(types, counts) {
      if (!types || !types.length) {
        return
      }
      this.chart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: { type: 'shadow' }
        },
        radar: {
          radius: '66%',
          center: ['50%', '50%'],
          splitNumber: 4,
          indicator: types.map(t => ({ name: t, max: Math.max(...counts, 1) * 1.5 })),
          shape: 'circle'
        },
        series: [{
          type: 'radar',
          data: [{ value: counts, name: '图书数量' }],
          symbolSize: 6,
          areaStyle: {
            color: 'rgba(37, 99, 235, 0.3)'
          },
          lineStyle: {
            color: '#2563eb',
            width: 2
          },
          itemStyle: {
            color: '#2563eb'
          },
          animationDuration: animationDuration
        }]
      })
    }
  }
}
</script>
