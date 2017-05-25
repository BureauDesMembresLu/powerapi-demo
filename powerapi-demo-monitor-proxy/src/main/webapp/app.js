import Vue from 'vue'
import App from './app/app'

Vue.config.productionTip = false

window.onload = function () {
  // Load the main Vue
  // eslint-disable-next-line no-new
  new Vue({
    el: '#app',
    template: '<app/>',
    components: {App}
  })
}
