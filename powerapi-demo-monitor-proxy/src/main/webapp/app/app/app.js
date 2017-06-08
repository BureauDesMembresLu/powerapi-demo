'use strict'
// Vue
import Vue from 'vue'
// Vue directives
import highlightjs from '../vue/directives/highlightjs/highlightjs'
import highcharts from '../vue/directives/highcharts/highcharts'
// Vue components
import bubble from '../components/bubble.vue'
import solutionTester from '../components/solution-tester.vue'
import chart from '../components/chart.vue'
import timeSummary from '../components/time-summary.vue'
// Vue filters
import { firstLetter, capitalize } from '../vue/filters'
// Vuex
import { mapState } from 'vuex'
import store, { MUT_LOADING_STARTED, MUT_LOADING_DONE } from '../vue/vuex/store'
// External libs
import axios from 'axios'

// Configure Vue
Vue.directive('highlightjs', highlightjs)
Vue.directive('highcharts', highcharts)
Vue.filter('firstLetter', firstLetter)
Vue.filter('capitalize', capitalize)

export default {
  components: {
    bubble,
    solutionTester,
    chart,
    timeSummary
  },
  computed: mapState({
    loading: (state) => state.loading,
    error: (state) => state.error
  }),
  store,
  beforeCreate () {
    // Manage a loading state
    axios.interceptors.request.use((config) => {
      store.commit(MUT_LOADING_STARTED)
      return config
    }, (error) => {
      store.commit(MUT_LOADING_DONE, false)
      return Promise.reject(error)
    })
    axios.interceptors.response.use((response) => {
      store.commit(MUT_LOADING_DONE, true)
      return response
    }, (error) => {
      store.commit(MUT_LOADING_DONE, false)
      return Promise.reject(error)
    })
  }
}
