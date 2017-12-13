<template>
  <div>
    <b-alert variant="danger" v-show="badCredentials">Niepoprawny login lub hasło</b-alert>
    <b-form @submit.prevent="login">
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
