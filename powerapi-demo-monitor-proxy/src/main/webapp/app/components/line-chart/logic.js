import { mapState } from 'vuex'

import Highcharts from 'highcharts'

import config from '../../config/config'

const SERIES_IDX_WORST = 0
const SERIES_IDX_BEST = 1
const SERIES_IDX_LAST = 2

let chartCounter = 0

export default {
  props: {
    chartTitle: {type: String, required: true},
    yAxis: {type: String, required: true}
  },
  data () {
    return {id: `line-chart${++chartCounter}`}
  },
  computed: mapState({
    worst: (state) => state.calls.power.worst.values,
    best: (state) => state.calls.power.best.values,
    last: (state) => state.calls.power.last.values
  }),
  mounted () {
    // Create the empty chart
    const chart = Highcharts.chart(this.id, {
      title: {text: this.chartTitle},

      xAxis: {title: {text: `Time (${config.monitoring.unit})`}},
      yAxis: {title: {text: this.yAxis}},
      legend: {
        layout: `vertical`,
        align: `right`,
        verticalAlign: `middle`
      },

      plotOptions: {
        series: {
          pointStart: config.monitoring.interval,
          pointInterval: config.monitoring.interval
        }
      },

      series: [{
        name: `Worst`,
        data: this.worst,
        className: `worst`,
        color: `#AA0000`,
        marker: {symbol: `square`}
      }, {
        name: `Best`,
        data: this.best,
        className: `best`,
        color: `#006600`,
        marker: {symbol: `triangle`}
      }, {
        name: `Last`,
        data: this.last,
        className: `last`,
        color: `#FF6600`,
        marker: {symbol: `circle`}
      }]
    })

    // Update the chart each time data is updated
    this.$watch(() => this.worst, () => {
      chart.series[SERIES_IDX_WORST].setData(this.worst, true)
    })
    this.$watch(() => this.best, () => {
      chart.series[SERIES_IDX_BEST].setData(this.best, true)
    })
    this.$watch(() => this.last, () => {
      chart.series[SERIES_IDX_LAST].setData(this.last, true)
    })
  }
}
