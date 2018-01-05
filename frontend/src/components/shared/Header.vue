<template>
  <div>
    <div class="page-header">
      <div class="row vcenter">
        <div class="col-md-8"><img :src="logoUrl" class="img-responsive"></div>
        <div class="col-md-4">
          <log-panel></log-panel>
        </div>
      </div>
    </div>
    <nav class="navbar navbar-default">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                  data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">iledasz.pl</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav">
            <router-link to="/" tag="li" active-class="active" exact><a>Strona główna</a></router-link>
            <router-link to="/browse" tag="li" active-class="active"><a>Przeglądaj</a></router-link>
            <li><a href="#">Losowy przedmiot</a></li>
          </ul>
        </div>
      </div>
    </nav>
  </div>

</template>

<script>
  import LogPanel from './LogPanel.vue'
  import logo from '../../assets/logo.png'

  export default {
    data() {
      return {
        logoUrl: logo,
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
