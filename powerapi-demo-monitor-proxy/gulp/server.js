'use strict'

const gulp = require('gulp')
const Browser = require('browser-sync')
const webpack = require('webpack')
const webpackDevMiddleware = require('webpack-dev-middleware')
const webpackHotMiddleware = require('webpack-hot-middleware')
const webpackConfig = require('./webpack.hot.conf')

const browser = Browser.create()
const bundler = webpack(webpackConfig)

exports.server = function () {
  const config = {
    server: 'site',
    middleware: [
      webpackDevMiddleware(bundler, {
        publicPath: webpackConfig.output.publicPath,
        quiet: true
      }),
      webpackHotMiddleware(bundler)
    ]
  }

  browser.init(config)

  gulp.watch(config.app + '**/*').on('change', () => browser.reload())
}
