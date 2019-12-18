<template>
  <div id="wrapper">
    <el-tabs id="tab" tab-position="left">
      <el-tab-pane label="活跃活动">
        <el-card class="card"></el-card>
      </el-tab-pane>
      <el-tab-pane label="历史  活动">
        <el-card class="card"></el-card>
      </el-tab-pane>
      <el-tab-pane label="用户管理">
        <el-card class="card"></el-card>
      </el-tab-pane>
      <el-tab-pane label="活动统计报表">
        <el-card class="card"></el-card>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<style scoped>
#tab {
  width: 80% !important;
  margin-left: auto;
  margin-right: auto;
  margin-top: 30px;
}
.card {
  box-shadow: 0px 0px 5px #ABABAB;
}
</style>

<style>
#tab .el-tabs__item.is-left.is-active {
  color: black;
  font-weight: 900;
}
</style>