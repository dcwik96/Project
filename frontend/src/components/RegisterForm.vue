<template>
  <div>
    <form @submit.prevent="register" style="padding: 50px;">
  <div class="form-group">
    <label for="name">Imię:</label>
    <input v-model="userDetails.name" type="text" class="form-control" placeholder="Wprowadź imię">
  </div>
  <div class="form-group">
    <label for="surname">Nazwisko:</label>
    <input v-model="userDetails.surname" type="text" class="form-control" placeholder="Wprowadź nazwisko">
  </div>
  <div class="form-group">
    <label for="username">Nazwa użytkownika:</label>
    <input v-model="userDetails.login" type="text" class="form-control" placeholder="Wprowadź nazwę użytkownika">
  </div>
  <div class="form-group">
    <label for="email">Adres email:</label>
    <input v-model="userDetails.email" type="email" class="form-control" placeholder="Wprowadź adres email">
  </div>
  <div class="form-group">
    <label for="phoneNumber">Numer telefonu:</label>
    <input v-model="userDetails.phone_number" type="email" class="form-control" placeholder="Wprowadź numer telefonu">
  </div>
  <div class="form-group">
    <label for="password">Hasło:</label>
    <input v-model="userDetails.password" type="password" class="form-control"  placeholder="Wprowadź hasło">
  </div>

  <button type="submit" variant="primary" class="btn btn-success">Zarejestruj</button>
  <button type="reset" variant="secondary" class="btn btn-primary">Wyczyść</button>

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
