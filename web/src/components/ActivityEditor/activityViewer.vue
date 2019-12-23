<template>
  <div>
    <el-divider class="divider" content-position="left">基本信息</el-divider>
      <el-row>
        <el-col :span="14">
          <el-form label-width="100px" size="small">
            <el-form-item class="clear" label="活动名称">
              {{name}}
            </el-form-item>
            <el-form-item class="clear" label="活动状态">
              <status-tag :status="status" />
            </el-form-item>
            <el-form-item class="clear" label="活动标签">
              <tag
              :tags="tags"
              class="full-width"/>
            </el-form-item>
            <el-form-item class="clear" label="活动地点">
              {{location}}
            </el-form-item>
            <el-form-item class="clear" label="报名人数">
              {{registernum}} / {{peoplenum}}
            </el-form-item>
            <el-form-item class="clear" label="报名起止时间">
              {{new Date(registerStartTime).toLocaleString()}} - {{new Date(registerEndTime).toLocaleString()}}
            </el-form-item>
            <el-form-item class="clear" label="活动起止时间">
              {{new Date(activityStartTime).toLocaleString()}} - {{new Date(activityEndTime).toLocaleString()}}
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="10">
          <el-form label-position="top" label-width="100px">
            <el-form-item label="活动海报" style="text-align: left">
              <el-image :src="photoUrl"></el-image>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    <div style="height: 10px"/>
    <el-divider class="divider" content-position="left">活动详情</el-divider>
    <div id="description-wrapper">
      <div v-html="descriptionContent"></div>
    </div>
  </div>
</template>

<script lang="ts">
import {Component, Vue, Ref, Prop} from 'vue-property-decorator';
import { ActivityDetailVO } from '../../types/activity';
import { fetchRichText } from '../../api/file';
import tag from './tag.vue';
import StatusTag from './activityStatusTag.vue';

@Component({
  components: {
    tag,
    StatusTag,
  },
})
export default class ActivityViewer extends Vue {
  @Prop()
  private activity!: ActivityDetailVO;

  private descriptionContent = '';

  get name() {
    return this.activity.activity.name;
  }

  get tags() {
    return this.activity.tags;
  }

  get location() {
    return this.activity.activity.location;
  }

  get peoplenum() {
    return this.activity.activity.peoplenum;
  }

  get registernum() {
    return this.activity.activity.registernum;
  }

  get activityStartTime() {
    return this.activity.activity.starttime;
  }

  get activityEndTime() {
    return this.activity.activity.endtime;
  }

  get registerStartTime() {
    return this.activity.activity.registerstarttime;
  }

  get registerEndTime() {
    return this.activity.activity.registerendtime;
  }

  get photoUrl() {
    return this.activity.activity.photourl;
  }

  get status() {
    return this.activity.activity.status;
  }

  private created() {
    fetchRichText(this.activity.activity.descriptionurl)
      .then((content) => this.descriptionContent = content);
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