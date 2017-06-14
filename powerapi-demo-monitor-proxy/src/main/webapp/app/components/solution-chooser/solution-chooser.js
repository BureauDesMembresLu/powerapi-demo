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
// Vuex
import { mapState } from 'vuex'
import { MUT_CHANGE_SOLUTION, SOLUTIONS } from '../../vue/vuex/store'

export default {
  data () {
    return {
      solutions: []
    }
  },
  props: {
    problem: {type: String, required: true},
    icon: {type: String, required: true}
  },
  computed: mapState({
    selected (state) {
      return state.solutions[this.problem]
    }
  }),
  beforeMount () {
    this.solutions = SOLUTIONS[this.problem]
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
