import Vue from 'vue'
import App from './app/app.vue'

Vue.config.productionTip = false

window.onload = function () {
  // Load the main Vue
  // eslint-disable-next-line no-new
  new Vue({
    el: '#app',
    template: '<app></app>',
    components: {App}
  })
}
