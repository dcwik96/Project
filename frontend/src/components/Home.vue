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

  export default {
    name: 'app',
    data () {
      return {
        adverts: [],
        photos: [],
        errors: []
      }
    },

    created() {
      this.$http.get('http://localhost:8080/api/adverts')
        .then(response => {
          this.adverts = response.data
        }, e => {
          console.log(e)
        })

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
