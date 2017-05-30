'use strict'

import axios from 'axios'

import bubble from '../components/bubble.vue'
import problemPresenter from '../components/problem-presenter.vue'
import chart from '../components/chart.vue'
import timeSummary from '../components/time-summary.vue'

import store, { MUT_STORE_CALL } from '../config/store'

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
          this.error = true;
        })
    }
  }
}
