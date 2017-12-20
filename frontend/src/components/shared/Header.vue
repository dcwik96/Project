<template>
  <div>
  <div class="page-header">
    <div class="row vcenter">
      <div class="col-md-8"><img src="img/logo.png" class="img-responsive"></div><div class="col-md-4"><log-panel></log-panel></div>
    </div>

  <!-- <h1>Example page header <small>Subtext for header</small></h1> -->
</div>
  <nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">iledasz.pl</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <router-link to="/" tag="li" active-class="active" exact><a>Strona główna</a></router-link>
        <router-link to="/browse" tag="li" active-class="active"><a>Przeglądaj</a></router-link>
        <li><a href="#">Losowy przedmiot</a></li>
      </ul>


    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
</div>
  <!-- <div>
    <div class="row align-items-center">
      <div class="col-md-4 mr-auto"><img src="img/logo.png"></div>
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
  </div> -->
</template>

<script>
  import LogPanel from './LogPanel.vue'
  export default {
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
