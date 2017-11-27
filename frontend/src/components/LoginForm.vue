<template>
  <div>
    <b-form>
      <b-form-group label="Adres e-mail: " label-for="email">
        <b-form-input id="email"
                      type="email" required
                      placeholder="Enter email"
        ></b-form-input>
      </b-form-group>
      <b-form-group
                    label="Hasło: " label-for="password">
        <b-form-input id="exampleInput2"
                      type="password"  required
                      placeholder="Podaj hasło"
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
</style>
