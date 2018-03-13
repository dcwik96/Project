import Vue from 'vue';
import axios from 'axios';

const state = {
  userAdverts: [],
  offers: [],
  buyerData: {}
};

const getters = {
  getUserAdverts: () => {
    return state.userAdverts
  },
  getOffers: () => {
    return state.offers
  },
  getBuyerData: () => {
    return state.buyerData
  }
};

const actions = {
  fetchUserAdverts: ({commit}) => {

    axios.get('api/userAdverts',)
      .then((response) => {
        commit('mutateUserAdverts', response.data)
      })
      .catch((e) => {
        console.log(e)
      })
  },
  fetchOffers: ({commit}, id) => {
    axios.get('/api/advert/' + id + '/offers')
      .then((response) => {
        commit('mutateOffer', response.data)
      })
      .catch((e) => {
        console.log(e)
      })
  },
  acceptOffer({commit}, id) {
    axios.get('/api/advert/select/' + id)
      .then((response) => {
        commit('mutateBuyerData', response.data)
      })
      .catch((e) => {
        console.log(e)
      })
  }
}

const mutations = {
  mutateUserAdverts(state, payload) {
    state.userAdverts = payload
  },
  mutateOffer(state, payload) {
    state.offers = payload
  },
  mutateBuyerData(state, payload) {
    state.buyerData = payload
  }
};

export default {
  state,
  getters,
  actions,
  mutations
}
