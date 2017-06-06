'use strict'
// Vuex
import { mapState } from 'vuex'
import { MUT_CHANGE_SOLUTION, SOLUTIONS } from '../../vue/vuex/store'

export default {
  data() {
    return {
      solutions: []
    }
  },
  props: {
    problem: {type: String, required: true}
  },
  computed: mapState({
    selected (state) {
      return state.solutions[this.problem]
    }
  }),
  beforeMount() {
    this.solutions = SOLUTIONS[this.problem]
  },
  methods: {
    selectSolution (event) {
      this.$store.commit(MUT_CHANGE_SOLUTION, {
        problem: this.problem,
        solution: event.currentTarget.dataset.solution
      })
    }
  }
}
