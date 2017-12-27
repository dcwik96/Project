import Vue from 'vue'
const state =
  {
    adverts: [],
  }

const getters = {
  getArrayOfAdverts: state =>  {
    return state.adverts
  },
}

const actions = {
  getAdverts: ({commit}) => {
    commit('setAdvertsArray')
  },
}

const mutations = {
  setAdvertsArray(state) {
    Vue.http.get('http://localhost:8080/api/adverts')
      .then(response => {
        state.adverts = response.data
      }, e => {
        console.log(e)
      })
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
