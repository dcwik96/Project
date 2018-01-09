<template>
  <div>
    <div class="panel panel-primary text-center">

      <div class="panel-heading"><h3>{{advert.title}}</h3></div>
      <br><br>

      <img v-img:test :src="'http://localhost:8080/api/photos/' + advert.photos[0].id" style="width: 300px;">

      <div class="panel-body"><h6>{{advert.description}}</h6>
        <br>

        <p>
          <div v-show="!showInput">
            <button class="btn btn-success" @click="toggleShowInput()" role="button">Ile dasz?</button>
            <router-link to="/" tag="button" class="btn btn-default" exact>Powrót</router-link>
          </div>
            <div class="text-center vcenter">
              <div class="input-group" v-show="showInput" style="width: 250px;">
                <form @submit.prevent="makeOffer({ id: advert.id, price: price})">
                  <div class="input-group">
                    <input type="text" class="form-control" placeholder="Ile dasz? (zł)" v-model="price" >
                    <span class="input-group-btn">
                      <button class="btn btn-success" type="submit">Ok</button>
                      <button class="btn btn-default" type="button" @click="showInput = false">Anuluj</button>
                    </span>
                  </div>
                </form>
            </div>
        </p>
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
        advert: {},
        showInput: false,
        price: 0
      }
    },
    computed: {
      ...mapGetters({adverts: 'getArrayOfAdverts'}),
    },
    created() {
      this.getAdvertById(this.$route.params.id)
    },
    methods: {
      ...mapActions(['makeOffer']),
      getAdvertById(id) {
        for (var i = 0; i < this.adverts.length; ++i) {
          if (this.adverts[i].id == id) {
            this.advert = this.adverts[i]
            return
          }
        }
      },
      toggleShowInput() {
        this.showInput = !this.showInput;
      }
    }
  }
</script>
