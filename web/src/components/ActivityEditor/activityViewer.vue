<template>
  <div>
    <el-divider class="divider" content-position="left">基本信息</el-divider>
      <el-row>
        <el-col :span="12">
          <el-form label-width="100px" size="small">
            <el-form-item class="clear" label="活动名称">
              {{activity.name}}
            </el-form-item>
            <el-form-item class="clear" label="活动标签">
              <tag
              :tags="tags"
              @submit="handleTagsSubmit"
              class="full-width"/>
            </el-form-item>
            <el-form-item class="clear" label="活动地点">
              {{activity.location}}
            </el-form-item>
            <el-form-item class="clear" label="最大报名人数">
              {{activity.peoplenum}}
            </el-form-item>
            <el-form-item class="clear" label="报名起止时间">
              {{new Date(activity.registerstarttime)}} - {{new Date(activity.registerendtime)}}
            </el-form-item>
            <el-form-item class="clear" label="活动起止时间">
              {{new Date(activity.starttime)}} - {{new Date(activity.endtime)}}
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="12">
          <el-form label-position="top" label-width="100px">
            <el-form-item label="活动海报" style="text-align: left">
              <el-image :src="activity.photourl"></el-image>
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
import { ActivityVO } from '../../types/activity';
import { fetchRichText } from '../../api/file';

@Component({})
export default class ActivityViewer extends Vue {
  @Ref()
  private activity!: ActivityVO;

  private descriptionContent = ''

  private created() {
    fetchRichText(this.activity.descriptionurl)
      .then((content) => this.descriptionContent = content)
  }
}

</script>