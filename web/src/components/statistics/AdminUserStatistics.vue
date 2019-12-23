<template>
  <div id="card">
    <h1>用户分析报表</h1>
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
        <el-button type="primary" size="small" @click="loadData"><i class="el-icon-refresh" />加载数据</el-button>
      </el-form-item>
    </el-form>
    <div style="height: 70px" />
    <el-divider class="divider" content-position="left">查询结果</el-divider>
    <div id="shortcut-wrapper" class="center-align">
      <ShortCut title="用户总量" :value="userRate" />
      <ShortCut title="用户参与率" :value="registerRate" />
      <ShortCut title="用户出勤率" :value="checkinRate" />
    </div>
    <div style="height: 30px" />
    <div id="chart-wrapper" class="center-align">
      <v-chart :options="option" id="chart"/>
    </div>

  </div>
</template>

<script lang="ts">
import { Component, Vue, Ref, Watch } from 'vue-property-decorator';
import ShortCut from '@/components/statistics/shortcut.vue';
import { getAdminUserStatistic } from '../../api/statistic';
import { AdminUserStatistics } from '../../types/statistics';
import { apiErrorMessage } from '@/common/apiErrorMessage';

@Component({
  components: {
    ShortCut,
  },
})
export default class TestView extends Vue {
  private rawData: AdminUserStatistics[] = [];

  private loadData() {
    getAdminUserStatistic(Date.now() - 10 * this.splittime, Date.now(), this.splittime)
      .then((data) => {
        this.rawData = data;
      })
      .catch((err: any) => apiErrorMessage(this, err));
  }

  private periodOption = '日';

  get splittime() {
    switch (this.periodOption) {
      case '日': return 86400 * 1000;
      case '周': return 7 * 86400 * 1000;
      case '月': return 30 * 86400 * 1000;
      default: return 86400 * 1000;
    }
  }

  @Watch('periodOption')
  private handlePeroidChange() {
    this.loadData();
  }

  get option() {
    return {
      title: {
        text: '用户分析报表',
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
          name: '数量',
        },
        {
          type: 'value',
          name: '比率',
          axisLabel: {
            formatter: '{value} %',
          },
        },
        {
          type: 'value',
          name: '比率',
          axisLabel: {
            formatter: '{value} %',
          },
        },
      ],
      series: this.series,
    };
  }

  get userRate() {
    if (this.rawData) {
      const data = this.rawData.map((d) => d.user);
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

  get registerRate() {
    if (this.rawData) {
      const data = this.rawData.map((d) => d.register / Math.max(1, d.user) * 100);
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
      const data = this.rawData.map((d) => d.checkin / Math.max(1, d.register) * 100);
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
      const dates = this.rawData.map((data) => data.time).map((time) => new Date(time).toLocaleDateString());
      return dates;
    }
    return [];
  }

  get series() {
    if (this.rawData) {
      const total = this.rawData.map((data) => data.user);
      const attendRate = this.rawData.map((data) => data.register / Math.max(1, data.user) * 100);
      const checkinRate = this.rawData.map((data) => data.checkin / Math.max(1, data.register) * 100);
      return [
        {
          name: '用户数量',
          type: 'bar',
          data: total,
        },
        {
          name: '参与率',
          type: 'line',
          data: attendRate,
          yAxisIndex: 1,
        },
        {
          name: '出勤率',
          type: 'line',
          data: checkinRate,
          yAxisIndex: 2,
        },
      ];
    }
    return [];
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
#chart-wrapper, #chart {
  width: 800px;
  height: 600px;
  margin-left: auto;
  margin-right: auto;
}
</style>
