<template>
<div>
  <modal-form></modal-form>
  <div class="btn-group blocks">

  <div v-if="!loggedIn" id="loginBtn" ref="loginBtn" class="btn btn-success mr-test" @click="$modal.show('login')">Panel logowania</div>
  <div v-if="!loggedIn" v-bind:style="registerBtnStyles" class="btn btn-primary" @click="$modal.show('login')">Zarejestruj się</div>
  </div>
  <div v-show="loggedIn" >
  <div class="dropdown">
    <button id="avatarButton" ref="avatarBtn" v-bind:style="avatarBtnStyles" class="btn btn-default"  data-toggle="dropdown">
      <span class="glyphicon glyphicon-user"></span>
      Witaj, Mariusz!
      <span class="caret"></span>
    </button>
    <ul class="dropdown-menu">
      <li><a href="#" >Ustawienia konta</a></li>
      <!--<li><a href="#">Test</a></li>-->
      <li><a href="#" @click="logOut">Wyloguj</a></li>
    </ul>
    <div id="functionPanel" ref="functionPanel" class="btn-group-vertical">
      <div class="btn btn-default mb-1">Wystaw przedmiot</div>
      <div class="input-group">
      <div class="btn btn-default mb-1">Twoje przedmioty <span class="badge badge-default badge-pill pull-right">1</span></div>
      </div>
    <div class="btn btn-default">Złożone oferty <span class="badge badge-default badge-pill">0</span></div>
    </div>
  </div>
  </div>
</div>
</template>

<script>
  import {eventBus} from "../../main";
  import ModalForm from '../ModalForm.vue'
  export default {
    data() {
      return {
        loggedIn: false,
        avatarBtnStyles: {height: ''},
        registerBtnStyles: {width: ''}
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
      getAttributes() {
      if(this.$refs.functionPanel) {
        let avatarHeight = this.$refs.functionPanel.clientHeight + 'px';
        this.avatarBtnStyles.height = avatarHeight;

      }
      if(this.$refs.loginBtn) {
        let registerWidth = this.$refs.loginBtn.clientWidth + 'px';
        this.registerBtnStyles.width = registerWidth;

      }

    }

    },
    mounted () {
   this.getAttributes()
    }
  }
</script>
