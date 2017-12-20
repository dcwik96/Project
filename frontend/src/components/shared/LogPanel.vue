<template>
<div>
  <modal-form></modal-form>
  <div class="btn-group-justified">
  <div v-if="!loggedIn" class="btn btn-success" @click="$modal.show('login')"><a>Panel logowania</a></div>
  </div>
  <div v-show="loggedIn" >
  <div class="dropdown">
    <button id="avatarButton" class="btn btn-default" type="button" data-toggle="dropdown">
      <img class="mr-2" src="avatarUrl" style="width: 30px;">
      Witaj, Mariusz!
      <span class="caret"></span>
    </button>
    <ul class="dropdown-menu">
      <li><a href="#" >Ustawienia konta</a></li>
      <!--<li><a href="#">Test</a></li>-->
      <li><a href="#" @click="logOut">Wyloguj</a></li>
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
</div>
</template>

<script>
  import {eventBus} from "../../main";
  import ModalForm from '../ModalForm.vue'
  import avatar from '../../assets/avatar.png'
  export default {
    data() {
      return {
        loggedIn: false,
        avatarUrl: avatar
      }
    },
    components: {
      ModalForm
    },
    created() {
      if (this.$cookie.get('login') != null) {
        this.loggedIn = true
      }

    },
    methods: {
      logOut() {
        this.$http.get('http://localhost:8080/logout')
          .then(() => {
            this.loggedIn = false
            this.$cookie.delete('login')
          })
      },
    }
  }
</script>


