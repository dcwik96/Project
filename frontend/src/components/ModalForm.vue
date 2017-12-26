<template>
  <div>
  <modal name="login" transition="pop-out" :width="modalWidth" :height="300">

          <app-login-form></app-login-form>

  </modal>

  <modal name="register" transition="pop-out" :width="modalWidth" :height="modalHeight">

          <app-register-form></app-register-form>

  </modal>
</div>
</template>
<script>

  import LoginForm from './LoginForm.vue'
  import RegisterForm from './RegisterForm.vue'
  import { eventBus } from "../main"


  const MODAL_WIDTH = 400
  export default {
    name: 'LoginModal',
    name: 'RegisterModal',
    data () {
      return {
        modalWidth: MODAL_WIDTH,
        modalHeight: 600
      }
    },
    created () {
      this.modalWidth = window.innerWidth < MODAL_WIDTH
        ? MODAL_WIDTH / 2
        : MODAL_WIDTH


      if(this.$refs.appRegisterForm != undefined) {
        this.modalHeight = this.$refs.appRegisterForm.height + 'px'
      }
      eventBus.$on('loggedIn', this.hide)
    },
    components: {
      appLoginForm : LoginForm,
      appRegisterForm : RegisterForm
    },
    methods: {

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
