<template>
  <div>
    <div class="row align-items-center">
      <div class="col-md-4 mr-auto"><img :src="logoUrl"></div>
      <div class="col-md-4"><log-panel></log-panel></div>
    </div>
    <nav class="navbar navbar-expand-md navbar-dark bg-dark m-auto">
      <button class="navbar-toggler">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarsExampleDefault">
          <ul class="nav nav-pills mr-auto">
            <router-link to="/" tag="li" active-class="active" exact><a>Strona główna</a></router-link>
            <router-link to="/browse" tag="li" active-class="active"><a>Przeglądaj</a></router-link>

          </ul>
      </div>
    </nav>
    <div class="starter-template" id="new-items"
         style="text-align:center; background-color: #2B2B2B; color: white; padding: 20px;">
      <h3>Najnowsze przedmioty</h3>
    </div>
  </div>
</template>

<script>
  import LogPanel from './LogPanel.vue'
  import logo from '../../assets/logo.png'
  export default {
    data() {
      return {
        logoUrl : logo,
      }
    },
    components: {
      LogPanel
    }
  }
</script>

<style>

  .nav-pills a {
     color: white;
   }

  .nav-pills a:hover {
    color: black;
  }


</style>
