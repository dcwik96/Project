<template>
  <div>
    <div class="page-header">
      <h1>Najnowsze przedmioty</h1>
    </div>
    <div v-if="adverts && adverts.length">
      <div class="row text-center">
        <div v-for="advert in adverts" class="col-sm-6 col-md-3">
          <div class="thumbnail">
            <div class="caption">
              <h5>{{advert.title | truncate(20)}}</h5>
              <img class=" text-center" :src="'http://localhost:8080/api/photos/' + advert.photos[0].id"
                   style="width: 100%; height: 150px;">
              <p></p>
              <p><a href="/" class="btn btn-success" role="button">Ile dasz?</a> <router-link :to= "{name: 'advert', params: {id: advert.id}}" tag="button" class="btn btn-default" exact>Zobacz ofertę</router-link> </p>
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
      return {}
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
