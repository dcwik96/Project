import Vue from 'vue'
import toasted from 'vue-toasted'
import axios from 'axios'
Vue.use(toasted);

const state =
  {
    username: '',
  };

const getters = {
  getUsername: state => {
    return state.username
  }
};

const actions = {
  setUsername: ({commit}, payload) => {
    commit('mutateUsername', payload)
  },
  login: ({commit},userData) => {
    const config = {
      position: 'bottom-center',
      singleton: true,
      duration: 1500
    };

    let data = JSON.stringify({
      username: userData.username,
      password: userData.password});

    axios.post('login', data)
      .then(() => {
        Vue.cookie.set('login', userData.username, 1);
        router.go({name: 'home'});
        commit('mutateUsername', userData.username)
      })
      .catch ((e) => {
        Vue.toasted.error("Nieprawidłowy login lub hasło", config)
      })
  },
  logout: ({commit}) => {
    axios.get('logout')
      .then(() => {
          Vue.cookie.delete('login');
          router.go({name: 'home'})
        })
      .catch(
        (e) => {
          console.log(e)
        })
  }
};

const mutations = {
  mutateUsername(state, payload) {
    state.username = payload
  }
};

export default {
  state,
  getters,
  actions,
  mutations
}
