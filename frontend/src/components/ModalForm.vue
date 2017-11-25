<template>
  <div>
    <button
      @click="$modal.show('demo-login')">
      modal
    </button>
    <demo-login-modal></demo-login-modal>
  </div>
</template>
<script>
  import Modal from './Modal.vue'

  export default {
    components: {DemoLoginModal: Modal},
    methods: {
      show: function () {
        this.$modal.show('dialog')
      }
    }
  }
</script>
<style lang="css">
</style>
