<template>
  <div>
    <div class="alert alert-danger" v-if="badCredentials">Podałeś złe dane</div>
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
    methods: {
      login() {
        this.badCredentials = false
        this.$http.post('http://localhost:8080/login',this.userData)
          .then((response) => {
            this.$http.get('http://localhost:8080/hellosecure')
              .then(() => {
                console.log('Udało się')
                eventBus.$emit('loggedIn')
              },() => {
                console.log('Nie udało sie')
                this.badCredentials = true
              })
          })
      }
    }
  }
</script>
<style lang="css">
</style>
