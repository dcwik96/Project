import Vue from 'vue'

const state = {
  userAdverts: [],
  offers: []
};

const getters = {
  getUserAdverts: () => {
    return state.userAdverts
  },
  getOffers: () => {
    return state.offers
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
  acceptOffer({commit}, payload) {
    const url = 'http://localhost:8080/api/advert/'+payload.advertId+'/offer/'+offerId;
    Vue.http.get(url, (response) => {
      console.log(response.body)
    }, (response) => {
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
  }
};

export default {
  state,
  getters,
  actions,
  mutations
}
