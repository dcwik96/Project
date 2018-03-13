import Vue from 'vue';
import toasted from 'vue-toasted';
import axios from 'axios';

Vue.use(toasted);

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
    let tempAdverts = [];
    axios.get('api/adverts')
      .then(response => {
        tempAdverts = response.data;
        Array.from(tempAdverts).forEach(advert => {
          advert.showInput = true
        });
        commit('setAdvertsArray', tempAdverts)
      })
      .catch((e) => {
        console.log(e)
      })
  },
  fetchLightData: ({commit}) => {
    let tempAdverts = [];
    axios.get('api/lightAdverts')
      .then(response => {
        tempAdverts = response.data;
        Array.from(tempAdverts).forEach(advert => {
          advert.showInput = true
        });
        commit('setLightAdvertsArray', tempAdverts)
      })
      .catch((e) => {
        console.log(e)
      }
    )
  },
  fetchAdvert: ({commit}, id) => {
    axios.get('api/oneadvert/' + id)
      .then(
      response => {
        commit('setAdvert', response.data)
      })
      .catch((e) => {
        Vue.toasted.error('Wystąpił problem', config)
      })
  },
  makeOffer: (payload, data) => {
    let config = {
      position: 'bottom-center',
      singleton: true,
      duration: 1000
    };

    let formData = new FormData();
    formData.append('offer',data.price);

    axios.post('api/advert/' + data.id + '/newOffer', formData)
      .then(() => {
        Vue.toasted.success('Twoja oferta została złożona', config)
      })
      .catch((e) => {
        Vue.toasted.error('Wystąpił problem', config)
      })
  },
  fetchRandomAdvert: ({commit}) => {
    axios.get('api/randomAdvert')
      .then(
      response => {
        commit('setRandomAdvert', response.data)
      })
      .catch((e) => {
        console.log(e)
    })
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
