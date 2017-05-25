<template>
    <div class="time-summary">
        <span class="worst">Worst: {{ worst.time | time }}</span>
        &mdash;
        <span class="best">Best: {{ best.time | time }}</span>
        &mdash;
        <span class="last bold">Last: {{ last.time | time }}</span>
    </div>
</template>

<script>
  import { mapState } from 'vuex'

  import { time } from '../../js/filters'

  export default {
    filters: {time},
    computed: mapState({
      worst: (state) => state.calls.time.worst,
      best: (state) => state.calls.time.best,
      last: (state) => state.calls.time.last
    })
  }
</script>

<style lang="scss" scoped>
    @import '../../scss/variables';

    .time-summary {
        text-align: center;
        font-size: smaller;

        .bold {
            font-weight: bold;
        }

        @each $type, $color in $colors {
            .#{$type} {
                color: $color;
            }
        }
    }
</style>
