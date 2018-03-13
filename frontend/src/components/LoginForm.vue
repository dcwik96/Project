<template>
  <div>
    <form @submit.prevent="$store.dispatch('login',userData).then(this.$router.go({name: 'home'}))" style="padding: 50px;">
      <div class="form-group">
        <label for="username">Nazwa użytkownika</label>
        <input id="username" type="text" class="form-control" placeholder="Wprowadź nazwę użytkownika"
               v-model="userData.username">
      </div>
      <div class="form-group">
        <label for="password">Hasło</label>
        <input id="password" type="password" class="form-control" placeholder="Wprowadź hasło"
               v-model="userData.password">
      </div>
      <button type="submit" variant="primary" class="btn btn-success">Zaloguj</button>
      <button type="reset" variant="secondary" class="btn btn-default">Wyczyść</button>
      <br/><br/>
      <a class="link" @click="goToRegister">Nie masz jeszcze konta</a>
    </form>
  </div>
</template>
<script>
  import {mapActions} from 'vuex'
  export default {
    data() {
      return {
        userData: {
          username: '',
          password: ''
        },
        registerPath: {path: 'register'}
      }
    },
    methods: {
      ...mapActions['login'],
      hide() {
        this.$modal.hide('login')
      },
      goToRegister() {
        this.$modal.hide('login');
        this.$modal.show('register')
      }
    }
  }
</script>
<style lang="css">
</style>
