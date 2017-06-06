'use strict'
// Vue
import Vue from 'vue'
// Vue directives
import highlightjs from '../vue/directives/highlightjs/highlightjs'
import highcharts from '../vue/directives/highcharts/highcharts'
// Vue components
import bubble from '../components/bubble.vue'
import problemPresenter from '../components/problem-presenter.vue'
import chart from '../components/chart.vue'
import timeSummary from '../components/time-summary.vue'
// Vuex
import store, { MUT_STORE_CALL } from '../vue/vuex/store'
// External libs
import axios from 'axios'

// Configure Vue
Vue.directive('highlightjs', highlightjs)
Vue.directive('highcharts', highcharts)

export default {
  components: {
    bubble,
    problemPresenter,
    chart,
    timeSummary
  },
  data () {
    return {
      loading: false,
      error: false
    }
  },
  store,
  beforeCreate () {
    // Manage a loading state
    axios.interceptors.request.use((config) => {
      this.loading = true
      return config
    }, (error) => {
      return Promise.reject(error)
    })
    axios.interceptors.response.use((response) => {
      this.loading = false
      return response
    }, (error) => {
      this.loading = false
      return Promise.reject(error)
    })
  },
  methods: {
    makeTheCall () {
      axios
        .get(
          `/proxy/demo/fetch-cyrils/${store.state.solutions.iterating}/${store.state.solutions.appending}`
        )
        .then((response) => {
          store.commit(MUT_STORE_CALL, response.data)
        })
        .catch(() => {
          this.error = true
        })
    }
  }
}
