<template>
  <div>
    <b-form @submit.prevent="login">
      <b-alert variant="danger" :show="badCredentials">Niepoprawny login lub hasło</b-alert>
      <b-form-group label="Nazwa użytkownika: " label-for="username">
        <b-form-input id="username"
                      type="text" required
                      placeholder="Podaj nazwę użytkownika"
                      v-model="userData.username"
        ></b-form-input>
      </b-form-group>
      <b-form-group
                    label="Hasło: " label-for="password">
        <b-form-input id="password"
                      type="password"  required
                      placeholder="Podaj hasło"
                      v-model="userData.password"
        ></b-form-input>
      </b-form-group>
      <b-button type="submit" variant="primary">Zaloguj</b-button>
      <b-button type="reset" variant="secondary">Reset</b-button>
    </b-form>
    <br>
    <b-link :to="registerPath" @click="hide">Nie masz jeszcze konta?</b-link>
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
        badCredentials: false,
        registerPath: { path:'register' }
      }
    },
    methods: {
      login() {
        this.$http.post('http://localhost:8080/login', this.userData)
          .then(() => {
              this.$cookie.set('login', 'There will be user data', 1)
              this.$router.go({name: 'home'})
              this.hide()
          }, () => {
              this.badCredentials = true
          })
      },
      hide() {
        this.$modal.hide('login')
      }
    }
  }
</script>
<style lang="css">
</style>
