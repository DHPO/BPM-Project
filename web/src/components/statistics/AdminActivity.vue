<template>
  <div id="card">
    <h1>活动分析报表</h1>
    <el-divider class="divider" content-position="left">查询选项</el-divider>
    <el-form>
      <el-form-item label="查看周期">
        <el-radio-group size="small" v-model="periodOption">
          <el-radio-button label="月"></el-radio-button>
          <el-radio-button label="周"></el-radio-button>
          <el-radio-button label="日"></el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="操作">
        <el-button type="primary" @click="loadData" size="small"><i class="el-icon-refresh" />加载数据</el-button>
      </el-form-item>
    </el-form>
    <div style="height: 100px" />
    <el-divider class="divider" content-position="left">查询结果</el-divider>
    <div id="shortcut-wrapper" class="center-align">
      <ShortCut title="活动总量" :value="activityRate" @mousedown.native="chartChoice='activity'" />
      <ShortCut title="用户出勤率" :value="checkinRate" @mousedown.native="chartChoice='activity'" />
      <ShortCut title="互动项目使用" :value="interactionRate" @mousedown.native="chartChoice='interaction'" />
      <ShortCut title="弹幕数量" :value="commentRate" @mousedown.native="chartChoice='comment'" />
    </div>
    <div style="height: 30px" />
    <div id="chart-wrapper" class="center-align">
      <v-chart id="chart" ref="chart" :options="option" />
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Ref, Watch } from 'vue-property-decorator';
import ShortCut from '@/components/statistics/shortcut.vue';
import {
  getAdminUserStatistic,
  getAdminActivityStatistic,
} from '../../api/statistic';
import {
  AdminUserStatistics,
  AdminActivityStatistics,
} from '../../types/statistics';
import { apiErrorMessage } from '../../common/apiErrorMessage';

@Component({
  components: {
    ShortCut,
  },
})
export default class TestView extends Vue {
  private rawData: AdminActivityStatistics[] = [];

  @Ref()
  private chart: any;

  private chartChoice = 'activity';

  get option() {
    switch (this.chartChoice) {
      case 'activity': return this.activityOption;
      case 'comment': return this.commentOption;
      case 'interaction': return this.interactiveOption;
    }
  }

  private loadData() {
    getAdminActivityStatistic(
      Date.now() - 10 * this.splittime,
      Date.now(),
      this.splittime,
    ).then((data) => {
      this.rawData = data;
    }).catch((err: any) => apiErrorMessage(this, err));
  }

  private periodOption = '日';

  get splittime() {
    switch (this.periodOption) {
      case '日':
        return 86400 * 1000;
      case '周':
        return 7 * 86400 * 1000;
      case '月':
        return 30 * 86400 * 1000;
      default:
        return 86400 * 1000;
    }
  }

  @Watch('periodOption')
  private handlePeroidChange() {
    this.loadData();
  }

  get commentOption() {
    return {
      title: {
        text: '弹幕分析报表',
      },
      legend: {},
      tooltip: {},
      xAxis: {
        type: 'category',
        data: this.xAxisData,
      },
      yAxis: [
        {
          type: 'value',
          name: '总量',
        },
        {
          type: 'value',
          name: '场均数量',
        },
      ],
      series: this.commentSeries,
    };
  }

  get commentSeries() {
    const comments = this.rawData.map((d) => d.comment);
    const average = this.rawData.map((d) => d.comment / Math.max(1, d.activity));

    return [
      {
        name: '总量',
        type: 'bar',
        data: comments,
      },
      {
        name: '场均数量',
        type: 'line',
        data: comments,
        yAxisIndex: 1,
      },
    ];
  }

  get activityOption() {
    return {
      title: {
        text: '活动分析报表',
      },
      legend: {},
      tooltip: {
        trigger: 'axis',
        showContent: false,
      },
      xAxis: {
        type: 'category',
        data: this.xAxisData,
      },
      yAxis: [
        {
          type: 'value',
          name: '总量',
        },
        {
          type: 'value',
          name: '出勤率',
        },
      ],
      series: this.activitySeries,
    };
  }

  get activitySeries() {
    const activity = this.rawData.map((d) => d.activity);
    const checkin = this.rawData.map((d) => d.checkin / Math.max(1, d.register));

    return [
      {
        name: '活动数量',
        type: 'bar',
        data: activity,
      },
      {
        name: '出勤率',
        type: 'line',
        smooth: true,
        data: checkin,
        yAxisIndex: 1,
      },
    ];
  }

