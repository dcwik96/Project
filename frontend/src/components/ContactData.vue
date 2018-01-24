<template>
    <div class="box">
      <label for="name">Imię:</label>
      <div class="input-group">
        <input id="name" type="text" class="form-control"
               v-model="buyerInfo.name">
        <span class="input-group-btn">
          <button
            v-clipboard:copy="buyerInfo.name"
            v-clipboard:success="onCopy"
            v-clipboard:error="onError"
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
            v-clipboard:copy="buyerInfo.surname"
            v-clipboard:success="onCopy"
            v-clipboard:error="onError"
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
            v-clipboard:copy="buyerInfo.email"
            v-clipboard:success="onCopy"
            v-clipboard:error="onError"
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

          <button
            v-clipboard:copy="buyerInfo.phone_number"
            v-clipboard:success="onCopy"
            v-clipboard:error="onError"
            @click="copySuccessHandler"
            class="btn btn-default"
            type="button">
            <i class="glyphicon glyphicon-copy"></i>
          </button>
      </div>
  </div>
</template>

<script>
    import Toasted from 'vue-toasted'
    import {mapGetters} from 'vuex';
    import Vue from 'vue'
    import VueClipboard from 'vue-clipboard2'
    Vue.use(VueClipboard)
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
        onCopy() {
          this.$toasted.success("Skopiowano.", config);
        },
        onError() {
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
