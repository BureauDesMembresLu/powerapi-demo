'use strict'

import Vue from 'vue'
import Vuex from 'vuex'

import {assignIn, clone, sum} from 'lodash'

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
  if (!collection.best.value || toStore.value < collection.best.value) {
    assignIn(collection.best, toStore)
  }
  if (!collection.worst.value || toStore.value > collection.worst.value) {
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
      time: {
        worst: {solutions: {}, time: null},
        best: {solutions: {}, time: null},
        last: {solutions: {}, time: null}
      },
      power: {
        worst: {solutions: {}, values: []},
        best: {solutions: {}, values: []},
        last: {solutions: {}, values: []}
      }
    }
  },
  mutations: {
    [MUT_CHANGE_SOLUTION] (state, payload) {
      state.solutions[payload.problem] = payload.solution
    },
    [MUT_STORE_CALL] (state, call) {
      const solutions = clone(state.solutions)

            // Update last data
      state.calls.lastData = call.proxied

            // Update time calls
      const pTime = call.processingTime
      storeBestWorstAndLast(state.calls.time, {
        solutions,
        time: pTime,
        value: pTime
      })

            // Update power consumption
      storeBestWorstAndLast(state.calls.power, {
        solutions,
        values: call.power,
        value: sum(call.power)
      })
    }
  }
})
