import Vue from 'vue'
import App from './App'
import VueRouter from 'vue-router'
import VueCookie from 'vue-cookie'
import VueResource from 'vue-resource'
import {routes} from './router/index'
import VModal from 'vue-js-modal'
import store from './store'
import Toasted from 'vue-toasted'

Vue.use(VModal)
Vue.use(VueRouter)
Vue.use(VueResource)
Vue.use(VueCookie)
Vue.use(Toasted)

export const eventBus = new Vue();

const Plugin = {
  install(Vue, options = {}) {

    if (this.installed) {
      return
    }

    this.installed = true
    this.event = new Vue()
    type.$modal = {
      show(name, params) {
        Plugin.event.$emit('toggle', name, true, params)
      },

      hide(name, params) {
        Plugin.event.$emit('toggle', name, false, params)
      },

      toggle(name, params) {
        Plugin.event.$emit('toggle', name, undefined, params)
      }
    }
  }
}

global.router = new VueRouter({
  routes,
  mode: 'history'
})


Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
