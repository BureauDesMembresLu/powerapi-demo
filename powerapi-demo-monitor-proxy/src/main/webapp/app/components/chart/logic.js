'use strict'
// Vuex
import { mapState } from 'vuex'
// Vue directives
import highcharts from '../../vue/directives/highcharts'

import appConfig from '../../config/config'

export default {
  directives: {
    highcharts
  },
  props: {
    title: {type: String, required: true},
    yAxis: {type: String, required: true},
    chartData: {type: String, required: true}
  },
  computed: mapState({
    chartConfig (state) {
      return {
        title: this.title,

        xAxis: {min: 0, title: {text: `Time (${appConfig.monitoring.unit})`}},
        yAxis: {min: 0, title: {text: this.yAxis}},
        plotOptions: {
          series: {
            pointStart: appConfig.monitoring.interval,
            pointInterval: appConfig.monitoring.interval
          }
        },

        worst: state.calls.worst[this.chartData],
        best: state.calls.best[this.chartData],
        last: state.calls.last[this.chartData]
      }
    }
  })
}
