'use strict'
// Vue components
import solutionChooser from '../solution-chooser.vue'
import solutionMemory from '../solution-memory.vue'
// Vuex
import { mapState } from 'vuex'
import { MUT_STORE_CALL, PB_APPENDING, PB_ITERATING, SOLUTIONS } from '../../vue/vuex/store'
// External libs
import axios from 'axios'

export default {
  components: {
    solutionChooser,
    solutionMemory
  },
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
  }),
  methods: {
    makeTheCall () {
      axios
        .get(
          `/proxy/demo/fetch-cyrils/${this.$store.state.solutions.iterating}/${this.$store.state.solutions.appending}`
        )
        .then((response) => {
          this.$store.commit(MUT_STORE_CALL, response.data)
        })
    }
  }
}
