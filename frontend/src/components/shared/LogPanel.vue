<template>
<div>

  <modal-form></modal-form>
  <div class="btn-group-justified">
  <div v-if="!loggedIn" class="btn btn-success" @click="$modal.show('login')"><a>Panel logowania</a></div>
  </div>

  <div v-if="loggedIn" >

  <div class="dropdown">
    <button id="avatarButton" class="btn btn-default" type="button" data-toggle="dropdown">
      <img class="mr-2" src="img/avatar.png" style="width: 30px;">
      Witaj, Mariusz!
      <span class="caret"></span>
    </button>
    <ul class="dropdown-menu">
      <li><a href="#" >Ustawienia konta</a></li>
      <!--<li><a href="#">Test</a></li>-->
      <li><a href="#">Wyloguj</a></li>
    </ul>
    <div id="functionPanel" class="btn-group-vertical">
      <div class="btn btn-default mb-1 text-left">Wystaw przedmiot</div>
      <div class="input-group">
      <div class="btn btn-default mb-1">Twoje przedmioty <span class="badge badge-default badge-pill">1</span></div>
      </div>
    <div class="btn btn-default text-left">Złożone oferty <span class="badge badge-default badge-pill">0</span></div>

    </div>
  </div>
  </div>








  <!--<div class="btn-group mb-1">-->
  <!--<button class="btn mr-1 btn-secondary">Wystaw ></button>-->
    <!--<button class="btn mr-1 btn-secondary">Konto ></button>-->
    <!--<button class="btn mr-1 btn-secondary">Przedmioty</button>-->
    <!--<button class="btn mr-1 btn-secondary">Oferty</button>-->
    <!--<button class="btn mr-1 btn-secondary">Wygrane</button>-->
    <!--<button class="btn mr-1 btn-secondary">Wyloguj</button>-->
  <!--</div>-->


    <!--<button type="button" class="btn btn-info mr-1"-->
            <!--style="width: 123px; font-size: 10px; background-color: #7A7F7C">Wystaw >-->
    <!--</button>-->
    <!--<button type="button" class="btn btn-secondary"-->
            <!--style="width: 123px; font-size: 10px; background-color: #7A7F7C">Konto >-->
    <!--</button>-->
  <!--</div>-->
  <!--<div>-->
    <!--<button type="button" class="btn btn-secondary mb-1"-->
            <!--style="width: 123px; font-size: 10px; background-color: #7A7F7C">Przedmioty <span-->
      <!--class="badge badge-danger">0</span></button>-->
    <!--<button type="button" class="btn btn-secondary mr-1 mb-1"-->
            <!--style="width: 123px; font-size: 10px; background-color: #7A7F7C">Oferty <span-->
      <!--class="badge badge-danger">0</span></button>-->

  <!--</div>-->
  <!--<button type="button" class="btn btn-secondary mb-1"-->
          <!--style="width: 123px; font-size: 10px; background-color: #7A7F7C">Wygrane <span-->
    <!--class="badge badge-danger">0</span></button>-->
  <!--<button type="button" class="btn btn-secondary mr-1 mb-1 "-->
          <!--style="width: 123px; font-size: 10px; background-color: #7A7F7C">Wyloguj-->
  <!--</button>-->


</div>
</template>

<script>
  import ModalForm from '../ModalForm.vue'
  export default {
    data() {
      return {
        loggedIn: true
      }
    },
    components: {
      ModalForm
    }

  }



//    document.getElementById('#logButton').style.height = height;


</script>

