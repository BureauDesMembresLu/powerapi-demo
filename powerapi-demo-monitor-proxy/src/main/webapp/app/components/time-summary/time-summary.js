/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
        yAxis: {title: {text: `Time (${appConfig.monitoring.unit})`}, min: 0, softMax: appConfig.chart.timeSoftMax},
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
