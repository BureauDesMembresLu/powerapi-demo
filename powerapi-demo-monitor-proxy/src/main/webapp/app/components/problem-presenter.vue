<template>
    <div>
        <solution-chooser problem="iterating"
                          :solutions="iteratingSolutions"></solution-chooser>
        <solution-chooser problem="appending"
                          :solutions="appendingSolutions"></solution-chooser>
    </div>
</template>

<script>
  import solutionChooser from './solution-chooser'

  import { APPENDING_SOLUTIONS, ITERATING_SOLUTIONS } from '../../js/store'

  export default {
    components: {'solution-chooser': solutionChooser},
    data () {
      return {
        appendingSolutions: APPENDING_SOLUTIONS,
        iteratingSolutions: ITERATING_SOLUTIONS
      }
    }
  }
</script>

<style lang="scss" scoped></style>
