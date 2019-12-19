<template>
  <div id="wrapper">
    <el-tabs id="tab" tab-position="left">
      <el-tab-pane label="当前活动">
        <el-card class="card">
          <active-activity :activities="activeActivities" />
        </el-card>
      </el-tab-pane>
      <el-tab-pane label="活动统计报表">
        <el-card class="card">
          <history-activity :activities="historyActivities" />
        </el-card>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script lang="ts">
import {Component, Vue} from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';
import UserStore from '../store/user';
import { getActivityOfUser } from '../api/activity';
import { ActivityVO, ActivityStatus } from '@/types/activity';
import { apiErrorMessage } from '../common/apiErrorMessage';
import ActiveActivity from '@/components/ActivityEditor/activeActivityManager.vue';
import HistoryActivity from '@/components/ActivityEditor/historyActivityManager.vue';

@Component({
  components: {
    ActiveActivity,
    HistoryActivity,
  },
})
export default class OrganizerView extends Vue {
  private userStore = getModule(UserStore, this.$store);
  private activities: ActivityVO[] = [];

  get user() {
    return this.userStore.User;
  }

  get activeActivities() {
    return this.activities.filter((a) => a.status !== ActivityStatus.End);
  }

  get historyActivities() {
    return this.activities.filter((a) => a.status === ActivityStatus.End);
  }

  private loadActivity() {
    if (this.user) {
      getActivityOfUser(this.user.id)
        .then((activities) => {
          this.activities = activities;
        })
        .catch((err: any) => {
          apiErrorMessage(this, err);
        });
    } else {
      this.activities = [];
    }
  }

  private created() {
    this.loadActivity();
  }
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