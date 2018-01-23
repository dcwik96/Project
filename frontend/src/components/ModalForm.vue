<template>
  <div>
    <modal name="login" transition="pop-out" :width="modalWidth" :height="350">
      <app-login-form></app-login-form>
    </modal>
    <modal name="register" transition="pop-out" :width="modalWidth" :height="modalHeight">
      <app-register-form></app-register-form>
    </modal>
    <modal name="buyer" transition="pop-out" :width="modalWidth" :height="400">
      <app-contact-data></app-contact-data>
    </modal>
  </div>
</template>
<script>
  import LoginForm from './LoginForm.vue'
  import RegisterForm from './RegisterForm.vue'
  import ContactData from './ContactData.vue'

  const MODAL_WIDTH = 400;

  export default {
    data() {
      return {
        modalWidth: MODAL_WIDTH,
        modalHeight: 650
      }
    },
    created() {
      this.modalWidth = window.innerWidth < MODAL_WIDTH
        ? MODAL_WIDTH / 2
        : MODAL_WIDTH

      if (this.$refs.appRegisterForm != undefined) {
        this.modalHeight = this.$refs.appRegisterForm.height + 'px'
      }
    },
    components: {
      appLoginForm: LoginForm,
      appRegisterForm: RegisterForm,
      appContactData: ContactData
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
