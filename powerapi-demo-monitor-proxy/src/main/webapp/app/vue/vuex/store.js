/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
'use strict'

import Vue from 'vue'
import Vuex from 'vuex'

import { assignIn, clone, sum } from 'lodash'

export const SOLUTION_IMPL = `implementation`

export const SOLUTIONS = Object.freeze({
  [SOLUTION_IMPL]: [
    `RECURSIVE`,
    `ITERATIVE`,
    `DEMO`
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
      [SOLUTION_IMPL]: SOLUTIONS[SOLUTION_IMPL][0]
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
