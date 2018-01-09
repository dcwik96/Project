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
  makeOffer: (id) => {
    var config = {
      position : 'bottom-center',
      singleton: true,
      duration: 1000
    }
    const url = 'http://localhost:8080/api/advert/'+id+'newOffer';
    var formData = new FormData();
    Vue.use(toasted)
    formData.append('offer',this.offer)

    Vue.http.post(url, formData, {
      emulateJSON: true
    })
      .then(() => {
        Vue.toasted.success('Twoja oferta została złożona', config).goAway(100)
      }, response => {
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
