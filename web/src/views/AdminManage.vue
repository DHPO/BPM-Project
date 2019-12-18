<template>
  <div id="wrapper">
    <el-tabs id="tab" tab-position="left">
      <el-tab-pane label="活动审批">
        <el-card class="card">
          <approval />
        </el-card>
      </el-tab-pane>
      <el-tab-pane label="活动管理">
        <el-card class="card">
          <activity-manage />
        </el-card>
      </el-tab-pane>
      <el-tab-pane label="用户管理">
        <el-card class="card">
          <UserManage />
        </el-card>
      </el-tab-pane>
      <el-tab-pane label="活动统计报表">
        <el-card class="card"></el-card>
      </el-tab-pane>
      <el-tab-pane label="用户统计报表">
        <el-card class="card"></el-card>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script lang="ts">
import {Component, Vue} from 'vue-property-decorator';
import Approval from '@/components/ActivityEditor/approval.vue';
import ActivityManage from '@/components/ActivityEditor/activityManage.vue';
import UserManage from '@/components/user/userManage.vue';

@Component({
  components: {
    Approval,
    ActivityManage,
    UserManage,
  },
})
export default class AdminManageView extends Vue {

}
</script>

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