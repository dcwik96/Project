<template>
  <modal name="login" transition="pop-out" :width="modalWidth" :height="modalWidth">
    <app-login-form></app-login-form>
  </modal>
</template>
<script>

  import LoginForm from './LoginForm.vue'
  import { eventBus } from "../main"

  const MODAL_WIDTH = 656
  const MODAL_HEIGHT = 330
  export default {
    name: 'LoginModal',
    data () {
      return {
        modalWidth: MODAL_WIDTH,
        modalHeight: MODAL_HEIGHT
      }
    },
    created () {
      this.modalWidth = window.innerWidth < MODAL_WIDTH
        ? MODAL_WIDTH / 2
        : MODAL_WIDTH
      this.modalHeight = MODAL_HEIGHT
    },
    components: {
      appLoginForm : LoginForm,
    },
    methods: {
      show () {
        this.$modal.show('login');
      },
      hide () {
        this.$modal.hide('login');
      }
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
