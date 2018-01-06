

<template>
  <div>
    <div class="panel panel-primary text-center">

      <div class="panel-heading"><h3>{{advert.title}}</h3></div>
      <br><br>


    <img v-img:test :src="'http://localhost:8080/api/photos/' + advert.photos[0].id" style="width: 300px;">

      <div class="panel-body"><h6>{{advert.description}}</h6></div>

    <br>

     <p><a href="/" class="btn btn-success" role="button">Ile dasz?</a> <router-link to= "/" tag="button" class="btn btn-default" exact>Powrót</router-link> </p>


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
        advert: {}
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
      }
    }
  }
</script>
