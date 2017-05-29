'use strict'

const gulp = require('gulp')
const gulpIf = require('gulp-if')
const eslint = require('gulp-eslint')
const plumber = require('gulp-plumber')
const friendlyFormatter = require('eslint-friendly-formatter')

const config = require('./config')
const util = require('./gulp-utils')

exports.lint = function () {
  return gulp.src(['gulp/*.js', config.app + '**/*.js'])
    .pipe(plumber({errorHandler: util.handleErrors}))
    .pipe(eslint())
    .pipe(eslint.format(friendlyFormatter))
    .pipe(eslint.failOnError())
}

exports.fix = function () {
  return gulp.src(config.app + '**/*.js')
    .pipe(plumber({errorHandler: util.handleErrors}))
    .pipe(eslint({fix: true}))
    .pipe(eslint.format(friendlyFormatter))
    .pipe(gulpIf(util.isLintFixed, gulp.dest(config.app)))
}
