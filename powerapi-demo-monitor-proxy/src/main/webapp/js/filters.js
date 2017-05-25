'use strict'

import {isNumber} from 'lodash'

const UNIT_JUMP = 1000
const UNIT_S = `s`
const UNIT_MS = `ms`

export const time = (timeInMs) => {
  let result
  if (isNumber(timeInMs)) {
    result = timeInMs > UNIT_JUMP ? `${timeInMs / UNIT_JUMP} ${UNIT_S}` : `${timeInMs} ${UNIT_MS}`
  } else {
    result = ``
  }

  return result
}
