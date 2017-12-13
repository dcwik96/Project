<template>
  <div>
    <b-form @submit.prvent="register">
      <b-form-group label="Imię: " label-for="name">
        <b-form-input id="name"
                      type="text" required
                      placeholder="Podaj swoje imie"
                      v-model="userDetails.name"
        ></b-form-input>
      </b-form-group>
      <b-form-group label="Nazwisko: " label-for="surname">
        <b-form-input id="surname"
                      type="text" required
                      placeholder="Podaj swoje nazwisko"
                      v-model="userDetails.surname"
        ></b-form-input>
      </b-form-group>
      <b-form-group label="Nazwa użytkownika: " >
        <b-form-input id="name"
                      type="text" required
                      placeholder="Podaj swoje imie"
                      v-model="userDetails.login"
        ></b-form-input>
      </b-form-group>
        <b-form-group label="Adres e-mail: " >
        <b-form-input id="email"
                      type="email" required
                      placeholder="Wprowadź e-mail"
                      v-model="userDetails.email"
        ></b-form-input>
      </b-form-group>
      <b-form-group label="Numer telefonu: " >
        <b-form-input id="phone_number"
                      type="text" required
                      placeholder="Podaj numer telefonu"
                      v-model="userDetails.phone_number"
        ></b-form-input>
      </b-form-group>
      <b-form-group
        label="Hasło: " label-for="pass1">
        <b-form-input id="pass1"
                      type="password" required
                      placeholder="Podaj hasło"
                      v-model="userDetails.password"
        ></b-form-input>
      </b-form-group>
      <b-button type="submit" variant="primary">Zaloguj</b-button>
      <b-button type="reset" variant="secondary">Reset</b-button>
    </b-form>
  </div>
</template>
<script>
export default {
  data() {
    return {
        userDetails: {
          name: '',
          surname: '',
          login: '',
          email: '',
          phone_number: '',
          password: '',
        }
    }
  },
  methods: {
    register() {
      this.$http.post('http://localhost:8080/registration', this.userDetails)
        .then(() => {
          console.log('poszło')
        },
          (response) => {
          console.log(response)
          })
    }
  }
}
</script>
<style lang="css">
  .nav-link {
    background-color: #000000;
  }
</style>
