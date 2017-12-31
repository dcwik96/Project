<template>
  <div>

    <form @submit.prevent="register" style="padding: 50px;">
      <div class="form-group">
        <label for="name">Imię:</label>
        <input id="name" v-model="userDetails.name" type="text" class="form-control" placeholder="Wprowadź imię">
      </div>
      <div class="form-group">
        <label for="surname">Nazwisko:</label>
        <input id="surname" v-model="userDetails.surname" type="text" class="form-control"
               placeholder="Wprowadź nazwisko">
      </div>
      <div class="form-group">
        <label for="username">Nazwa użytkownika:</label>
        <input id="username" v-model="userDetails.login" type="text" class="form-control"
               placeholder="Wprowadź nazwę użytkownika">
      </div>
      <div class="form-group">
        <label for="email">Adres email:</label>
        <input id="email" v-model="userDetails.email" type="email" class="form-control"
               placeholder="Wprowadź adres email">
      </div>
      <div class="form-group">
        <label for="phoneNumber">Numer telefonu:</label>
        <input id="phoneNumber" v-model="userDetails.phone_number" type="text" class="form-control"
               placeholder="Wprowadź numer telefonu">
      </div>
      <div class="form-group">
        <label for="password">Hasło:</label>
        <input id="password" v-model="userDetails.password" type="password" class="form-control"
               placeholder="Wprowadź hasło">
      </div>
      <button type="submit" variant="primary" class="btn btn-success">Zarejestruj</button>
      <button type="reset" variant="secondary" class="btn btn-primary">Wyczyść</button>
      <div class="alert alert-danger" v-if="dataError">{{ message }}</div>
    </form>
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
        },
        dataError: false,
        message: ''
      }
    },
    methods: {
      register() {
        this.$http.post('http://localhost:8080/registration', this.userDetails, {
          emulateJSON : true
        })
          .then(() => {
            this.message = 'Twoje konto zostało założne możesz przejść do logwania.'
          }, response => {
            this.dataError = true
            this.message = response.bodyText
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
