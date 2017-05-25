<template>
    <ul class="solutions">
        <li v-for="(solution, index) in solutions">
            <a :class="{selected: solution === selected}"
               :data-solution="solution"
               @click="selectSolution">Solution {{ index + 1 }}</a>
        </li>
    </ul>
</template>

<script>
  import { MUT_CHANGE_SOLUTION } from '../../js/store'

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
</script>

<style lang="scss" scoped>
    ul.solutions {
        a {
            cursor: pointer;
            &.selected {
                color: darkcyan;
                font-weight: bold;
            }
        }
    }
</style>