  get interactiveOption() {
    return {
      legend: {},
      title: {
        text: '互动项目分析报表',
      },
      tooltip: {
        trigger: 'axis',
        showContent: false,
      },
      dataset: this.interactiveDataset,
      xAxis: { type: 'category' },
      yAxis: { gridIndex: 0 },
      grid: { top: '55%' },
      series: [
        { type: 'line', smooth: true, seriesLayoutBy: 'row' },
        { type: 'line', smooth: true, seriesLayoutBy: 'row' },
        { type: 'line', smooth: true, seriesLayoutBy: 'row' },
        { type: 'line', smooth: true, seriesLayoutBy: 'row' },
        {
          type: 'pie',
          id: 'pie',
          radius: '30%',
          center: ['50%', '25%'],
          label: {
            formatter: `{b}:{d} %`,
          },
          encode: {
            itemName: 'date',
            value: this.xAxisData[0],
            tooltip: this.xAxisData[0],
          },
        },
      ],
    };
  }

  get interactiveDataset() {
    if (this.rawData) {
      const dates = this.rawData
        .map((data) => data.time)
        .map((time) => new Date(time).toLocaleDateString());
      const vote = this.rawData.map((d) => d.interaction.vote);
      const video = this.rawData.map((d) => d.interaction.video);
      const draw = this.rawData.map((d) => d.interaction.draw);
      const game = this.rawData.map((d) => d.interaction.game);

      return {
        source: [
          ['date', ...dates],
          ['投票', ...vote],
          ['直播', ...video],
          ['抽奖', ...draw],
          ['答题闯关', ...game],
        ],
      };
    }
    return {};
  }

  get activityRate() {
    if (this.rawData) {
      const data = this.rawData.map((d) => d.activity);
      if (data.length >= 2) {
        const last1 = data[data.length - 1];
        const last2 = data[data.length - 2];
        if (last1 === 0 && last2 === 0) {
          return 0;
        } else if (last1 === 0 && last2 !== 0) {
          return -1;
        } else if (last1 !== 0 && last2 === 0) {
          return 9.99;
        } else {
          return last1 / last2 - 1;
        }
      }
    }
    return 0;
  }

  get commentRate() {
    if (this.rawData) {
      const data = this.rawData.map((d) => d.comment);
      if (data.length >= 2) {
        const last1 = data[data.length - 1];
        const last2 = data[data.length - 2];
        if (last1 === 0 && last2 === 0) {
          return 0;
        } else if (last1 === 0 && last2 !== 0) {
          return -1;
        } else if (last1 !== 0 && last2 === 0) {
          return 9.99;
        } else {
          return last1 / last2 - 1;
        }
      }
    }
    return 0;
  }

  get interactionRate() {
    if (this.rawData) {
      const data = this.rawData
        .map((d) => d.interaction)
        .map((i) => i.vote + i.game + i.draw + i.video);
      if (data.length >= 2) {
        const last1 = data[data.length - 1];
        const last2 = data[data.length - 2];
        if (last1 === 0 && last2 === 0) {
          return 0;
        } else if (last1 === 0 && last2 !== 0) {
          return -1;
        } else if (last1 !== 0 && last2 === 0) {
          return 9.99;
        } else {
          return last1 / last2 - 1;
        }
      }
    }
    return 0;
  }

  get checkinRate() {
    if (this.rawData) {
      const data = this.rawData.map(
        (d) => (d.checkin / Math.max(1, d.register)) * 100,
      );
      if (data.length >= 2) {
        const last1 = data[data.length - 1];
        const last2 = data[data.length - 2];
        if (last1 === 0 && last2 === 0) {
          return 0;
        } else if (last1 === 0 && last2 !== 0) {
          return -1;
        } else if (last1 !== 0 && last2 === 0) {
          return 9.99;
        } else {
          return last1 / last2 - 1;
        }
      }
    }
    return 0;
  }

  get xAxisData() {
    if (this.rawData) {
      const dates = this.rawData
        .map((data) => data.time)
        .map((time) => new Date(time).toLocaleDateString());
      return dates;
    }
    return [];
  }

  private mounted() {
    const myChart = this.chart.chart;
    myChart.on('updateAxisPointer', (event: any) => {
      const xAxisInfo = event.axesInfo[0];
      if (xAxisInfo) {
        const dimension = xAxisInfo.value + 1;
        myChart.setOption({
          series: {
            id: 'pie',
            label: {
              formatter: '{b}: {@[' + dimension + ']} ({d}%)',
            },
            encode: {
              value: dimension,
              tooltip: dimension,
            },
          },
        });
      }
    });
  }
}
</script>

<style scoped>
#card {
  overflow-x: hidden;
  width: 85%;
  margin-left: auto;
  margin-right: auto;
}
#shortcut-wrapper {
  width: 65vw;
  margin-left: -50px;
  margin-right: auto;
}
.center-align {
  display: flex;
  align-content: flex-start;
  justify-content: center;
}
#chart {
  width: 800px;
  height: 600px;
}
</style>
