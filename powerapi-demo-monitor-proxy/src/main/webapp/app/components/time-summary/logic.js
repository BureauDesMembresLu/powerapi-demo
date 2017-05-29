import { mapState } from 'vuex'

import { time } from '../../config/filters'

export default {
  filters: {time},
  computed: mapState({
    worst: (state) => state.calls.worst.time,
    best: (state) => state.calls.best.time,
    last: (state) => state.calls.last.time
  })
}
