import Vue from 'vue'

const state = {
  userAdverts: {},
  offers: [],
  advertsCount: 0
};

const getters = {
  getUserAdverts: () => {
    return state.userAdverts
  },
  getOffers: () => {
    return state.offers
  },
  getAdvertsCount: () => {
    return state.advertsCount
  }
};

const actions = {
  fetchUserAdverts: ({commit}) => {
    const url = 'http://localhost:8080/api/userAdverts';
    Vue.http.get(url,
      {
        emulateHTTP: true,
        emulateJSON: true
      })
      .then((response) => {
        var count = Array.from(response.body).length;
        console.log(count);
        commit('mutateAdvertsCount', count);
        commit('mutateUserAdverts', response.body)
      },(response) => {
        console.log(response)
      })
  },
  fetchOffers: ({commit}, id) => {
    Vue.http.get('http://localhost:8080/api/advert/'+id+'/offers')
      .then((response) => {
        commit('mutateOffer', response.body)
      },
        (response) => {
          console.log(response.body)
        })
  }
};

const mutations = {
  mutateUserAdverts(state, payload) {
    state.userAdverts = payload
  },
  mutateOffer(state, payload) {
    state.offers = payload
  },
  mutateAdvertsCount(state, payload) {
    state.advertsCount = payload
  }
};

export default {
  state,
  getters,
  actions,
  mutations
}
