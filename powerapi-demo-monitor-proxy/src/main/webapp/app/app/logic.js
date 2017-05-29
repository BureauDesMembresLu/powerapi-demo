import axios from 'axios'

import problemPresenter from '../components/problem-presenter.vue'
import lineChart from '../components/line-chart.vue'
import timeSummary from '../components/time-summary.vue'

import store, { MUT_STORE_CALL } from '../config/store'

export default {
  components: {
    problemPresenter,
    lineChart,
    timeSummary
  },
  store,
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
          alert(`An error occurred`)
        })
    }
  }
}
