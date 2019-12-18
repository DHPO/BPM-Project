<template>
  <div>
    <el-divider class="divider" content-position="left">基本信息</el-divider>
      <el-row>
        <el-col :span="12">
          <el-form label-width="100px" size="small">
            <el-form-item class="clear" label="活动名称">
              {{name}}
            </el-form-item>
            <el-form-item class="clear" label="活动标签">
              <tag
              :tags="tags"
              @submit="handleTagsSubmit"
              class="full-width"/>
            </el-form-item>
            <el-form-item class="clear" label="活动地点">
              {{location}}
            </el-form-item>
            <el-form-item class="clear" label="最大报名人数">
              {{peoplenum}}
            </el-form-item>
            <el-form-item class="clear" label="报名起止时间">
              {{new Date(registerStartTime)}} - {{new Date(registerEndTime)}}
            </el-form-item>
            <el-form-item class="clear" label="活动起止时间">
              {{new Date(activityStartTime)}} - {{new Date(activityEndTime)}}
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="12">
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
import {Component, Vue, Ref} from 'vue-property-decorator';
import { ActivityDetailVO } from '../../types/activity';
import { fetchRichText } from '../../api/file';

@Component({})
export default class ActivityViewer extends Vue {
  @Ref()
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

  private created() {
    fetchRichText(this.activity.activity.descriptionurl)
      .then((content) => this.descriptionContent = content);
  }
}
</script>