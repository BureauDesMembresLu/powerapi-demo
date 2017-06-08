'use strict'
// Vuex
import { mapState } from 'vuex'

import appConfig from '../../config/config'

// Highcharts workaround
import highcharts from '../../vue/directives/highcharts/highcharts'

export default {
  computed: mapState({
    chartConfig (state) {
      return {
        title: `Execution time`,
        icon: `time`,

        xAxis: {title: {text: `Execution`}},
        yAxis: {title: {text: `Time (${appConfig.monitoring.unit})`}, min: 0, softMax: 10},
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
  }),
  mounted () {
    highcharts.bind(this.$el, {arg: `bar`, value: this.chartConfig})
    this.$watch(() => this.chartConfig, () => {
      highcharts.componentUpdated(this.$el, {arg: `line`, value: this.chartConfig})
    })
  }
}
