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
              <img class=" text-center" :src="'http://localhost:8080/api/photos/' + advert.photo.id"
                   style="width: 100%; height: 150px;">
              <p></p>
              <p v-if="advert.showInput">
                <span v-popover.hover="{content:'Zaloguj się!'}">
                <button class="btn btn-success" :disabled="$cookie.get('login') == null ? true : false"  role="button"  @click="enableInput(index)">Ile dasz?</button></span>
                <router-link :to="{name: 'advert', params: {id: advert.id}}" tag="button" class="btn btn-default" exact>
                  Zobacz ofertę
                </router-link>
              </p>
              <div v-if="!advert.showInput" class="row">
                <div class="col-lg-12">
                  <form @submit.prevent="makeOffer({ id: advert.id, price: price})">
                    <div class="input-group">
                      <input type="text" class="form-control" placeholder="Ile dasz? (zł)" v-model="price">
                      <span class="input-group-btn">
                      <button class="btn btn-success" type="submit">Ok</button>
                      <button class="btn btn-default" type="button" @click="disableInput(index)">Anuluj</button>
                    </span>
                    </div>
                  </form>
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
        price: 0,
      }
    },
    methods: {
      ...mapActions([
          'fetchLightData',
          'enableInput',
          'disableInput',
          'makeOffer'])
    },
    computed: {
      ...mapGetters({adverts: 'getArrayOfLightAdverts'}),
    },
    created() {
      this.fetchLightData()
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
