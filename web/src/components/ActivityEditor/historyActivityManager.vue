<template>
  <div>
    <h1>活动报表查看</h1>

    <el-table :data="activities">
      <el-table-column type="expand">
        <template slot-scope="scope">
          <el-row>
            <el-col :span="14">
              <el-form label-width="100px" size="small" label-position="left">
                <el-form-item class="clear" label="活动名称">
                  {{scope.row.name}}
                </el-form-item>
                <el-form-item class="clear" label="活动状态">
                  <status-tag :status="scope.row.status" />
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
      <el-table-column label="状态">
        <template slot-scope="scope">
          <status-tag :status="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="开始时间">
        <template slot-scope="scope">
          {{new Date(scope.row.starttime).toLocaleString()}}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="openStatisticLink(scope.row.id)"><i class="el-icon-pie-chart"/>查看活动报表</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script lang="ts">
import {Component, Vue, Prop} from 'vue-property-decorator';
import { ActivityVO } from '../../types/activity';
import StatusTag from './activityStatusTag.vue';

@Component({
  components: {
    StatusTag,
  },
})
export default class ActiveActivityManager extends Vue {
  @Prop()
  private activities!: ActivityVO[];

  private openDetailLink(activityId: number) {
    const url = this.$router.resolve({
      path: `/activity/${activityId}`,
    });
    window.open(url.href, '_blank');
  }

  private openStatisticLink(activityId: number) {
    const url = this.$router.resolve({
      path: `/statistics/${activityId}`,
    });
    window.open(url.href, '_blank');
  }
}
</script>