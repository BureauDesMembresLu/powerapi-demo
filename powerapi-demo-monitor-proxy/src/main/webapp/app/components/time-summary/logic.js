import { mapState } from 'vuex'

import { time } from '../../config/filters'

export default {
  filters: {time},
  computed: mapState({
    worst: (state) => state.calls.time.worst,
    best: (state) => state.calls.time.best,
    last: (state) => state.calls.time.last
  })
}
