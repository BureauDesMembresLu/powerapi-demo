<template>
    <div class="flex-container">
        <div class="col flex">
            <problem-presenter></problem-presenter>
            <button @click="makeTheCall">Call the service</button>
        </div>
        <div class="col flex">
            <time-summary></time-summary>
            <line-chart chart-title="Energy consumption" y-axis="Power (W)"></line-chart>
        </div>
    </div>
</template>

<script>
  import axios from 'axios'

  import problemPresenter from './components/problem-presenter'
  import lineChart from './components/line-chart'
  import timeSummary from './components/time-summary'

  import store, { MUT_STORE_CALL } from '../js/store'

  export default {
    components: {
      problemPresenter,
      lineChart,
      timeSummary
    },
    store,
    methods: {
      makeTheCall () {
        axios
          .get(
            `/proxy/demo/fetch-cyrils/${store.state.solutions.iterating}/${store.state.solutions.appending}`
          )
          .then((response) => {
            store.commit(MUT_STORE_CALL, response.data)
          })
          .catch(() => {
            alert(`An error occurred`)
          })
      }
    }
  }
</script>

<style lang="scss">
    //@import 'normalize.css/normalize';
    @import '../scss/variables';

    body {
        font-family: Helvetica, Verdana, sans-serif;
    }

    .flex-container {
        display: flex;
        justify-content: space-around;

        &.vertical {
            flex-direction: column;
        }
        .flex {
            flex: 1 1 0;
            //&.flex-content {
            //  flex-basis: auto;
            //}
            //&.flex-no-grow {
            //  flex-grow: 0;
            //}
            //@for $i from 2 through 5 {
            //  .flex-grow-#{$i} {
            //    flex-grow: #{$i};
            //  }
            //}
        }
    }

    #app {
        padding: $app-padding-vertical $app-padding-horizontal;

        .col {
            padding: $cols-padding;
        }
    }
</style>
