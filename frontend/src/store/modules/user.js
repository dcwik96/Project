import Vue from 'vue'
import toasted from 'vue-toasted'

Vue.use(toasted);

const state =
  {
    username: '',
  };

const getters = {
  getUsername: state => {
    return state.username
  }
}

const actions = {
  setUsername: ({commit}, payload) => {
    commit('mutateUsername', payload)
  },
  register: (userDetails) => {
    const url = 'http://localhost:8080/registration'
    var config = {
      position: 'bottom-center',
      singleton: true,
      duration: 1500
    };
    if (userDetails.password !== this.password) {
      Vue.toasted.error("Wprowadzone hasła nie są identyczne.", config);
      return
    }
    Vue.http.post(url, this.userDetails, {
      emulateJSON: true
    })
      .then(() => {
        Vue.toasted.success('Twoje konto zostało założne możesz przejść do logwania.', config)
      }, response => {
        Vue.toasted.error(response.bodyText, config)
      })
  },
  login: ({commit},userData) => {
    var config = {
      position: 'bottom-center',
      singleton: true,
      duration: 1500
    };
    const url = 'http://localhost:8080/login';
    Vue.http.post(url, userData, {
      emulateJSON: true
    })
      .then(() => {
        Vue.cookie.set('login', userData.username, 1);
        commit('mutateUsername', userData.username);
      }, () => {
        Vue.toasted.error("Nieprawidłowy login lub hasło", config);
      })
  }
}

const mutations = {
  mutateUsername(state, payload) {
    state.username = payload
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
