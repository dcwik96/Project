<template>
  <div>
    <div class="page-header">
      <h1>Najnowsze przedmioty</h1>
    </div>
    <div v-if="adverts && adverts.length">
      <div class="row text-center">
        <div v-for="(advert,index) in adverts" class="col-sm-6 col-md-3">
          <div class="panel panel-primary">
            <div class="panel-heading"><h5>{{advert.title | truncate(20)}}</h5></div>
            <div class="panel-body">
              <img class=" text-center" :src="'http://localhost:8080/api/photos/' + advert.photos[0].id"
                   style="width: 100%; height: 150px;">
              <p></p>
              <p v-if="advert.showInput">
                <button class="btn btn-success" role="button" @click="enableInput(index)">Ile dasz?</button>
                <router-link :to="{name: 'advert', params: {id: advert.id}}" tag="button" class="btn btn-default" exact>
                  Zobacz ofertę
                </router-link>
              </p>
              <div v-if="!advert.showInput" class="row">
                <div class="col-lg-12">
                  <div class="input-group">
                    <input type="text" class="form-control" placeholder="Ile dasz? (zł)">
                    <span class="input-group-btn">
                      <button class="btn btn-success" @click="makeOffer(advert.id)" type="button">Ok</button>
                      <button class="btn btn-default" type="button" @click="disableInput(index)">Anuluj</button>
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'
  import {mapActions} from 'vuex'
  export default {
    name: 'app',
    data() {
      return {

      }
    },
    methods: {
      makeOffer(id) {

      },
      ...mapActions(['getAdverts',
                      'enableInput',
                      'disableInput'])
    },
    computed: {
      ...mapGetters({adverts: 'getArrayOfAdverts'}),
    },
    created() {
     this.getAdverts()
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
