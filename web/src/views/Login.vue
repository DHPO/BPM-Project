<template>
  <div id="login-view-container">
    <LoginForm id="login-form"/>
  </div>
</template>

<script>
import LoginForm from '../components/LoginForm';
import { Component, Vue } from 'vue-property-decorator';

@Component({
  components: {
    LoginForm,
  },
})
export default class LoginView extends Vue {}
</script>

<style lang="css">
  #login-view-container {
    padding-top: 100px;
  }

  #login-form {
    width: 50%;
    margin-left: 25%;
  }
</style>