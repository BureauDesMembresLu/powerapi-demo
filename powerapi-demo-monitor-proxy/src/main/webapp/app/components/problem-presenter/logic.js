import solutionChooser from '../solution-chooser.vue'

import { APPENDING_SOLUTIONS, ITERATING_SOLUTIONS } from '../../config/store'

export default {
  components: {'solution-chooser': solutionChooser},
  data () {
    return {
      appendingSolutions: APPENDING_SOLUTIONS,
      iteratingSolutions: ITERATING_SOLUTIONS
    }
  }
}
