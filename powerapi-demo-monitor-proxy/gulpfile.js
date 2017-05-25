'use strict';

const gulp = require('gulp'),
    gutil = require('gulp-util'),
    gulpIf = require('gulp-if'),
    runSequence = require('run-sequence'),
    plumber = require('gulp-plumber'),
    del = require('del'),
    eslint = require('gulp-eslint'),
    htmlmin = require('gulp-htmlmin'),
    sass = require('gulp-sass'),
    autoprefixer = require('gulp-autoprefixer'),
    webpack = require('webpack');

const util = require('./gulp/utils'),
    webpackConfig = require('./gulp/webpack.config');

const config = require('./gulp/config');

gulp.task('clean', function () {
    return del([config.dist], {dot: true});
});

gulp.task('html', function () {
    return gulp.src(config.app + '**/*.html')
        .pipe(htmlmin({collapseWhitespace: true}))
        .pipe(gulp.dest(config.dist));
});

gulp.task('js', function (callback) {
    webpack(webpackConfig, function (err, stats) {
        if (err) {
            throw new gutil.PluginError('webpack', err);
        }
        gutil.log('[webpack]', stats.toString({colors: true}));
        callback();
    });
});

gulp.task('scss', function () {
    return gulp.src(config.app + 'scss/*.scss')
        .pipe(sass.sync({
            includePaths: ['node_modules'],
            outputStyle: 'compressed'
        }).on('error', util.handleErrors))
        .pipe(autoprefixer({
            browsers: ['last 2 versions'],
            cascade: false
        }))
        .pipe(gulp.dest(config.dist + 'style'));
});

// check app for eslint errors
gulp.task('eslint', function () {
    return gulp.src(['gulpfile.js', 'gulp/*.js', config.src + '**/*.js'])
        .pipe(plumber({errorHandler: util.handleErrors}))
        .pipe(eslint())
        .pipe(eslint.format())
        .pipe(eslint.failOnError());
});

// check app for eslint errors anf fix some of them
gulp.task('eslint:fix', function () {
    return gulp.src(config.src + '**/*.js')
        .pipe(plumber({errorHandler: util.handleErrors}))
        .pipe(eslint({fix: true}))
        .pipe(eslint.format())
        .pipe(gulpIf(util.isLintFixed, gulp.dest(config.src)));
});

gulp.task('install', function () {
    runSequence('inject:dep', 'inject:app', 'inject:troubleshoot');
});

gulp.task('build', ['clean'], function (callback) {
    runSequence(['html', 'js', 'scss'], callback);
});

gulp.task('watch', function () {
    gulp.watch(config.app, ['build']);
});

gulp.task('default', ['build']);
