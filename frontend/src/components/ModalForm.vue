<template>
  <div>
    <button
      @click="$modal.show('login')">
      modal
    </button>
    <login-modal></login-modal>
  </div>
</template>
<script>
  import Modal from './Modal.vue'

  export default {
    components: {LoginModal: Modal},
  }
</script>
<style lang="css">
</style>
