<template>
  <div class="box">
    <label for="name">Imię:</label>
    <div class="input-group">
      <input id="name" type="text" class="form-control"
             v-model="buyerInfo.name">
      <span class="input-group-btn">
          <button
            @click="copy(buyerInfo.name)"
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
            @click="copy(buyerInfo.surname)"
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
            @click="copy(buyerInfo.email)"
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
            @click="copy(buyerInfo.phone_number)"
            class="btn btn-default"
            type="button">
            <i class="glyphicon glyphicon-copy"></i>
          </button>
        </span>
    </div>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex';
  import Vue from 'vue'
  import Clipboard from 'v-clipboard';
  Vue.use(Clipboard);
  let config = {
    position : 'bottom-center',
    singleton: true,
    duration: 1000
  };

  export default {
    computed: {
      ...mapGetters({buyerInfo: 'getBuyerData'})
    },
    methods: {
      copy(data) {
        this.$clipboard(data);
        this.$toasted.success("Skopiowano.", config);
      }
    }
  }
</script>

<style>
  .box {
    padding: 50px;
  }
</style>
