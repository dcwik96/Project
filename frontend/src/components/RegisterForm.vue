<template>
  <div>
    <b-form>
      <b-form-group label="Nazwa użytkownika: " label-for="username">
        <b-form-input id="username"
                      type="text" required
                      placeholder="Wprowadź nazwę użytkownika"
        ></b-form-input>
      </b-form-group>
        <b-form-group label="Adres e-mail: " label-for="username">
        <b-form-input id="email"
                      type="email" required
                      placeholder="Wprowadź e-mail"
        ></b-form-input>
      </b-form-group>
      <b-form-group
        label="Hasło: " label-for="pass1">
        <b-form-input id="pass1"
                      type="password" required
                      placeholder="Podaj hasło"
        ></b-form-input>
      </b-form-group>
      <b-form-group
        label="Powtórz hasło" label-for="pass2">
        <b-form-input id="pass2"
                      type="password" required
                      placeholder="Wprowadź jeszcze raz to samo hasło"
        ></b-form-input>
      </b-form-group>
      <b-button type="submit" variant="primary">Zaloguj</b-button>
      <b-button type="reset" variant="secondary">Reset</b-button>
    </b-form>
  </div>
</template>
<script>

</script>
<style lang="css">
  .nav-link {
    background-color: #000000;
  }
</style>
