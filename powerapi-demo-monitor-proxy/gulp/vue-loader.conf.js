const utils = require('./vue-utils')
const config = require('./config')
const isProduction = process.env.NODE_ENV === 'production'

module.exports = {
  loaders: utils.cssLoaders({
    sourceMap: isProduction ? config.productionSourceMap : false,
    extract: isProduction
  })
}
