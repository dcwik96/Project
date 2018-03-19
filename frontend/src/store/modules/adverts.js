import Vue from 'vue';
import toasted from 'vue-toasted';
import axios from 'axios';

Vue.use(toasted);

const state =
  {
    adverts: [],
    lightAdverts: [],
    advert: {},
    randomAdvert: {},
    advertId: 0
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
    commit('TOGGLE_SHOW_INPUT', payload)
  },
  disableInput: ({commit}, payload) => {
    commit('TOGGLE_SHOW_INPUT', payload)
  },
  fetchData: ({commit}) => {
    let tempAdverts = [];
    axios.get('api/adverts')
      .then(response => {
        tempAdverts = response.data;
        Array.from(tempAdverts).forEach(advert => {
          advert.showInput = true
        });
        commit('SET_ADVERTS_ARRAY', tempAdverts)
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
        commit('SET_LIGHT_ADVERTS_ARRAY', tempAdverts)
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
        commit('SET_ADVERT', response.data)
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
        commit('SET_RANDOM_ADVERT', response.data)
      })
      .catch((e) => {
        console.log(e)
    })
  },
  addAdvert({commit}, payload) {
    axios.post('api/newAdvert', payload);
  },
  addPhotos({commit}, payload) {
    let formData = new FormData();
    payload.images.forEach(img => {
      formData.append('image',img);
      axios.post('api/'+advertId+'/sendNudes', formData);
      formData = new FormData()
    })

  }
};

const mutations = {
  SET_ADVERTS_ARRAY(state, payload) {
    state.adverts = payload
  },
  SET_LIGHT_ADVERTS_ARRAY(state, payload) {
    state.lightAdverts = payload
  },
  TOGGLE_SHOW_INPUT(state, payload) {
    state.lightAdverts[payload].showInput = !state.lightAdverts[payload].showInput
  },
  SET_ADVERT(state, payload) {
    state.advert = payload
  },
  SET_RANDOM_ADVERT(state, payload) {
    state.randomAdvert = payload
  },
  SET_ADVERT_ID(state, payload) {
    state.advertId = payload;
  }
};

export default {
  state,
  getters,
  actions,
  mutations
}
