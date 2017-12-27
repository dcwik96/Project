import Vue from 'vue'
const state =
  {
    username: '',
  }

const getters = {
  getUsername: state => {
    return state.username
  }
}

const actions = {
  setUsername: ({commit}, payload) => {
    commit('mutateUsername', payload)
  }
}

const mutations = {
  // mutateUsername: (state) => {
  //   Vue.http.get('http://localhost:8080/aboutMe')
  //     .then((response) => {
  //       console.log(response.data)
  //       state.username = response.data
  //     },
  //     e => {
  //       console.log(e)
  //     })
  // }
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
