<template>
  <div>Testdupa ID: {{$route.params.id}}
    {{advert}}
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'
  export default {
    data () {
      return {
        id: '',
        advert: {}
      }

    },
    computed: {
      ...mapGetters({adverts: 'getArrayOfAdverts'}),
    },
    created() {
      this.getAdvertById(this.$route.params.id)
    },
    methods: {
      getAdvertById(id) {
        for(var i = 0; i < this.adverts.length; ++i) {
          if(this.adverts[i].id == id ) {
            this.advert = this.adverts[i]
            return
          }
        }
      }
    }
  }
</script>
