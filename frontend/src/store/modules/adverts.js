import Vue from 'vue'
import toasted from 'vue-toasted'
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
  enableInput: ({commit},payload) => {
    commit('toggleShowInput', payload)
  },
  disableInput: ({commit},payload) => {
    commit('toggleShowInput', payload)
  },
  fetchData: ({commit}) => {
    const url = 'http://localhost:8080/api/adverts'
    var tempAdverts = []
    Vue.http.get(url).then(
      response => {
        tempAdverts = response.data
        Array.from(tempAdverts).forEach(advert => {
          advert.showInput = true
        })
        commit('setAdvertsArray', tempAdverts)
      },
      response => {
        console.log(response)
      }
    )
  },
  makeOffer: (payload, data) => {
    var config = {
      position : 'bottom-center',
      singleton: true,
      duration: 1000
    }
    const url = 'http://localhost:8080/api/advert/'+data.id+'/newOffer'
    var offerData = {
      offer: data.price
    }

    console.log(payload)
    Vue.use(toasted)

    Vue.http.post(url, offerData, {
      emulateJSON: true
    })
      .then(() => {
        Vue.toasted.success('Twoja oferta została złożona', config)
      }, response => {
        console.log(response)
        Vue.toasted.error('Wystąpił problem', config)
      })
  }
}

const mutations = {
  setAdvertsArray(state, payload) {
   state.adverts = payload
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
