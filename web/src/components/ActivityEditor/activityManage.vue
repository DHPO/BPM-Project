<template>
  <div>
    <h1>活动管理</h1>

    <div id="search-input">
      <el-input v-model="searchText" placeholder="输入查询条件">
        <div style="width: 100px" slot="prepend">
          <el-select v-model="searchOption">
            <el-option value="keyword" label="关键字"></el-option>
            <el-option value="tag" label="标签"></el-option>
          </el-select>
        </div>
        <el-button slot="append" @click="search">查询</el-button>
      </el-input>
    </div>

    <div style="height: 30px"></div>
    <el-table :data="activities" v-loading="loading">
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
import { getCheckList, approveActivity, getActivityByTag, getActivityByKeyword } from '../../api/activity';
import { apiErrorMessage } from '../../common/apiErrorMessage';
import StatusTag from './activityStatusTag.vue';

@Component({
  components: {
    StatusTag,
  },
})
export default class Approval extends Vue {
  private activities: ActivityVO[] = [];

  private searchText = '';

  private loading = false;

  private searchOption = 'keyword';

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
        this.search();
      })
      .catch((err: any) => {
        apiErrorMessage(this, err);
      });
  }

  private search() {
    let searchPromise;
    this.loading = true;
    if (this.searchOption === 'tag') {
      searchPromise = getActivityByTag(this.searchText);
    } else {
      searchPromise = getActivityByKeyword(this.searchText);
    }
    searchPromise
      .then((activities) => this.activities = activities)
      .catch((err: any) => apiErrorMessage(this, err))
      .finally(() => this.loading = false);
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

#search-input {
  width: 60%;
  margin-left: auto;
  margin-right: auto;
}
</style>