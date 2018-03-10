import Vue from 'vue'
import App from './App'
import VueRouter from 'vue-router'
import VueCookie from 'vue-cookie'
import VueResource from 'vue-resource'
import {routes} from './router/index'
import VModal from 'vue-js-modal'
import store from './store'
import Toasted from 'vue-toasted'
import 'bootstrap/dist/css/bootstrap.min.css'
import * as uiv from 'uiv'
import axios from 'axios';

Vue.use(uiv);
Vue.use(VModal);
Vue.use(VueRouter);
Vue.use(VueResource);
Vue.use(VueCookie);
Vue.use(Toasted);
Vue.http.options.credentials = true;
axios.defaults.withCredentials = true;
axios.defaults.baseURL = "http://localhost:8080";
axios.defaults.headers.common['Access-Control-Allow-Origin'] = 'http://localhost:8080';

export const eventBus = new Vue();

const Plugin = {
  install(Vue, options = {}) {

    if (this.installed) {
      return
    }

    this.installed = true;
    this.event = new Vue();
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
};

global.router = new VueRouter({
  routes,
  mode: 'history'
});


Vue.config.productionTip = false;

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App),
});
