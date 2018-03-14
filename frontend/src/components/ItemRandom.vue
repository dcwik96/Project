<template>
    <div class="panel panel-primary text-center" :key="keyValue">
      <div class="panel-heading"><h3>{{advert.title}}</h3></div>
      <br><br>
      <img v-img:test :src="photoSrc + advert.photos[0].id" style="width: 300px;">
      <div class="panel-body"><h6>{{advert.description}}</h6>
        <br>
        <p>
        <div v-show="!showInput">
          <button class="btn btn-success" @click="toggleShowInput()" role="button">Ile dasz?</button>
          <router-link to="/" tag="button" class="btn btn-default" exact>Powrót</router-link>
          <button class="btn btn-success" type="button" @click="random">Losuj</button>
        </div>
        <div class="text-center vcenter">
          <div class="input-group" v-show="showInput" style="width: 250px;">
            <form @submit.prevent="makeOffer({ id: advert.id, price: price})">
              <div class="input-group">
                <input type="text" class="form-control" placeholder="Ile dasz? (zł)" v-model="price">
                <span class="input-group-btn">
                        <button class="btn btn-success" type="submit">Ok</button>
                        <button class="btn btn-default" type="button" @click="showInput = false">Anuluj</button>
                  </span>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
</template>
<script>
  import Vue from 'vue';
  import VueImg from 'v-img';
  import {mapGetters} from 'vuex'
  import {mapActions} from 'vuex'

  Vue.use(VueImg);

  export default {
    data() {
      return {
        id: '',
        showInput: false,
        price: 0,
        keyValue: false,
        photoSrc: 'http://localhost:8080/api/photos/'
      }
    },
    methods: {
      ...mapActions(['makeOffer',
        'fetchRandomAdvert']),
      toggleShowInput() {
        this.showInput = !this.showInput;
      },
      random() {
        this.fetchRandomAdvert();
        this.keyValue = !this.keyValue
      }
    },
    computed: {
      ...mapGetters({advert: 'getRandomAdvert'}),
    },
    created() {
      this.random()
    }
  }
</script>

<style>
  /*.fade-enter {*/
    /*opacity: 0;*/
  /*}*/
  /*.fade-enter-active {*/
    /*transition: all 1s;*/
  /*}*/
  /*.fade-leave {*/

  /*}*/
  /*.fade-leave-active {*/
    /*transition: all 1s;*/
    /*opacity: 0;*/
  /*}*/
</style>
