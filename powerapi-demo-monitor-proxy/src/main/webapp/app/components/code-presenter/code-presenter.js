'use strict'
// Vuex
import { mapState } from 'vuex'
import { PB_APPENDING, PB_ITERATING, SOLUTIONS } from '../../vue/vuex/store'

export default {
  data () {
    return {
      iteratingSolutions: SOLUTIONS[PB_ITERATING],
      appendingSolutions: SOLUTIONS[PB_APPENDING]
    }
  },
  computed: mapState({
    loading: (state) => state.loading,
    iteratingSolution: (state) => state.solutions[PB_ITERATING],
    appendingSolution: (state) => state.solutions[PB_APPENDING]
  })
}
