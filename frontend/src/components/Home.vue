<template>
  <div>
    <div class="page-header">
      <h1>Najnowsze przedmioty</h1>
    </div>
    <div v-if="adverts && adverts.length">
      <div class="row text-center">
        <div v-for="advert in adverts" class="col-sm-6 col-md-3">
          <div class="panel panel-primary">
            <div class="panel-heading"><h5>{{advert.title | truncate(20)}}</h5></div>
  <div class="panel-body">
    <img class=" text-center" :src="'http://localhost:8080/api/photos/' + advert.photos[0].id"
         style="width: 100%; height: 150px;">
         <p></p>

         <p v-if="showFirst">
           <button class="btn btn-success" role="button" v-on:click="toggle">Ile dasz?</button> <router-link :to= "{name: 'advert', params: {id: advert.id}}" tag="button" class="btn btn-default" exact>Zobacz ofertę</router-link>
         </p>

           <!-- <div class="input-group">
             <input type="text" class="form-control" placeholder="Wprowadź kwotę" aria-describedby="basic-addon2">
             <span class="input-group-addon">zł</span>
             <button class="btn btn-success" role="button" v-on:click="toggle">Ok</button> <router-link :to= "{name: 'advert', params: {id: advert.id}}" tag="button" class="btn btn-default" exact>Anuluj</router-link>
           </div> -->
           <div :key="advert.id" v-if="!showFirst" class="row">
  <div class="col-lg-12">
    <div class="input-group">

      <input type="text" class="form-control" placeholder="Ile dasz? (zł)">
      <span class="input-group-btn">
        <button class="btn btn-success" v-on:click="toggle" type="button">Ok</button>
        <button class="btn btn-default" type="button" v-on:click="toggle">Anuluj</button>
      </span>
    </div><!-- /input-group -->
  </div><!-- /.col-lg-6 -->
</div><!-- /.row -->

  </div>
</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'

  export default {
    name: 'app',
    data() {
      return {
        showFirst: true
      }
    },
    methods: {
      toggle: function() {
      this.showFirst = !this.showFirst;
    }
  },
    computed: {
      ...mapGetters({adverts: 'getArrayOfAdverts'}),
    },
    created() {
      this.$store.dispatch('getAdverts')
    },
    filters: {
      truncate: function (string, value) {
        return string.substring(0, value) + '...';
      }
    }
  }
</script>
<style>
</style>
