import { MUT_CHANGE_SOLUTION } from '../../config/store'

export default {
  props: {
    problem: {type: String, required: true},
    solutions: {type: Array, required: true}
  },
  computed: {
    selected () {
      return this.$store.state.solutions[this.problem]
    }
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
