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
