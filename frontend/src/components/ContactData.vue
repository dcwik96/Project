<template>
  <p>
    Dane składającego tą ofertę:
    <br>
      {{buyerInfo.name}}
    <br>
      {{buyerInfo.surname}}
    <br>
      {{buyerInfo.phone_number}}
    <br>
      {{buyerInfo.email}}
  </p>
</template>

<script>
    import {mapGetters} from 'vuex';
    export default {
      computed: {
        ...mapGetters({buyerInfo: 'getBuyerData'})
      }
    }
</script>

<style scoped>

</style>
