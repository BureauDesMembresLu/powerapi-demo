'use strict'
// Vue components
import solutionChooser from '../solution-chooser.vue'
import solutionMemory from '../solution-memory.vue'
import codePresenter from '../code-presenter.vue'
// Vuex
import { mapState } from 'vuex'
import { MUT_STORE_CALL, MUT_WIPE_CALLS } from '../../vue/vuex/store'
// External libs
import axios from 'axios'

export default {
  components: {
    solutionChooser,
    solutionMemory,
    codePresenter
  },
  computed: mapState({
    loading: (state) => state.loading
  }),
  methods: {
    testSolution () {
      axios
        .get(
          `/proxy/demo/fetch-cyrils/${this.$store.state.solutions.iterating}/${this.$store.state.solutions.appending}`
        )
        .then((response) => {
          this.$store.commit(MUT_STORE_CALL, response.data)
        })
    },
    wipeTests () {
      this.$store.commit(MUT_WIPE_CALLS)
    }
  }
}
