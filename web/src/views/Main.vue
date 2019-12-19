<template>
<div>
  <el-menu id="menu" default-active="1" class="el-menu-demo" mode="horizontal" router>
    <li class="el-menu-item title">去玩吧管理平台</li>
    <!--<el-menu-item index="1">处理中心</el-menu-item>-->
    <el-submenu index="2">
      <template slot="title">活动</template>
      <el-menu-item index="/activity/edit">新建活动</el-menu-item>
      <el-menu-item index="/manage">活动管理</el-menu-item>
    </el-submenu>
    <el-menu-item index="/admin" v-if="isAdmin">管理</el-menu-item>
    <li class="el-menu-item right">
      <el-link href="/#/login" v-if="!hasLogin">登录/注册</el-link>
      <div v-else>
        你好，{{username}}/
        <el-link @click="logout">登出</el-link>
      </div>
    </li>
  </el-menu>
  <router-view id="view"></router-view>
</div>
</template>

<script lang="ts">
import {Component, Vue} from 'vue-property-decorator';
import {getModule} from 'vuex-module-decorators';
import UserStore from '../store/user';
import { UserAuth, UserRole } from '../types/user';

@Component({})
export default class MainView extends Vue {
  private userStore = getModule(UserStore, this.$store);

  get hasLogin() {
    return this.userStore.auth !== UserAuth.NotLogin;
  }

  get user() {
    return this.userStore.User;
  }

  get isAdmin() {
    return this.user && this.user.role === UserRole.Manager;
  }

  get username() {
    const user = this.userStore.User;
    return user ? user.username : undefined;
  }

  private logout() {
    this.userStore.logout();
    this.$router.push({
      path: '/login',
    });
  }

  private created() {
    if (!this.hasLogin) {
      this.$router.push({
        path: '/login',
      });
    }
  }

}
</script>

<style lang="css" scope>
  .title {
    font-size: 25px !important;
    color: #555 !important;
  }

  .right {
    float: right !important;
    margin-right: 50px !important;
  }

  #menu {
    padding-left: 10%;
    padding-right: 10%;
    box-shadow: 0px 2px 5px #ABABAB;
  }
</style>