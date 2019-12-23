<template>
  <div>
    <h1>活动分析报表</h1>
    <el-button type="primary" @click="loadData" size="small"><i class="el-icon-refresh" />加载数据</el-button>
    <div id="shortcut-wrapper" class="center-align">
      <short-cut title="报名率" :value="registerRate" />
      <short-cut title="出勤率" :value="checkinRate" />
      <short-cut title="弹幕数量" :value="commentNum" />
    </div>
    <v-chart id="chart" v-if="showChart" :options="commentOption" />
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import { OrganizerActivityStatistics } from '../../types/statistics';
import { ActivityVO, ActivityDetailVO } from '../../types/activity';
import { getActivityStatistic } from '../../api/statistic';
import { getActivity } from '../../api/activity';
import ShortCut from './valueShortCut.vue';
import { commentDataSeriesConvert, generateFakeData } from './comment';
import { apiErrorMessage } from '@/common/apiErrorMessage';

@Component({
  components: {
    ShortCut,
  },
})
export default class OrganizerActivity extends Vue {
  @Prop()
  private activityId!: number;

  private splitMinute = 2;

  private rawStatistic: OrganizerActivityStatistics | null = null;
  private activity: ActivityDetailVO | null = null;

  get registerRate() {
    if (this.activity !== null && this.rawStatistic !== null) {
      const rate =
        this.rawStatistic.register /
        Math.max(1, this.activity.activity.peoplenum);
      return `${(100 * rate).toFixed(1)} %`;
    }
    return '0 %';
  }

  get checkinRate() {
    if (this.activity !== null && this.rawStatistic !== null) {
      const rate =
        this.rawStatistic.checkin / Math.max(1, this.rawStatistic.register);
      return `${(100 * rate).toFixed(1)} %`;
    }
    return '0 %';
  }

  get showChart() {
    return this.rawStatistic && this.rawStatistic.comment.length > 0;
  }

  get commentNum() {
    if (this.activity !== null && this.rawStatistic !== null) {
      return this.rawStatistic.comment.length;
    }
    return 0;
  }

  get commentDataSeries() {
    if (this.activity !== null && this.rawStatistic !== null) {
      const result =  commentDataSeriesConvert(
        this.rawStatistic.comment,
        this.activity.activity.starttime,
        this.activity.activity.endtime,
        this.splitMinute * 86400,
      );
      result.splice(0, 0, [
        this.activity.activity.starttime,
        0,
      ]);
      result.splice(result.length, 0, [
        this.activity.activity.endtime,
        0,
      ]);
      return result;
    }
    return [];
  }

  get commentDataEmotionSeries() {
    return this.commentDataSeries
    .map((d) => [d[0], d[1]])
    .filter((d) => d[1] !== 0)
    .map((d) => [d[0], d[1].toFixed(3)]);
  }

  get commentDataAmountSeries() {
    return this.commentDataSeries.map((d) => [d[0], d[2]]);
  }

  get startMarks() {
    if (this.activity !== null && this.rawStatistic !== null) {
      const data = this.rawStatistic.interaction.map((i) => ({
        name: `${i.name}开始`,
        value: `${i.name}开始`,
        xAxis: i.starttime,
        yAxis: 0,
      }));
      data.push({
        name: '活动开始',
        value: '活动开始',
        xAxis: this.activity.activity.starttime,
        yAxis: 0,
      });
      return data;
    }
    return [];
  }

  get endMarks() {
    if (this.activity !== null && this.rawStatistic !== null) {
      const data =  this.rawStatistic.interaction.map((i) => ({
        name: `${i.name}结束`,
        value: `${i.name}结束`,
        xAxis: i.endtime,
        yAxis: 1,
      }));
      data.push({
        name: '活动结束',
        value: '活动结束',
        xAxis: this.activity.activity.endtime,
        yAxis: 1,
      });
      return data;
    }
    return [];
  }

  get commentOption() {
    return {
      title: {
        text: '互动项目-弹幕情感分析',
      },
      tooltip: {
        trigger: 'axis',
      },
      xAxis: {
        type: 'time',
        name: '时间',
        splitLine: {
          show: true,
        },
      },
      yAxis: [
        {
          type: 'value',
          name: '弹幕数量',
          boundaryGap: [0, '100%'],
          splitLine: {
            show: false,
          },
        },
        {
          type: 'value',
          name: '情感分数',
          splitLine: {
            show: false,
          },
        },
      ],
      series: [
        {
          name: '情感分数',
          type: 'line',
          showSymbol: false,
          hoverAnimation: false,
          min: 0,
          max: 1,
          data: this.commentDataEmotionSeries,
          yAxisIndex: 1,
          smooth: true,
          markPoint: {
            data: this.startMarks,
          },
        },
        {
          name: '弹幕数量',
          type: 'bar',
          showSymbol: false,
          hoverAnimation: false,
          data: this.commentDataAmountSeries,
          markPoint: {
            data: this.endMarks,
          },
        },
      ],
    };
  }

  private loadData() {
    return this.loadFakeData();
  }

  private async loadRealData() {
    return Promise.all([
      getActivityStatistic(this.activityId)
      .then(
        (data) => (this.rawStatistic = data),
      ).catch((err: any) => apiErrorMessage(this, err)),
      getActivity(this.activityId)
      .then((data) => (this.activity = data))
      .catch((err: any) => apiErrorMessage(this, err)),
    ]).then(() => {
      this.$forceUpdate();
    });
  }

  private async loadFakeData() {
    const {activity, statistic} = generateFakeData();
    this.activity = activity;
    this.rawStatistic = statistic;
  }

}
</script>

<style scoped>
#card {
  overflow-x: hidden;
  width: 80%;
  margin-left: auto;
  margin-right: auto;
}
#shortcut-wrapper {
  width: 50vw;
  margin-left: auto;
  margin-right: auto;
}
.center-align {
  display: flex;
  align-content: flex-start;
  justify-content: center;
}
#chart {
  width: 800px;
  height: 500px;
  margin-left: auto;
  margin-right: auto;
}
</style>