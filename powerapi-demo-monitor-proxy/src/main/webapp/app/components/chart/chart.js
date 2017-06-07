'use strict'
// Vuex
import { mapState } from 'vuex'

import appConfig from '../../config/config'

export default {
  props: {
    title: {type: String, required: true},
    icon: {type: String, required: false},
    yAxis: {type: String, required: true},
    chartData: {type: String, required: true}
  },
  computed: mapState({
    chartConfig (state) {
      return {
        title: this.title,
        icon: this.icon,

        xAxis: {title: {text: `Time (${appConfig.monitoring.unit})`}, min: 0, softMax: 10},
        yAxis: {title: {text: this.yAxis}, min: 0},
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
