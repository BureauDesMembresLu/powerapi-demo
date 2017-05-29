const webpack = require('webpack')

module.exports = (config) => function scripts () {

  return new Promise(resolve => webpack(config, (err, stats) => {

    if (err) {
      console.log('Webpack', err)
    }

    console.log(stats.toString({
      colors: true,
      modules: false,
      children: false,
      chunks: false,
      chunkModules: false
    }))

    resolve()
  }))
}
