<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import * as echarts from 'echarts'
require('echarts/theme/macarons')
import resize from './mixins/resize'
import { getHotBooks } from '@/api/dashboard'

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
      getHotBooks().then(res => {
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
      if (!data.length) {
        return
      }
      const names = data.map(item => item.book_name)
      this.chart.setOption({
        tooltip: {
          trigger: 'item',
          formatter: '{b} : {c} 次 ({d}%)'
        },
        legend: {
          type: 'scroll',
          orient: 'vertical',
          right: 10,
          top: 20,
          bottom: 20,
          data: names
        },
        series: [
          {
            name: '热门图书',
            type: 'pie',
            radius: ['40%', '70%'],
            center: ['40%', '55%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 6,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 14,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: data,
            animationEasing: 'cubicInOut',
            animationDuration: 2000
          }
        ]
      })
    }
  }
}
</script>
