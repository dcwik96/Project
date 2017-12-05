<template>
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

    }
  }
</script>

<style>
</style>
