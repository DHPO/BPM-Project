import { CommentStatisticsData } from './../../types/statistics';

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

