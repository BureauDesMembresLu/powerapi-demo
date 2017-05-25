'use strict';

const notify = require('gulp-notify'),
    argv = require('yargs').argv;


let isLintFixed = function (file) {
    // Has ESLint fixed the file contents?
    return file.eslint !== null && file.eslint.fixed;
};

let handleErrors = function () {
    let args = Array.prototype.slice.call(arguments);
    let notification = argv.notification === undefined ? true : argv.notification;
    // Send error to notification center with gulp-notify
    if (notification) {
        notify.onError({
            title: 'PowerAPI demo Gulp Build',
            subtitle: 'Failure!',
            message: 'Error: <%= error.message %>',
            sound: 'Beep'
        }).apply(this, args);
    }
    // Keep gulp from hanging on this task
    this.emit('end');
};

module.exports = {
    isLintFixed,
    handleErrors
};
