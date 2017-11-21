<template>
  
  <!-- <ul v-if="adverts && adverts.length" class="col-sm-12">
    <li v-for="advert in adverts">
      <p><strong>{{advert.title}}</strong></p>
    </li>
  </ul>
  <ul v-else>
    <li v-for="error of errors">
      {{error.message}}
    </li>
  </ul> -->
</template>

<script>
import axios from 'axios'
export default {
  name: 'Item',
  data () {
    return {
      adverts: [],
      errors: []
    }
  },
  created() {
    var config = {
      headers: {'Accept': 'application/json',
                'Content-Type': 'application/json',
                "Access-Control-Allow-Origin": "*"
      }
    }
    axios.get('http://localhost:8080/api/adverts', {},config)
    .then(response => {
      this.adverts = response.data
    })
    .catch(e => {
      this.errors.push(e)
    })
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only
<style scoped>
h1, h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
 -->