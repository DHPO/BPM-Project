<template>
  <el-dialog :visible.sync="dialogVisible" title="新建页面">
    <el-form id="form" label-position="left" label-width="100px" size="small">
      <el-form-item label="页面类型">
        <el-select v-model="type" placeholder="请选择页面类型">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="页面名称">
        <el-input v-model="name" placeholder="请输入页面名称"></el-input>
      </el-form-item>
      <!--问卷-->
      <template v-if="showQuestionnare">
        <el-form-item label="问卷星ID">
          <el-input v-model="questionnaireId" placeholder="请输入问卷星ID"></el-input>
        </el-form-item>
      </template>
      <!--直播-->
      <template v-if="showVideo">
        <el-form-item label="直播链接">
          <el-input v-model="videoUrl" placeholder="请输入直播链接"></el-input>
        </el-form-item>
      </template>
      <el-form-item>
        <el-button type="primary" @click="submit" :disabled="!submitable" :loading="loading">确定</el-button>
        <el-button @click="dialogVisible=false">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import {
  InteractiveType,
  QuestionnaireConfig,
  InteractiveConfig,
  VideoConfig,
  LotteryConfig,
  LotteryPoolType,
} from '@/types/interactive';
import * as interactiveAPI from '@/api/interactive';
import { ResultStatus } from '../../api/user';
import { apiErrorMessage } from '@/common/apiErrorMessage';

const options = [
  {
    value: InteractiveType.Questionnaire,
    label: '问卷',
  },
  {
    value: InteractiveType.Slide,
    label: '答题闯关',
  },
  {
    value: InteractiveType.Video,
    label: '直播',
  },
  {
    value: InteractiveType.Lottery,
    label: '抽奖',
  },
];

@Component({})
export default class CreateTabDialog extends Vue {
  @Prop({ required: true, type: Number })
  private activityId!: number;

  private dialogVisible: boolean = false;

  private options = options;

  private type: InteractiveType = InteractiveType.Questionnaire;
  private name: string = '';
  private questionnaireId: string = '';
  private videoUrl = 'http://ivi.bupt.edu.cn/hls/cctv3hd.m3u8';

  private loading = false;

  public show() {
    this.dialogVisible = true;
  }

  private clearContent() {
    this.type = InteractiveType.Questionnaire;
    this.name = '';
    this.questionnaireId = '';
  }

  get submitable() {
    if (this.name === '') {
      return false;
    }
    switch (this.type) {
      case InteractiveType.Questionnaire: {
        return this.questionnaireId !== '';
      }
      default:
        return true;
    }
  }

  get showQuestionnare() {
    return this.type === InteractiveType.Questionnaire;
  }

  get showVideo() {
    return this.type === InteractiveType.Video;
  }

  public submit() {
    let config;
    switch (this.type) {
      case InteractiveType.Questionnaire: {
        config = {
          type: this.type,
          name: this.name,
          config: {
            id: this.questionnaireId,
          },
        } as InteractiveConfig<QuestionnaireConfig>;
        break;
      }
      case InteractiveType.Slide: {
        config = {
          type: this.type,
          name: this.name,
          config: {
            questions: [],
          },
        };
        break;
      }
      case InteractiveType.Video: {
        config = {
          type: this.type,
          name: this.name,
          config: {
            url: this.videoUrl,
          },
        };
        break;
      }
      case InteractiveType.Lottery: {
        config = {
          type: this.type,
          name: this.name,
          config: {
            num: 1,
            type: LotteryPoolType.SignedUser,
          } as LotteryConfig,
        };
        break;
      }
    }
    this.loading = true;
    interactiveAPI
      .createInteractive(this.activityId, config as any)
      .then((result) => {
        this.$emit('submit', result);
        this.dialogVisible = false;
        this.$message.success('提交成功');
      })
      .catch((err: ResultStatus) => {
        apiErrorMessage(this, err);
      })
      .finally(() => {
        this.loading = false;
      });
  }
}
</script>

<style lang="css">
el-slider {
  width: 180px !important;
  margin-left: auto;
  margin-right: auto;
}
div.el-form-item {
  width: 400px;
  padding-right: 20px;
  float: left;
}
#form {
  overflow: auto;
}
div.el-select {
  width: 100% !important;
}
</style>