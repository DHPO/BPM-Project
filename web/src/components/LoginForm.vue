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
      <el-alert v-if="showLoginAlert" class="alarm" type="error" :title="loginAlertMessage" :closable="false"></el-alert>
      <div></div>
      <el-button class="button" type="primary" @click="login" v-loading="buttonLoading">登录</el-button>
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
      <el-alert v-if="showRegisterAlert" class="alarm" type="error" :title="registerAlertMessage" :closable="false"></el-alert>
      <div></div>
      <el-button class="button" type="primary" @click="register" v-loading="buttonLoading">注册</el-button>
    </el-tab-pane>
  </el-tabs>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';
import UserStore from '../store/user';
import { ResultStatus } from '../api/user';

@Component
export default class HelloWorld extends Vue {
  @Prop() private msg!: string;

  private userStore = getModule(UserStore, this.$store);

  private username: string = '';
  private password: string = '';
  private repeatPassword: string = '';

  private loginAlertMessage: string = '';
  private registerAlertMessage: string = '';
  private buttonLoading: boolean = false;

  get showLoginAlert() {
    return this.loginAlertMessage !== '';
  }

  get showRegisterAlert() {
    return this.registerAlertMessage !== '';
  }

  private validateLogin() {
    if (this.username === '') {
      this.loginAlertMessage = '用户名不能为空';
      return false;
    } else if (this.password === '') {
      this.loginAlertMessage = '密码不能为空';
      return false;
    } else {
      return true;
    }
  }

  private validateRegister() {
    if (this.username === '') {
      this.registerAlertMessage = '用户名不能为空';
      return false;
    } else if (this.password === '') {
      this.registerAlertMessage = '密码不能为空';
      return false;
    } else if (this.password !== this.repeatPassword) {
      this.registerAlertMessage = '两次输入的密码不一致';
      return;
    } else {
      return true;
    }
  }

  private async login() {
    if (!this.validateLogin()) {
      return;
    }
    this.buttonLoading = true;
    this.userStore.login({
      username: this.username,
      password: this.password,
    }).then((user) => {
      this.loginAlertMessage = '';
      this.$message({
        message: '登录成功',
        type: 'success',
      });
    }).catch((err: ResultStatus) => {
      switch (err) {
        case ResultStatus.PasswordError: {
          this.loginAlertMessage = '密码错误';
          break;
        }
        case ResultStatus.UserNotExist: {
          this.loginAlertMessage = '用户不存在';
          break;
        }
        case ResultStatus.LockedAccount: {
          this.loginAlertMessage = '用户被锁定';
          break;
        }
        case ResultStatus.SystemError: {
          this.loginAlertMessage = '系统错误';
          break;
        }
        default: {
          this.loginAlertMessage = `其他错误：${err}`;
        }
      }
    }).finally(() => {
      this.buttonLoading = false;
    });
  }

  private async register() {
    if (!this.validateRegister()) {
      return;
    }
    this.buttonLoading = true;
    this.userStore.register({
      username: this.username,
      password: this.password,
    }).finally(() => {
      this.buttonLoading = false;
    });
  }
}
</script>

<style lang="css" scope>
.input, .alarm {
  margin-top: 15px;
  margin-bottom: 10px;
  width: 80% !important;
}

.alarm {
  margin-top: 15px !important;
  margin-bottom: 10px !important;
  margin-left: 10% !important;
}

.input-prepend {
  width: 80px;
}

.button {
  margin-top: 10px !important;
  margin-bottom: 10px !important;
}
</style>