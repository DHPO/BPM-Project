<template>
  <div>
    <h1>活动审批</h1>
    <el-table :data="activities" v-loading="loading">
      <el-table-column type="expand">
        <template slot-scope="scope">
          <el-row>
            <el-col :span="14">
              <el-form label-width="100px" size="small" label-position="left">
                <el-form-item class="clear" label="活动名称">
                  {{scope.row.name}}
                </el-form-item>
                <el-form-item class="clear" label="活动地点">
                  {{scope.row.location}}
                </el-form-item>
                <el-form-item class="clear" label="最大报名人数">
                  {{scope.row.peoplenum}}
                </el-form-item>
                <el-form-item class="clear" label="报名起止时间">
                  {{new Date(scope.row.registerstarttime).toLocaleString()}} - {{new Date(scope.row.registerendtime).toLocaleString()}}
                </el-form-item>
                <el-form-item class="clear" label="活动起止时间">
                  {{new Date(scope.row.starttime).toLocaleString()}} - {{new Date(scope.row.endtime).toLocaleString()}}
                </el-form-item>
                <el-form-item label="">
                  <el-button @click="openDetailLink(scope.row.id)">查看详情</el-button>
                </el-form-item>
              </el-form>
            </el-col>
            <el-col :span="10">
              <el-form label-position="top" label-width="100px">
                <el-form-item label="活动海报" style="text-align: left">
                  <el-image :src="scope.row.photourl"></el-image>
                </el-form-item>
              </el-form>
            </el-col>
          </el-row>
        </template>
      </el-table-column>
      <el-table-column label="活动名称" prop="name"></el-table-column>
      <el-table-column label="活动地点" prop="location"></el-table-column>
      <el-table-column label="提交时间">
        <template slot-scope="scope">
          {{new Date(scope.row.addtime).toLocaleString()}}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="approve(scope.row.id, true)"><i class="el-icon-check"/>通过</el-button>
          <el-button size="mini" type="danger" @click="approve(scope.row.id, false)"><i class="el-icon-close"/>不通过</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script lang="ts">
import {Component, Vue} from 'vue-property-decorator';
import { ActivityVO } from '../../types/activity';
import { getCheckList, approveActivity } from '../../api/activity';
import { apiErrorMessage } from '../../common/apiErrorMessage';

@Component({})
export default class Approval extends Vue {
  private activities: ActivityVO[] = [];

  private loading = false;

  private loadActivities() {
    this.loading = true;
    getCheckList()
      .then((activities) => this.activities = activities)
      .catch((err: any) => apiErrorMessage(this, err))
      .finally(() => this.loading = false);
  }

  private created() {
    this.loadActivities();
  }

  private openDetailLink(activityId: number) {
    const url = this.$router.resolve({
      path: `/activity/${activityId}`,
    });
    window.open(url.href, '_blank');
  }

  private approve(activityId: number, approve: boolean) {
    return approveActivity(activityId, approve)
      .then(() => {
        this.$message.success('提交成功');
        this.loadActivities();
      })
      .catch((err: any) => {
        apiErrorMessage(this, err);
      });
  }
}
</script>

<style scoped>
.clear {
  clear: both;
  width: 80%;
  height: max-content;
}

.full-width {
  width: 90% !important;
  margin-left: auto;
  margin-right: auto;
}

#description-wrapper {
  margin: 0px 40px;
  text-align: left;
}
</style>