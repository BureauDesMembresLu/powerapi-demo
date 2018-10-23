const webpack = require('webpack')
const merge = require('webpack-merge')
const devConfig = require('./webpack.dev.conf')

module.exports = merge(devConfig, {
  entry: {
    hmr: [
      'webpack/hot/dev-server',
      'webpack-hot-middleware/client'
    ],
    mock: './mock/server.js'
  },
  plugins: [
    new webpack.HotModuleReplacementPlugin()
  ]
})
