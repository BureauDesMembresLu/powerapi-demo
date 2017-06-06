'use strict'

const gulp = require('gulp')

const wpDevConfig = require('./webpack.prod.conf')
const wpPrdConfig = require('./webpack.prod.conf')
const scripts = require('./webpack.scripts')

const clean = require('./clean').clean
const server = require('./server').server
const eslint = require('./eslint')

exports.clean = gulp.series(clean)
exports['serve:dev'] = server
exports['build:dev'] = gulp.series(clean, scripts(wpDevConfig))
exports.build = gulp.series(clean, scripts(wpPrdConfig))
exports['eslint:lint'] = eslint.lint
exports['eslint:fix'] = eslint.fix

gulp.task('default', exports.build)
