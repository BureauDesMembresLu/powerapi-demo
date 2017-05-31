'use strict'

// Vue components
import solutionChooser from '../solution-chooser.vue'
// Vue directives
import highlightjs from '../../vue/highlightjs'

import {
  APPENDING_SOLUTIONS,
  ITERATING_SOLUTIONS,
  KEY_APPENDING_SOLUTION,
  KEY_ITERATING_SOLUTION
} from '../../vue/store'

export default {
  components: {solutionChooser},
  directives: {highlightjs},
  data () {
    return {
      iteratingSolutions: ITERATING_SOLUTIONS,
      appendingSolutions: APPENDING_SOLUTIONS
    }
  },
  computed: {
    iteratingSolution () {
      return this.$store.state.solutions[KEY_ITERATING_SOLUTION]
    },
    appendingSolution () {
      return this.$store.state.solutions[KEY_APPENDING_SOLUTION]
    }
  }
}
