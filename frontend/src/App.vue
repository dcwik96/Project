<template>
  <div class="container">
    <app-header></app-header>
    <router-view></router-view>



  </div>
</template>

<script>
  import Header from './components/shared/Header.vue'
  import Home from './components/Home.vue'

  export default {


    components: {
      appHome: Home,
      appHeader: Header
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
