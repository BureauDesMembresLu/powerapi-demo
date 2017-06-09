'use strict'

import Vue from 'vue'
import Vuex from 'vuex'

import { assignIn, clone, sum } from 'lodash'

export const PB_ITERATING = `iterating`
export const PB_APPENDING = `appending`

export const SOLUTIONS = Object.freeze({
  [PB_ITERATING]: [
    `METHOD_IN_CONDITION`,
    `METHOD_BEFORE_CONDITION_IPP`,
    `METHOD_BEFORE_CONDITION_PPI`,
    `ITERATOR`,
    `FOREACH`
  ],
  [PB_APPENDING]: [
    `PLAIN_STRING_APPENDING`,
    `STRING_BUFFER`,
    `STRING_BUILDER`
  ]
})

export const MUT_LOADING_STARTED = `setLoadingStarted`
export const MUT_LOADING_DONE = `setLoadingDone`
export const MUT_CHANGE_SOLUTION = `changeSolution`
export const MUT_RESTORE_SOLUTION = `restoreSolution`
export const MUT_STORE_CALL = `storeCall`
export const MUT_WIPE_CALLS = `wipeCall`

Vue.use(Vuex)

const storeBestWorstAndLast = (collection, toStore) => {
  assignIn(collection.last, toStore)
  if (!collection.best.totalPower || toStore.totalPower < collection.best.totalPower) {
    assignIn(collection.best, toStore)
  }
  if (!collection.worst.totalPower || toStore.totalPower > collection.worst.totalPower) {
    assignIn(collection.worst, toStore)
  }
}

const getDefaultCall = () => {
  return {solutions: {}, time: null, power: [], totalPower: undefined}
}
const getDefaultCalls = () => {
  return {
    worst: getDefaultCall(),
    best: getDefaultCall(),
    last: getDefaultCall()
  }
}

export default new Vuex.Store({
  debug: true,
  state: {
    loading: false,
    error: false,
    solutions: {
      [PB_ITERATING]: SOLUTIONS[PB_ITERATING][0],
      [PB_APPENDING]: SOLUTIONS[PB_APPENDING][0]
    },
    calls: getDefaultCalls()
  },
  mutations: {
    [MUT_LOADING_STARTED] (state) {
      state.loading = true
      state.error = false
    },
    [MUT_LOADING_DONE] (state, successful) {
      state.loading = false
      state.error = !successful
    },
    [MUT_CHANGE_SOLUTION] (state, payload) {
      state.solutions[payload.problem] = payload.solution
    },
    [MUT_RESTORE_SOLUTION] (state, call) {
      assignIn(state.solutions, state.calls[call].solutions)
    },
    [MUT_STORE_CALL] (state, call) {
      const solutions = clone(state.solutions)

      // Update calls
      storeBestWorstAndLast(state.calls, {
        solutions,
        time: call.processingTime,
        power: call.power,
        totalPower: sum(call.power)
      })
    },
    [MUT_WIPE_CALLS] (state) {
      state.calls = getDefaultCalls()
    }
  }
})
