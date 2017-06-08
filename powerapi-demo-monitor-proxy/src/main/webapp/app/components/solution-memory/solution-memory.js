'use strict'
// Vuex
import { mapState } from 'vuex'
import { MUT_RESTORE_SOLUTION } from '../../vue/vuex/store'

export default {
  computed: mapState({
    solutions: (state) => state.calls
  }),
  methods: {
    restoreSolution (event) {
      this.$store.commit(MUT_RESTORE_SOLUTION, event.currentTarget.dataset.solution)
    }
  }
}
