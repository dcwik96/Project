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

    let data = new FormData();
    data.append('username' ,userData.username);
    data.append('password', userData.password);

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
  logout: () => {
    axios.get('logout')
      .then(() => {
          console.log('Deleting cookie');
          Vue.cookie.delete('login');
          router.go({name: 'home'})
        })
      .catch(
        (e) => {
          console.log(e)
        })
  },
  register: ({commit}, userData) => {
    let config = {
      position: 'bottom-center',
      singleton: true,
      duration: 1500
    };

    let data = new FormData();
    data.append('name', userData.name);
    data.append('surname', userData.surname);
    data.append('login', userData.login);
    data.append('email', userData.email);
    data.append('phone_number', userData.phone_number);
    data.append('password', userData.password);

    axios.post('registration', data)
      .then(() => {
        Vue.toasted.success('Twoje konto zostało założne możesz przejść do logowania.', config)
      })
      .catch((e) => {
        Vue.toasted.error(e.bodyText, config)
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
