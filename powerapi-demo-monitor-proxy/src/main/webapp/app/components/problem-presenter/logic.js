'use strict'
// Vuex
import  { mapState } from 'vuex'
// Vue components
import solutionChooser from '../solution-chooser.vue'
// Vue directives
import highlightjs from '../../vue/directives/highlightjs'

import { PB_APPENDING, PB_ITERATING, SOLUTIONS } from '../../vue/vuex/store'

export default {
  components: {solutionChooser},
  directives: {highlightjs},
  data () {
    return {
      iteratingSolutions: SOLUTIONS[PB_ITERATING],
      appendingSolutions: SOLUTIONS[PB_APPENDING]
    }
  },
  computed: mapState({
    iteratingSolution: (state) => state.solutions[PB_ITERATING],
    appendingSolution: (state) => state.solutions[PB_APPENDING]
  })
}
