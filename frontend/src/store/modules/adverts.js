import Vue from 'vue'
import toasted from 'vue-toasted'
Vue.use(toasted)
const state =
  {
    adverts: [],
    lightAdverts: [],
    advert: {}
  };

const getters = {
  getArrayOfAdverts: state =>  {
    return state.adverts
  },
  getArrayOfLightAdverts: state =>  {
    return state.lightAdverts
  },
  getAdvert: state => {
    return state.advert
  }
};

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
    const url = 'http://localhost:8080/api/advert/' + id;
    var tempAdverts = {};
    Vue.http.get(url).then(
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
      position : 'bottom-center',
      singleton: true,
      duration: 1000
    };
    const url = 'http://localhost:8080/api/advert/'+data.id+'/newOffer';
    var offerData = {
      offer: data.price
    };

    Vue.http.post(url, offerData, {
      emulateJSON: true,
      credentials: true
    })
      .then(() => {
        Vue.toasted.success('Twoja oferta została złożona', config)
      }, response => {
        console.log(response);
        Vue.toasted.error('Wystąpił problem', config)
      })
  }
}

const mutations = {
  setAdvertsArray(state, payload) {
   state.adverts = payload
  },
  setLightAdvertsArray(state, payload) {
    state.lightAdverts = payload
  },
  toggleShowInput(state, payload) {
    state.adverts[payload].showInput = !state.adverts[payload].showInput
  },
  setAdvert(state, payload) {
    state.advert = payload
  },
};

export default {
  state,
  getters,
  actions,
  mutations
}
