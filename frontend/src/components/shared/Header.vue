<template>
  <div>
    <modal-form></modal-form>
  <div class="row ">

    <div class="col-md-12 "><img src="img/logo.png"><log-panel></log-panel></div>
  </div>

    <nav class="navbar navbar-expand-md navbar-dark bg-dark m-auto">


      <button class="navbar-toggler">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarsExampleDefault">



          <ul class="nav nav-pills nav-fill mr-auto">

            <router-link to="/" tag="li" active-class="active" exact><a>Strona główna</a></router-link>
            <router-link to="/browse" tag="li" active-class="active"><a>Przeglądaj</a></router-link>
            <li class="nav-item">
            <a class="nav-link"  @click="$modal.show('login')">Konto</a>
            </li>
            <!--<li class="nav-item">-->
              <!--<a class="nav-link active" href="/">Strona główna</a>-->
            <!--</li>-->
            <!--<li class="nav-item">-->
              <!--<a class="nav-link" href="/">Przeglądaj</a>-->
            <!--</li>-->
            <!--<li class="nav-item">-->
              <!--<a class="nav-link" href="/">Losuj</a>-->
            <!--</li>-->

          </ul>

        <form class="form-inline my-2 my-lg-0">
          <input class="form-control mr-sm-2" type="text" placeholder="Wyszukaj" aria-label="Search">
          <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Szukaj</button>
        </form>
      </div>
    </nav>


    <div class="starter-template" id="new-items"
         style="text-align:center; background-color: #2B2B2B; color: white; padding: 20px;">
      <h3>Najnowsze przedmioty</h3>

    </div>



  </div>

</template>

<script>
  import ModalForm from '../ModalForm.vue'
  import LogPanel from './LogPanel.vue'
  export default {
    components: {
      LogPanel,
      ModalForm
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
