'use strict'

module.exports = {
  env: {
    development: '"development"',
    testing: '"test"',
    production: '"production"'
  },
  app: 'src/main/webapp/',
  assets: {
    root: 'target/classes/public/',
    subDirectory: 'static'
  },
  test: 'src/test/javascript/',
  productionSourceMap: true
}
