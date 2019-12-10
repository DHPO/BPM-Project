<template>
<div>
  <el-menu default-active="1" class="el-menu-demo" mode="horizontal">
    <li class="el-menu-item title">去玩吧</li>
    <el-menu-item index="1">处理中心</el-menu-item>
    <el-submenu index="2">
      <template slot="title">我的工作台</template>
      <el-menu-item index="2-1">选项1</el-menu-item>
      <el-menu-item index="2-2">选项2</el-menu-item>
      <el-menu-item index="2-3">选项3</el-menu-item>
      <el-submenu index="2-4">
        <template slot="title">选项4</template>
        <el-menu-item index="2-4-1">选项1</el-menu-item>
        <el-menu-item index="2-4-2">选项2</el-menu-item>
        <el-menu-item index="2-4-3">选项3</el-menu-item>
      </el-submenu>
    </el-submenu>
    <el-menu-item index="3" disabled>消息中心</el-menu-item>
    <li class="el-menu-item right">
      <el-avatar icon="el-icon-user-solid"></el-avatar>
    </li>
  </el-menu>
  <router-view></router-view>
</div>
</template>

<style lang="css" scope>
  .title {
    font-size: 25px !important;
    color: #555 !important;
  }

  .right {
    float: right !important;
    margin-right: 50px !important;
  }
</style>