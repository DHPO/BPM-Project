<template>
<el-card id="card">
  <activity-editor v-if="loadDone" :activityVO="activityVO" />
</el-card>
</template>

<script lang="ts">
import {Component, Vue, Prop} from 'vue-property-decorator';
import ActivityEditor from '@/components/ActivityEditor/editor.vue';
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
    if (this.id) {
      return getActivity(this.id)
        .then((activityVO) => {
          this.activityVO = activityVO;
          this.loadDone = true;
        })
        .catch((err: any) => apiErrorMessage(this, err));
    } else {
      this.loadDone = true;
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