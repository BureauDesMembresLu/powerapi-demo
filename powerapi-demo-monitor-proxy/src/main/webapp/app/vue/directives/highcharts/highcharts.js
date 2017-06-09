'use strict'

import Highcharts from 'highcharts'

const SERIES_IDX_WORST = 0
const SERIES_IDX_BEST = 1
const SERIES_IDX_LAST = 2

// FIXME This looks dirty. Is there a way to store data in the directive?
const charts = {}

let chartCounter = 0

export default {
  deep: true,
  bind (el, binding) {
    const config = binding.value
    el.dataset.chartId = ++chartCounter
    charts[chartCounter] = Highcharts.chart(el, {
      chart: {type: binding.arg || `line`},

      title: {
        text: config.icon ? `<span class="pa-${config.icon}"></span> ${config.title}` : config.title,
        useHTML: Boolean(config.icon)
      },

      xAxis: config.xAxis,
      yAxis: config.yAxis,
      legend: false,

      plotOptions: config.plotOptions,

      // FIXME Using hard-coded colors is a problem. Can we make CSS-styling work, please?
      series: [
        {
          name: `Worst`,
          data: config.worst,
          className: `worst`,
          color: `#991f3d`,
          marker: {enabled: false}
        }, {
          name: `Best`,
          data: config.best,
          className: `best`,
          color: `#006600`,
          marker: {enabled: false}
        }, {
          name: `Last`,
          data: config.last,
          className: `last`,
          color: `#f2a200`,
          marker: {enabled: false}
        }
      ]
    })
  },
  componentUpdated (el, binding) {
    // Update charts but redraw only after all series have been updated
    const chart = charts[el.dataset.chartId]
    const config = binding.value
    chart.series[SERIES_IDX_WORST].setData(config.worst, false)
    chart.series[SERIES_IDX_BEST].setData(config.best, false)
    chart.series[SERIES_IDX_LAST].setData(config.last, false)
    chart.redraw()
  }
}