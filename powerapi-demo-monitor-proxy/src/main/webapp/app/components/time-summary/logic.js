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
  computed: mapState({
    chartConfig (state) {
      return {
        title: `Execution time`,

        xAxis: {visible: false},
        yAxis: {min: 0, title: {text: `Time (${appConfig.monitoring.unit})`}},
        plotOptions: {
          bar: {
            dataLabels: {
              enabled: true
            }
          }
        },

        worst: [state.calls.worst[`time`] / appConfig.monitoring.ratio],
        best: [state.calls.best[`time`] / appConfig.monitoring.ratio],
        last: [state.calls.last[`time`] / appConfig.monitoring.ratio]
      }
    }
  })
}
