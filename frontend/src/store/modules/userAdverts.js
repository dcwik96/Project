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
        commit('SET_USER_ADVERTS', response.data)
      })
      .catch((e) => {
        console.log(e)
      })
  },
  fetchOffers: ({commit}, id) => {
    axios.get('/api/advert/' + id + '/offers')
      .then((response) => {
        commit('SET_OFFER', response.data)
      })
      .catch((e) => {
        console.log(e)
      })
  },
  acceptOffer({commit}, id) {
    axios.get('/api/advert/select/' + id)
      .then((response) => {
        commit('SET_BUYER_DATA', response.data)
      })
      .catch((e) => {
        console.log(e)
      })
  }
};

const mutations = {
  SET_USER_ADVERTS(state, payload) {
    state.userAdverts = payload
  },
  SET_OFFER(state, payload) {
    state.offers = payload
  },
  SET_BUYER_DATA(state, payload) {
    state.buyerData = payload
  }
};

export default {
  state,
  getters,
  actions,
  mutations
}
