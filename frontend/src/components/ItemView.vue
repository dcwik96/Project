<template>
  <div>Testdupa ID: {{$route.params.id}}
    {{adverts}}

  </div>



</template>

<script>
  import {mapGetters} from 'vuex'
  export default {
    data () {
      return {
        params: {
        id:
          this.$route.params.id
        }

      }

    },
    computed: {
      ...mapGetters({adverts: 'getArrayOfAdverts'})
    }


  }
</script>
