<template>
  <div v-if="loadDone">
    <h1>活动编辑</h1>
    <el-divider class="divider" content-position="left">基本信息</el-divider>
      <el-row>
        <el-col :span="12">
          <el-form label-width="100px" size="small">
            <el-form-item v-show="id" label="活动ID">
              {{id}}
            </el-form-item>
            <el-form-item class="clear" label="活动名称">
              <el-input
                v-model="name"
                class="full-width"
                placeholder="请输入活动名称"
              ></el-input>
            </el-form-item>
            <el-form-item class="clear" label="活动标签">
              <tag
              :tags="tags"
              :editable="true"
              @submit="handleTagsSubmit"
              class="full-width"/>
            </el-form-item>
            <el-form-item class="clear" label="活动地点">
              <geo-input
                class="full-width"
                :locationStr="gpsLocation"
                @submit="handleGeoSubmit"
              />
            </el-form-item>
            <el-form-item class="clear" label="">
              <el-input
                class="full-width"
                v-model="detailLocation"
                placeholder="请输入具体地址"
              />
            </el-form-item>
            <el-form-item class="clear" label="最大报名人数">
              <el-input-number
                v-model="peoplenum"
                class="full-width"
              ></el-input-number>
            </el-form-item>
            <el-form-item class="clear" label="报名起止时间">
              <el-date-picker
                v-model="registerTime"
                class="full-width"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始时间"
                end-placeholder="结束时间">
              </el-date-picker>
            </el-form-item>
            <el-form-item class="clear" label="活动起止时间">
              <el-date-picker
                type="datetimerange"
                v-model="activityTime"
                class="full-width"
                range-separator="至"
                start-placeholder="开始时间"
                end-placeholder="结束时间">
              </el-date-picker>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="12">
          <el-form label-position="top" label-width="100px">
            <el-form-item label="活动海报" style="text-align: left">
              <image-uploader
                :imageUrl="photoUrl"
                @submit="handlePhotoSubmit"
              />
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    <div style="height: 10px"/>
    <el-divider class="divider" content-position="left">活动详情</el-divider>
    <div id="description-wrapper">
      <rich-editor
        :defaultContent="description"
        @submit="handleDescriptionSubmit"
      />
    </div>
    <div>
      <el-button type="primary" @click="submit">提交</el-button>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import ImageUploader from './uploader.vue';
import geoInput from './geoInput.vue';
import tag from './tag.vue';
import RichEditor from './richEditor.vue';
import { ActivityAddVO, ActivityVO, ActivityDetailVO } from '../../types/activity';
import { addActivity, updateActivity } from '@/api/activity';
import { uploadRichText, fetchRichText } from '@/api/file';
import { apiErrorMessage } from '../../common/apiErrorMessage';

@Component({
  components: {
    ImageUploader,
    geoInput,
    tag,
    RichEditor,
  },
})
export default class ActivityEditor extends Vue {
  @Prop({default: undefined})
  private activityVO: ActivityDetailVO | undefined;

  get id() {
    if (this.activityVO) {
      return this.activityVO.activity.id;
    }
    return undefined;
  }

  private loadDone = false;

  private name = '';
  private description = '';
  private gps: any;
  private gpsLocation: string = '';
  private detailLocation: string = '';
  private peoplenum = 0;
  private photoUrl = '';
  private tags: string[] = [];

  private registerTime: [Date?, Date?] = [];
  private activityTime: [Date?, Date?] = [];

  private backupActivity: any;

  private handleGeoSubmit(location: any) {
    this.gpsLocation = location.location;
    this.gps = {
      longitude: location.longitude,
      latitude: location.latitude,
    };
  }

  private handleTagsSubmit(tags: string[]) {
    this.tags = tags;
  }

  private handleDescriptionSubmit(content: string) {
    this.description = content;
  }

  private handlePhotoSubmit(url: string) {
    this.photoUrl = url;
  }

  private check() {
    if (!this.name) {
      return '请输入活动名称';
    }
    if (this.tags.length === 0) {
      return '请设置活动标签';
    }
    if (!this.gpsLocation || !this.detailLocation) {
      return '请输入活动地址';
    }
    if (this.peoplenum === 0) {
      return '请设置活动人数';
    }
    if (this.registerTime.length === 0) {
      return '请设置活动报名时间';
    }
    if (this.activityTime.length === 0) {
      return '请设置活动时间';
    }
    if (!this.photoUrl) {
      return '请设置活动海报';
    }
    if (!this.description) {
      return '请设置活动详情';
    }
    return;
  }

  private submit() {
    const checkResult = this.check();
    if (checkResult) {
      this.$message.warning(checkResult);
      return;
    }
    return uploadRichText(this.description)
      .then((url) => {
        const activityVO: ActivityAddVO = {
          name: this.name,
          peoplenum: this.peoplenum,
          starttime: this.activityTime[0]!.valueOf(),
          endtime: this.activityTime[1]!.valueOf(),
          registerstarttime: this.registerTime[0]!.valueOf(),
          registerendtime: this.registerTime[1]!.valueOf(),
          photourl: this.photoUrl,
          descriptionurl: url,
          tags: this.tags,
          location: {
            location: `${this.gpsLocation},${this.detailLocation}`,
            ...this.gps,
          },
        };
        if (!this.id) {
          return addActivity(activityVO);
        } else {
          return updateActivity(this.id, activityVO);
        }
      })
      .then(() => {
          this.$message.success('提交成功');
          this.$router.push({
            path: '/manage',
          });
        })
      .catch((err: any) => {
        apiErrorMessage(this, err);
      });
  }

  private async load(activityVO: ActivityDetailVO) {
    this.backupActivity = activityVO;
    this.name = activityVO.activity.name;
    this.activityTime = [new Date(activityVO.activity.starttime), new Date(activityVO.activity.endtime)];
    this.registerTime = [new Date(activityVO.activity.registerstarttime)
      , new Date(activityVO.activity.registerendtime)];
    [this.gpsLocation, this.detailLocation] = activityVO.activity.location.split(',');
    this.gps = {
      longitude: activityVO.position.longitude,
      latitude: activityVO.position.latitude,
    };
    this.photoUrl = activityVO.activity.photourl;
    this.peoplenum = activityVO.activity.peoplenum;
    this.tags = activityVO.tags;
    this.description = await fetchRichText(activityVO.activity.descriptionurl);
  }

  private created() {
    if (this.activityVO) {
      this.load(this.activityVO)
      .then(() => this.loadDone = true);
    }
    this.loadDone = true;
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
}
</style>