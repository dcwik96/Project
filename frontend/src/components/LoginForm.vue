<template>
  <div>

    <form @submit.prevent="login" style="padding: 50px;">
  <div class="form-group">
    <label for="username">Nazwa użytkownika</label>
    <input id="username" type="text" class="form-control" placeholder="Wprowadź nazwę użytkownika" v-model="userData.username">
  </div>
  <div class="form-group">
    <label for="password">Hasło</label>
    <input id="password" type="password" class="form-control"  placeholder="Wprowadź hasło" v-model="userData.password">
  </div>


  <button type="submit" variant="primary" class="btn btn-success">Zaloguj</button>
  <button type="reset" variant="secondary" class="btn btn-default">Wyczyść</button>
  <br/><br/>
      <a class="link" @click="goToRegister">Nie masz jeszcze konta</a>
  <div class="alert alert-danger" variant="danger" v-show="badCredentials" role="alert">Niepoprawne dane logowania!</div>
</form>

  </div>
</template>
<script>
  export default {
    data() {
      return {
        userData: {
          username: '',
          password: ''
        },
        badCredentials: false,
        registerPath: { path:'register' }
      }
    },
    methods: {
      login() {
        this.$http.post('http://localhost:8080/login', this.userData)
          .then(() => {
              this.$cookie.set('login', this.userData.username, 1)
              this.$store.dispatch('setUsername', this.userData.username)
              this.hide()
              this.$router.go({name: 'home'})
          }, () => {
              this.badCredentials = true
          })
      },
      hide() {
        this.$modal.hide('login')
      },
      goToRegister() {
        this.$modal.hide('login')
        this.$modal.show('register')
      }
    }
  }
</script>
<style lang="css">
</style>
