<template>
  <div>
    <modal-form></modal-form>
    <div class="btn-group blocks">
  <div v-if="!loggedIn" id="loginBtn" v-bind:style="loginBtnStyles" class="btn btn-success mr-test" @click="$modal.show('login')">Zaloguj się</div>
  <div v-if="!loggedIn" ref="registerBtn"  class="btn btn-primary" @click="$modal.show('register')">Zarejestruj się</div>
  </div>
  <div v-if="loggedIn">
  <div class="dropdown">
    <button id="avatarButton" ref="avatarBtn" v-bind:style="avatarBtnStyles" class="btn btn-default"  data-toggle="dropdown">
      <span class="glyphicon glyphicon-user"></span>
      Witaj, {{ username }}!
      <span class="caret"></span>
    </button>
    <ul class="dropdown-menu">
      <li><a href="#" >Ustawienia konta</a></li>
      <li><a href="#" @click="logOut">Wyloguj</a></li>
    </ul>
    <div id="functionPanel" ref="functionPanel" class="btn-group-vertical">
      <router-link to="/additem" tag="div" class="btn btn-default mb-1" exact>Wystaw przedmiot</router-link>
      <div class="input-group">
      <div class="btn btn-default mb-1">Twoje przedmioty <span class="badge badge-default badge-pill pull-right">1</span></div>
      </div>
      <div class="btn btn-default mb-1">Twoje oferty <span class="badge badge-default badge-pill pull-right">1</span></div>
    </div>
  </div>
  </div>
  </div>
</template>

<script>
  import ModalForm from '../ModalForm.vue'
  import avatar from '../../assets/avatar.png'
  import {mapGetters} from 'vuex'

  export default {
    data() {
      return {
        loggedIn: false,
        avatarBtnStyles: {height: ''},
        loginBtnStyles: {width: ''}
      }
    },
    components: {
      ModalForm
    },
    created() {
      if (this.$cookie.get('login') != null) {
        this.loggedIn = true
        this.$store.dispatch('setUsername', this.$cookie.get('login'))
      }
    },
    computed: {
      ...mapGetters({username: 'getUsername'})
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
        if (this.$refs.functionPanel) {
          let avatarHeight = this.$refs.functionPanel.clientHeight + 'px';
          this.avatarBtnStyles.height = avatarHeight;
        }
        if (this.$refs.registerBtn) {
          let registerWidth = this.$refs.registerBtn.clientWidth + 'px';
          this.loginBtnStyles.width = registerWidth;

        }
      }
    },
    mounted() {
      this.getAttributes()
    }
  }
</script>
