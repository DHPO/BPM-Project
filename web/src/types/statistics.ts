import { InteractiveType } from './interactive';
export interface AdminActivityStatistics {
  time: number;
  /**
   * 活动数量
   */
  activity: number;
  /**
   * 注册用户人次
   */
  register: number;
  checkin: number;
  interaction: {
    /**
     * 投票
     */
    vote: number;
    /**
     * 答题闯关
     */
    game: number;
    /**
     * 抽奖
     */
    draw: number;
    /**
     * 直播
     */
    video: number;
  };
  comment: number;
}

export interface CommentStatisticsData {
  activityid: number;
  commenttime: number;
  description: string;
  id: number;
  negative_prob: number;
  positive_prob: number;
  userid: number;
}

export interface InteractionStatisticsData {
  activityId: number;
  attendnum: number;
  description: string;
  endtime: number;
  id: number;
  interactiontype: InteractiveType;
  name: string;
  starttime: number;
}

export interface OrganizerActivityStatistics {
  checkin: number;
  comment: CommentStatisticsData[];
  id: number;
  interaction: InteractionStatisticsData[];
  register: number;
}

export interface AdminUserStatistics {
  time: number;
  user: number;
  register: number;
  checkin: number;
}
