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
    commit('toggleShowInput', payload)
  },
  disableInput: ({commit},payload) => {
    commit('toggleShowInput', payload)
  }
}

const mutations = {
  setAdvertsArray(state) {
    Vue.http.get('http://localhost:8080/api/adverts')
      .then(response => {
        state.adverts = response.data
        state.adverts.forEach(advert => {
          advert.showInput = true
        })
      }, e => {
        console.log(e)
      })

  },
  toggleShowInput(state, payload) {
    state.adverts[payload].showInput = !state.adverts[payload].showInput;
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
