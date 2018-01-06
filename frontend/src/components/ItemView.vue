<template>
  <div>Testdupa ID: {{$route.params.id}}</div>



</template>

<script>
  export default {
    data () {
      return {
        params: {
        id:
          this.$route.params.id

        },
      }

    }


  }
</script>
