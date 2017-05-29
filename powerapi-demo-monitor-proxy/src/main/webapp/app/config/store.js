'use strict'

import Vue from 'vue'
import Vuex from 'vuex'

import { assignIn, clone, sum } from 'lodash'

export const ITERATING_SOLUTIONS = Object.freeze([
  `METHOD_IN_CONDITION`,
  `METHOD_BEFORE_CONDITION_IPP`,
  `METHOD_BEFORE_CONDITION_PPI`,
  `ITERATOR`,
  `FOREACH`
])

export const APPENDING_SOLUTIONS = Object.freeze([
  `PLAIN_STRING_APPENDING`,
  `STRING_BUFFER`,
  `STRING_BUILDER`
])

export const KEY_ITERATING_SOLUTION = `iterating`
export const KEY_APPENDING_SOLUTION = `appending`

export const MUT_STORE_CALL = `storeCall`
export const MUT_CHANGE_SOLUTION = `changeSolution`

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

export default new Vuex.Store({
  debug: true,
  state: {
    solutions: {
      [KEY_ITERATING_SOLUTION]: ITERATING_SOLUTIONS[0],
      [KEY_APPENDING_SOLUTION]: APPENDING_SOLUTIONS[0]
    },
    calls: {
      worst: {solutions: {}, time: null, power: [], totalPower: undefined},
      best: {solutions: {}, time: null, power: [], totalPower: undefined},
      last: {solutions: {}, time: null, power: [], totalPower: undefined}
    }
  },
  mutations: {
    [MUT_CHANGE_SOLUTION] (state, payload) {
      state.solutions[payload.problem] = payload.solution
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
    }
  }
})
