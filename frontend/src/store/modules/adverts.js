import Vue from 'vue'
import toasted from 'vue-toasted'

Vue.use(toasted)
const state =
  {
    adverts: [],
    lightAdverts: [],
    advert: {},
    randomAdvert: {}
  };

const getters = {
  getArrayOfAdverts: state => {
    return state.adverts
  },
  getArrayOfLightAdverts: state => {
    return state.lightAdverts
  },
  getAdvert: state => {
    return state.advert
  },
  getRandomAdvert: state => {
    return state.randomAdvert
  }
};

const actions = {
  enableInput: ({commit}, payload) => {
    commit('toggleShowInput', payload)
  },
  disableInput: ({commit}, payload) => {
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
  fetchLightData: ({commit}) => {
    const url = 'http://localhost:8080/api/lightAdverts';
    var tempAdverts = [];
    Vue.http.get(url).then(
      response => {
        tempAdverts = response.data;
        Array.from(tempAdverts).forEach(advert => {
          advert.showInput = true
        });
        commit('setLightAdvertsArray', tempAdverts)
      },
      response => {
        console.log(response)
      }
    )
  },
  fetchAdvert: ({commit}, id) => {
    const url = 'http://localhost:8080/api/oneadvert/' + id;
    Vue.http.get(url, {credentials: true}).then(
      response => {
        commit('setAdvert', response.data)
      },
      response => {
        console.log(response)
      }
    )
  },
  makeOffer: (payload, data) => {
    var config = {
      position: 'bottom-center',
      singleton: true,
      duration: 1000
    };
    const url = 'http://localhost:8080/api/advert/' + data.id + '/newOffer';
    var offerData = {
      offer: data.price
    };

    Vue.http.post(url, offerData, {
      emulateJSON: true,
      emulateHTTP: true,
      credentials: true
    })
      .then(() => {
        Vue.toasted.success('Twoja oferta została złożona', config)
      }, () => {
        Vue.toasted.error('Wystąpił problem', config)
      })
  },
  fetchRandomAdvert: ({commit}) => {
    const url = 'http://localhost:8080/api/randomAdvert';
    Vue.http.get(url, {credentials: false}).then(
      response => {
        commit('setRandomAdvert', response.data)
      },
      response => {
        console.log(response)
      }
    )
  }
};

const mutations = {
  setAdvertsArray(state, payload) {
    state.adverts = payload
  },
  setLightAdvertsArray(state, payload) {
    state.lightAdverts = payload
  },
  toggleShowInput(state, payload) {
    state.lightAdverts[payload].showInput = !state.lightAdverts[payload].showInput
  },
  setAdvert(state, payload) {
    state.advert = payload
  },
  setRandomAdvert(state, payload) {
    state.randomAdvert = payload
  }
};

export default {
  state,
  getters,
  actions,
  mutations
}
