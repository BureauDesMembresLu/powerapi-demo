'use strict'

const notify = require('gulp-notify')
const argv = require('yargs').argv

// Has ESLint fixed the content of this file?
let isLintFixed = (file) => file.eslint !== null && file.eslint.fixed

let handleErrors = function () {
  const args = Array.prototype.slice.call(arguments)
  const notification = argv.notification === undefined ? true : argv.notification
  // Send error to notification center with gulp-notify
  if (notification) {
    notify.onError({
      title: 'PowerAPI demo Gulp Build',
      subtitle: 'Failure!',
      message: 'Error: <%= error.message %>',
      sound: 'Beep'
    }).apply(this, args)
  }
  // Keep gulp from hanging on this task
  this.emit('end')
}

module.exports = {
  isLintFixed,
  handleErrors
}
