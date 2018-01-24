<template>
    <div class="box">
      <label for="name">Imię:</label>
      <div class="input-group">
        <input id="name" type="text" class="form-control"
               v-model="buyerInfo.name">
        <span class="input-group-btn">
          <button
            v-clipboard="buyerInfo.name"
            @click="copySuccessHandler"
            class="btn btn-default"
            type="button">
            <i class="glyphicon glyphicon-copy"></i>
          </button>
        </span>
      </div>
      <label for="surname">Nazwisko:</label>
      <div class="input-group">
        <input id="surname" type="text" class="form-control"
               v-model="buyerInfo.surname">
        <span class="input-group-btn">
          <button
            v-clipboard="buyerInfo.surname"
            @click="copySuccessHandler"
            class="btn btn-default"
            type="button">
            <i class="glyphicon glyphicon-copy"></i>
          </button>
        </span>
      </div>
      <label for="email">Email:</label>
      <div class="input-group">
        <input id="email" type="text" class="form-control"
               v-model="buyerInfo.email">
        <span class="input-group-btn">
          <button
            v-clipboard="buyerInfo.email"
            @click="copySuccessHandler"
            class="btn btn-default"
            type="button">
            <i class="glyphicon glyphicon-copy"></i>
          </button>
        </span>
      </div>
      <label for="phone_number">Numer telefonu:</label>
      <div class="input-group">
        <input id="phone_number" type="text" class="form-control"
               v-model="buyerInfo.phone_number">
        <span class="input-group-btn">
          <button
            v-clipboard="buyerInfo.phone_number"
            @click="copySuccessHandler"
            class="btn btn-default"
            type="button">
            <i class="glyphicon glyphicon-copy"></i>
          </button>
        </span>
      </div>
  </div>
</template>

<script>
    import Toasted from 'vue-toasted'
    import {mapGetters} from 'vuex';
    import Vue from 'vue'
    import Clipboard from 'v-clipboard';
    Vue.use(Clipboard);
    var config = {
      position : 'bottom-center',
      singleton: true,
      duration: 1000
    };

    export default {
      computed: {
        ...mapGetters({buyerInfo: 'getBuyerData'})
      },
      methods: {
        copySuccessHandler() {
          this.$toasted.success("Skopiowano.", config);
        },
        copyErrorHandler() {
          this.$toasted.error("Błąd podczas kopiowania.", config);
        }
      }
    }
</script>

<style>
  .box {
    padding: 50px;
  }
</style>
