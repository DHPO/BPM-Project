<template>
<el-card id="card" v-loading="!loadDone">
  <activity-editor v-if="loadDone" :activity="activityVO" />
</el-card>
</template>

<script lang="ts">
import {Component, Vue, Prop, Watch} from 'vue-property-decorator';
import ActivityEditor from '@/components/ActivityEditor/activityViewer.vue';
import { ActivityDetailVO } from '../types/activity';
import { getActivity } from '@/api/activity';
import { apiErrorMessage } from '../common/apiErrorMessage';

@Component({
  components: {
    ActivityEditor,
  },
})
export default class ActivityEditView extends Vue {
  @Prop({default: null})
  private id!: number|null;

  private loadDone = false;

  private activityVO: ActivityDetailVO | null = null;

  private async created() {
    this.load();
  }

  @Watch('id')
  private load() {
    this.loadDone = false;
    if (this.id) {
      return getActivity(this.id)
        .then((activityVO) => {
          this.activityVO = activityVO;
          this.loadDone = true;
        })
        .catch((err: any) => apiErrorMessage(this, err));
    } else {
      this.activityVO = null;
      this.$nextTick(() => {
        this.loadDone = true;
      });
    }
  }
}
</script>

<style scoped>
#card {
  margin-top: 20px;
  width: 70vw;
  margin-left: auto;
  margin-right: auto;
}
</style>