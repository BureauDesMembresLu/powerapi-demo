'use strict'

const del = require('del')

const config = require('./config')

exports.clean = function () {
  return del([config.assets.root], {dot: true})
}
