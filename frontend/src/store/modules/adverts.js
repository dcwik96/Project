import Vue from 'vue'
const state =
  {
    adverts: []
  }

const getters = {
  getArrayOfAdverts: state =>  {
    return state.adverts
  }
}

const actions = {
  getAdverts: ({commit}) => {
    commit('setAdvertsArray')
  },
  enableInput: ({commit},payload) => {
    commit('toggleShowInputTrue', payload)
  },
  disableInput: ({commit},payload) => {
    commit('toggleShowInputFalse', payload)
  }
}

const mutations = {
  setAdvertsArray(state) {
    Vue.http.get('http://localhost:8080/api/adverts')
      .then(response => {
        state.adverts = response.data
      }, e => {
        console.log(e)
      })
    state.adverts.forEach(advert => {
      advert.showInput = true
    })
  },
  toggleShowInputFalse(state, payload) {
    state.adverts[payload].showInput = false;
  },
  toggleShowInputTrue(state, payload) {
    state.adverts[payload].showInput = true;
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
