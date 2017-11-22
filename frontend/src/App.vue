<template>
  <div id="app" >
    <div v-if="adverts && adverts.length" >
      <div class="row" v-for="i in Math.ceil(adverts.length / 3)">
        <div v-for="advert in adverts.slice((i - 1) * 3, i * 3)" class="col-sm-4" >

          <div class="card text-center align-middle">
            <img v-bind:src="'http://localhost:8080/api/photos/' + advert.id" class="mx-auto d-block" style="width: 200px;">
            <div class="card-block">
              <h6 class="card-title">{{advert.title}}</h6>
              <div class="card-body">
                <a href="#" class="btn btn-primary">Więcej</a>
                <a href="#" class="btn btn-success">Ile dasz?</a>
              </div>
            </div>
          </div>


        </div>
      </div>
    </div>
    <ul v-else>
      <li v-for="error of errors">
        {{error.message}}
      </li>
    </ul>
    <router-view/>

  </div>
</template>

<script>
  import axios from 'axios'
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
      var config = {
        headers: {'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin': '*'
        },

      }
      axios.get('http://localhost:8080/api/adverts', {},config)
        .then(response => {
          this.adverts = response.data
        })
        .catch(e => {
          this.errors.push(e)
        })

    }
  }
</script>


<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }
</style>
