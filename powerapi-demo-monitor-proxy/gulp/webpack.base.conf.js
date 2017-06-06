const path = require('path')

const appConfig = require('./config')

const utils = require('./vue-utils')
const vueLoaderConfig = require('./vue-loader.conf')

const resolve = (dir) => path.join(__dirname, '..', dir)

module.exports = {
  context: resolve(appConfig.app),
  entry: {app: './main.js'},
  output: {
    path: resolve(appConfig.assets.root),
    filename: '[name].js'
  },
  resolve: {
    extensions: ['.js', '.vue', '.json'],
    alias: {
      'vue$': 'vue/dist/vue.esm.js',
      '@': resolve(appConfig.app)
    }
  },
  module: {
    rules: [
      {
        test: /\.(js|vue)$/,
        loader: 'eslint-loader',
        enforce: 'pre',
        include: [resolve(appConfig.app), resolve(appConfig.test)],
        options: {
          formatter: require('eslint-friendly-formatter')
        }
      },
      {
        test: /\.html$/,
        loader: 'html-loader'
      },
      {
        test: /\.vue$/,
        loader: 'vue-loader',
        options: vueLoaderConfig
      },
      {
        test: /\.js$/,
        loader: 'babel-loader',
        include: [resolve(appConfig.app), resolve(appConfig.test)]
      },
      {
        test: /\/fonts\/.*\.(woff2?|eot|ttf|otf|svg)(\?.*)?$/,
        loader: 'file-loader',
        options: {
          limit: 10000,
          name: utils.assetsPath('fonts/[name].[hash:7].[ext]')
        }
      },
      {
        test: /\/img\/.*\.(jpg|png)(\?.*)?$/,
        loader: 'file-loader',
        options: {
          limit: 10000,
          name: utils.assetsPath('img/[name].[hash:7].[ext]')
        }
      }
    ]
  }
}
