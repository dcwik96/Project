<template>
  <div>
    <form @submit.prevent="login" style="padding: 50px;">
  <div class="form-group">
    <label for="username">Nazwa użytkownika</label>
    <input id="username" type="text" class="form-control" placeholder="Wprowadź nazwę użytkownika" v-model="userData.username">
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">Hasło</label>
    <input id="password" type="password" class="form-control"  placeholder="Wprowadź hasło" v-model="userData.password">
  </div>


  <button type="submit" variant="primary" class="btn btn-success">Zaloguj</button>
  <button type="reset" variant="secondary" class="btn btn-default">Wyczyść</button>
  <br><br>
  <div class="alert alert-danger" variant="danger" v-show="badCredentials" role="alert">Niepoprawne dane logowania!</div>
</form>
  </div>
</template>
<script>
  import {eventBus} from "../main"
  export default {
    data() {
      return {
        userData: {
          username: '',
          password: ''
        },
        registerState: '',
        badCredentials: false
      }
    },
    created() {

    },
    methods: {
      login() {
        this.$http.post('http://localhost:8080/login', this.userData)
          .then(() => {
              this.$cookie.set('login', 'G4D74V98CJTY8JNFCH6J2QU2', 1)
              eventBus.$emit('loggedIn')
          }, () => {
              this.badCredentials = true
              eventBus.$emit('loginFailed')
          })
      }
    }
  }
</script>
<style lang="css">
</style>
