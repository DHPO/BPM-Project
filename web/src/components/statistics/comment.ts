import { CommentStatisticsData, OrganizerActivityStatistics } from './../../types/statistics';
import { ActivityVO, ActivityDetailVO } from '@/types/activity';
import { InteractiveType } from '@/types/interactive';

export function commentDataSeriesConvert(comments: CommentStatisticsData[], start: number, end: number, split: number) {
  const bins = generateBins(start, end, split);

  comments.forEach((c) => {
    const idx = number2Bin(c.commenttime, start, split);
    if (idx >= 0 && idx < bins.length) {
      bins[idx].push(c.positive_prob);
    }
  });

  return bins.map((b, idx) => ([
    start + idx * split,
    b.average,
    b.count,
  ])).filter((v) => v[1] !== 0);
}

class Bin {
  public count = 0;
  public value = 0;

  public push(value: number) {
    this.value += value;
    this.count += 1;
  }

  get average() {
    if (this.count) {
      return this.value / this.count;
    }
    return 0;
  }
}

function generateBins(start: number, end: number, split: number) {
  return Object.keys(Array.from({length: Math.ceil((end - start) / split)})).map(() => new Bin());
}

function number2Bin(num: number, start: number, split: number) {
  return Math.floor((num - start) / split);
}

function generateFakeComment(start: number, end: number) {
  return {
    id: 0,
    commenttime: start + Math.random() * (end - start),
    positive_prob: Math.random(),
    negative_prob: 0,
    userid: 0,
    description: '',
    activityid: 0,
  };
}

export function generateFakeData(): {
  activity: ActivityDetailVO,
  statistic: OrganizerActivityStatistics,
} {

  return {
    activity: {
      activity: {
        addtime: 1576647228343,
        attendnum: 92,
        descriptionurl: 'http://118.31.77.203:8080/file/U19367ccc350066/ActiProj/Content/1576678624137',
        endtime: Date.now(),
        id: 1576647229601,
        location: '上海交通大学闵行校区机械与动力工程学院,高田会堂123',
        name: '【大师讲坛】第147期：从应用力学到工程科学',
        organizerid: 1575879335368,
        peoplenum: 100,
        photourl: 'http://118.31.77.203:8080/file/U19367ccc350066/ActiProj/Photo/1576647125280',
        registerendtime: 1576654200000,
        registernum: 95,
        registerstarttime: 1576425600000,
        starttime: Date.now() - 3600000,
        status: 3,
        updatetime: 1576647228343,
      },
      position: {
        latitude: 0,
        longitude: 0,
        location: '',
      },
      tags: [],
    },
    statistic: {
      id: 1576647229601,
      checkin: 90,
      register: 95,
      interaction: [
        {
          activityId: 0,
          attendnum: 0,
          description: '',
          starttime: Date.now() - 55 * 60 * 1000,
          endtime: Date.now() - 45 * 60 * 1000,
          interactiontype: InteractiveType.Questionnaire,
          name: '投票',
          id: 0,
        },
        {
          activityId: 0,
          attendnum: 0,
          description: '',
          starttime: Date.now() - 40 * 60 * 1000,
          endtime: Date.now() - 30 * 60 * 1000,
          interactiontype: InteractiveType.Questionnaire,
          name: '直播',
          id: 0,
        },
        {
          activityId: 0,
          attendnum: 0,
          description: '',
          starttime: Date.now() - 25 * 60 * 1000,
          endtime: Date.now() - 5 * 60 * 1000,
          interactiontype: InteractiveType.Questionnaire,
          name: '答题闯关',
          id: 0,
        },
      ],
      comment: Array.from({length: 900 + Math.random() * 100})
        .map(() => generateFakeComment(Date.now() - 3600000, Date.now())),
    },
  };
}
