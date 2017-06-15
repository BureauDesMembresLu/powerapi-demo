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
// Vue components
import solutionChooser from '../solution-chooser.vue'
import solutionMemory from '../solution-memory.vue'
import codePresenter from '../code-presenter.vue'
// Vuex
import { mapState } from 'vuex'
import { MUT_STORE_CALL, MUT_WIPE_CALLS, SOLUTION_IMPL } from '../../vue/vuex/store'
// External libs
import axios from 'axios'
// App
import appConfig from '../../config/config'

export default {
  components: {
    solutionChooser,
    solutionMemory,
    codePresenter
  },
  computed: mapState({
    loading: (state) => state.loading
  }),
  methods: {
    testSolution () {
      axios
        .get(
          `/proxy/${appConfig.demo.iterations}/demo/hanoi/${appConfig.demo.hanoiSize}/${this.$store.state.solutions[SOLUTION_IMPL]}`
        )
        .then((response) => {
          this.$store.commit(MUT_STORE_CALL, response.data)
        })
    },
    wipeTests () {
      this.$store.commit(MUT_WIPE_CALLS)
    }
  }
}
