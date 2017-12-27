<!-- <template>
  <div class="mt-3">
    <div v-if="adverts && adverts.length" >
      <div class="row" v-for="i in Math.ceil(adverts.length / 3)">
        <div v-for="advert in adverts.slice((i - 1) * 3, i * 3)" class="col-sm-4" >
          <div class="card text-center align-middle" style="height: 350px; margin-bottom: 20px;">
            <img v-bind:src="'http://localhost:8080/api/photos/' + advert.id" class="mx-auto d-block" style="width: 200px; height: 150px;">
            <div class="card-block">
              <h6 class="card-title">{{advert.title}}</h6>
              <div class="card-body">
                <a href="/" class="btn btn-primary">Więcej</a>
                <a href="/" class="btn btn-success">Ile dasz?</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template> -->

<template>
  <div>
    <div class="page-header">
  <h1>Najnowsze przedmioty</h1>
</div>
  <div v-if="adverts && adverts.length" >
  <div class="row text-center">
  <div v-for="advert in adverts" class="col-sm-6 col-md-3">
    <div class="thumbnail">

      <div class="caption">
        <h5>{{advert.title | truncate(20)}}</h5>
        <img class=" text-center" v-bind:src="'http://localhost:8080/api/photos/' + advert.id" style="width: 100%; height: 150px;">
        <p></p>
        <p><a href="#" class="btn btn-success" role="button">Ile dasz?</a> <a href="#" class="btn btn-default" role="button">Zobacz ofertę</a></p>
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
    data () {
      return {
      }
    },
    computed: {
      ...mapGetters({adverts: 'getArrayOfAdverts'}),
    },
    created() {
      this.$store.dispatch('getAdverts')
    },
    filters: {
  	  truncate: function(string, value) {
    	return string.substring(0, value) + '...';
    }
  }
}
</script>
<style>
</style>
