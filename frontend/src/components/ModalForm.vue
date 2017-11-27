<template>
  <modal name="login" transition="pop-out" :width="modalWidth" :height="400">
    <b-card no-body>
      <b-tabs pills card>
        <b-tab title="Zaloguj" active>
          <app-login-form></app-login-form>
        </b-tab>
        <b-tab title="Zarejestruj">
          <app-register-form></app-register-form>
        </b-tab>
      </b-tabs>
    </b-card>
  </modal>
</template>
<script>
  import LoginForm from './LoginForm.vue'
  import RegisterForm from './RegisterForm.vue'
  const MODAL_WIDTH = 656
  export default {
    name: 'LoginModal',
    data () {
      return {
        modalWidth: MODAL_WIDTH
      }
    },
    created () {
      this.modalWidth = window.innerWidth < MODAL_WIDTH
        ? MODAL_WIDTH / 2
        : MODAL_WIDTH
    },
    components: {
      appLoginForm : LoginForm,
      appRegisterForm : RegisterForm
    }
  }
</script>
<style>
  .pills a {
    color: white;
  }

  .pills a:hover {
    color: black;
  }
</style>
