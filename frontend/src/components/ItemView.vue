<template>
  <div>
    <div class="panel panel-primary text-center">
      <div class="panel-heading"><h3>{{advert.title}}</h3></div>
      <br>
      <div class="alert alert-success" v-if="offerAccepted">{{message}}</div>
      <div class="alert alert-danger" v-if="someProblem">{{message}}</div>
      <br><br>
    <img v-img:test :src="'http://localhost:8080/api/photos/' + advert.photos[0].id" style="width: 300px;">
      <div class="panel-body"><h6>{{advert.description}}</h6></div>
    <br>
     <p>
       <button class="btn btn-success" @click="makeOffer">Ile dasz?</button>
       <input type="text" style="width: 100px" ref="priceInput">
       <router-link to= "/" tag="button" class="btn btn-default" exact>Powrót</router-link>
     </p>
      <div class="small-12 columns"v-if="offers.length > 0 && isValidUser">
        <br>
        <h2>Złożone ofery</h2>
        <ul class="list-group" >
          <li class="list-group-item" v-for="offer in offers">
            {{ offer.price }}
          </li>
        </ul>
      </div>
  </div>
  </div>
</template>

<script>
import Vue from 'vue';
import VueImg from 'v-img';

Vue.use(VueImg);
  import {mapGetters} from 'vuex'
  export default {
    data () {
      return {
        id: '',
        advert: {},
        offerAccepted: false,
        message: '',
        someProblem: false,
        offers:[{price: 100}, {price:1200}, {price:1300}]
      }

    },
    computed: {
      ...mapGetters({adverts: 'getArrayOfAdverts'}),
    },
    created() {
      this.getAdvertById(this.$route.params.id)
    },
    methods: {
      getAdvertById(id) {
        for(var i = 0; i < this.adverts.length; ++i) {
          if(this.adverts[i].id == id ) {
            this.advert = this.adverts[i]
            return
          }
        }
      },
      makeOffer() {
        var formData = new FormData();
        formData.append('offer', this.$refs.priceInput.value)
        this.$http.post('http://localhost:8080/api/advert/'+this.advert.id+'/newOffer',formData)
          .then(
            response => {
              this.offerAccepted = true;
              this.message = 'Twoja oferta została dodana.'
            console.log(response)
          },
            response => {
              this.someProblem = true
              this.message = 'Wystąpił problem przy składaniu oferty'
              console.log(response)
          })
      }
    }
  }
</script>
