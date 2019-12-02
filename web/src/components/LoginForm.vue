<template>
  <el-tabs type="border-card" stretch>
    <el-tab-pane label="登录">
      <h1>登录</h1>
      <el-input class="input" v-model="username" placeholder="请输入用户名">
        <div slot="prepend" class="input-prepend">用户名</div>
      </el-input>
      <el-input class="input" v-model="password" placeholder="请输入密码" type="password">
        <div slot="prepend" class="input-prepend">密码</div>
      </el-input>
      <div></div>
      <el-button class="button" type="primary" @click="login">登录</el-button>
    </el-tab-pane>
    <el-tab-pane label="注册">
      <h1>注册</h1>
      <el-input class="input" v-model="username" placeholder="请输入用户名">
        <div slot="prepend" class="input-prepend">用户名</div>
      </el-input>
      <el-input class="input" v-model="password" placeholder="请输入密码" type="password">
        <div slot="prepend" class="input-prepend">密码</div>
      </el-input>
      <el-input class="input" v-model="repeatPassword" placeholder="请再次输入密码" type="password">
        <div slot="prepend" class="input-prepend">密码</div>
      </el-input>
      <div></div>
      <el-button class="button" type="primary" @click="register">注册</el-button>
    </el-tab-pane>
  </el-tabs>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';

@Component
export default class HelloWorld extends Vue {
  @Prop() private msg!: string;

  private username: string = '';
  private password: string = '';
  private repeatPassword: string = '';

  private async login() {
    console.log(this.username, this.password);
  }

  private async register() {
    console.log(this.username, this.password)
  }

}

</script>

<style lang="css" scope>
  .input {
    margin-top:15px;
    margin-bottom: 10px;
    width: 80% !important;
  }

  .input-prepend {
    width: 80px;
  }

  .button {
    margin-top: 10px !important;
    margin-bottom: 10px !important;
  }

</style>