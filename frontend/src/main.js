import Vue from 'vue'
import App from './App'
import VueRouter from 'vue-router'
import { routes } from './router/index'
import VModal from 'vue-js-modal'
import BootstrapVue from 'bootstrap-vue/dist/bootstrap-vue.esm';
import 'bootstrap-vue/dist/bootstrap-vue.css';
import 'bootstrap/dist/css/bootstrap.css';

Vue.use(VModal)
Vue.use(VueRouter)
Vue.use(BootstrapVue);

const Plugin = {
  install (Vue, options = {}) {
    /**
     * Makes sure that plugin can be insstalled only once
     */
    if (this.installed) {
      return
    }

    this.installed = true
    this.event = new Vue()
    /**
     * Plugin API
     */
    Vue.prototype.$modal = {
      show (name, params) {
        Plugin.event.$emit('toggle', name, true, params)
      },

      hide (name, params) {
        Plugin.event.$emit('toggle', name, false, params)
      },

      toggle (name, params) {
        Plugin.event.$emit('toggle', name, undefined, params)
      }
    }}}

const router = new VueRouter({
  routes,
  mode: 'history'
})


Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  render: h => h(App)
})
