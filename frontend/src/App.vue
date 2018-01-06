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

  .page-heading {
    position: relative;
    padding: 30px 15px;
    text-align: left;
    color: rgba(255, 255, 255, 0.5);
    background-color: #ffffff;
    font-size: 1.25rem;
  }

  .page-header {
    position: relative;
  }

  #functionPanel div {
    margin-bottom: 5px;
  }

  #functionPanel div:last-child {
    margin-bottom: 0px;
  }

  .mr-test {
    margin-right: 5px;
  }

  .blocks {
    padding: 24px 12px;
    margin: 0 5px;
    border-radius: 0;
  }

  @media screen and (min-width: 991px) {
    .vcenter {
      display: flex;
      align-items: center;
      justify-content: center;
    }
  }

  .modal-mask {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .5);
    display: table;
    transition: opacity .3s ease;
  }

  .modal-wrapper {
    display: table-cell;
    vertical-align: middle;
  }

  .modal-container {
    width: 300px;
    margin: 0px auto;
    padding: 20px 30px;
    background-color: #fff;
    border-radius: 2px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, .33);
    transition: all .3s ease;
    font-family: Helvetica, Arial, sans-serif;
  }

  .modal-header h3 {
    margin-top: 0;
    color: #42b983;
  }

  .modal-body {
    margin: 20px 0;
  }

  .modal-default-button {
    float: right;
  }

  .modal-enter {
    opacity: 0;
  }

  .modal-leave-active {
    opacity: 0;
  }

  .modal-enter .modal-container,
  .modal-leave-active .modal-container {
    -webkit-transform: scale(1.1);
    transform: scale(1.1);
  }

</style>
