import Vue from 'vue'

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
    const url = 'http://localhost:8080/api/userAdverts';
    Vue.http.get(url,
      {
        emulateHTTP: true,
        emulateJSON: true
      })
      .then((response) => {
        commit('mutateUserAdverts', response.body)
      },(response) => {
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
  },
  acceptOffer({commit}, id) {
    const url = 'http://localhost:8080/api/advert/select/'+id;

    Vue.http.get(url)
      .then( (response) => {
        commit('mutateBuyerData', response.body)
    }, (response) => {
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
