<template>
  <div>
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
        }
      }
    },
    methods: {
      login() {
        this.$http.post('http://localhost:8080/login',this.userData)
          .then((response) => {
           if (response.status == 200) {
             eventBus.$emit('loggedIn')
           }
          })
          .then((request) => {
            console.log(request)
          })
      }
    }
  }
</script>
<style lang="css">
</style>
