'use strict'

const gulp = require('gulp')
const gulpIf = require('gulp-if')
const runSequence = require('run-sequence')
const plumber = require('gulp-plumber')
const del = require('del')
const eslint = require('gulp-eslint')
const friendlyFormatter = require('eslint-friendly-formatter')
const webpack = require('webpack')

const util = require('./gulp/gulp-utils')
const webpackPrdConfig = require('./gulp/webpack.prod.conf')
const webpackDevConfig = require('./gulp/webpack.dev.conf')

const config = require('./gulp/config')

gulp.task('clean', function () {
  return del([config.assets.root], {dot: true})
})

gulp.task('js', function (callback) {
  webpack(webpackPrdConfig, util.webpackCallback(callback))
})

gulp.task('build', ['clean'], function (callback) {
  runSequence(['js'], callback)
})

gulp.task('js:dev', function (callback) {
  webpack(webpackDevConfig, util.webpackCallback(callback))
})

gulp.task('build:dev', ['clean'], function (callback) {
  runSequence(['js:dev'], callback)
})

gulp.task('watch:dev', ['build:dev'], function () {
  gulp.watch(config.app + '**/*', ['js:dev'])
})

// check app for eslint errors
gulp.task('eslint', function () {
  return gulp.src(['gulpfile.js', 'gulp/*.js', config.app + '**/*.js'])
    .pipe(plumber({errorHandler: util.handleErrors}))
    .pipe(eslint())
    .pipe(eslint.format(friendlyFormatter))
    .pipe(eslint.failOnError())
})

// check app for eslint errors anf fix some of them
gulp.task('eslint:fix', function () {
  return gulp.src(config.app + '**/*.js')
    .pipe(plumber({errorHandler: util.handleErrors}))
    .pipe(eslint({fix: true}))
    .pipe(eslint.format(friendlyFormatter))
    .pipe(gulpIf(util.isLintFixed, gulp.dest(config.app)))
})

gulp.task('default', ['build'])
