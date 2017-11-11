<template>
  <div class="Item">
    <div class="card" style="width: 200px;">
          <h7 class="card-title">Komputer stacjonarny!</h7>
          <img class="card-img-top"
               src="https://media.gcflearnfree.org/content/55e0730c7dd48174331f5164_01_17_2014/desktop_mac_full_view_alt.jpg"
               alt="Card image cap">
          <div class="card-body">
            <p class="card-text">Pozostało czasu: xx:xx:xx</p>
            <a href="#" class="btn btn-primary">Zobacz więcej</a>
          </div>
    </div>
    </div>
</template>

<script>
export default {
  name: 'Item',
  data () {
    return {
      msg: 'Item'
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
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
