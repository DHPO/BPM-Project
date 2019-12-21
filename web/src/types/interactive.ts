export enum InteractiveType {
  Comment,
  Questionnaire,
  Slide,
  Score,
  Lottery,
  Video,
  Signin,
}

export interface SigninConfig {
  url: string;
}

export interface QuestionnaireConfig {
  id: string;
}
export interface SlideContent {
  text: string;
}

export interface SlideConfig {
  questions: SlideContent[];
}

export interface InteractiveConfig<T> {
  type: InteractiveType;
  id: string;
  name: string;
  config: T;
}

export interface QuestionnaireState {
  showQrCode: boolean;
  showResult: boolean;
  reload: boolean;
}

export interface SlideState {
  current: number;
}

export enum LotteryPoolType {
  SignedUser,
  Custom,
}
export interface LotteryConfig {
  type: LotteryPoolType;
  num: number;
  pool?: any[];
}

export enum LotteryRunningState {
  Infant,
  Running,
  Done,
}

export interface LotteryState {
  runningState: LotteryRunningState;
  pool?: any[];
  result?: any[];
}

export interface VideoConfig {
  url: string;
}

export interface StateRequest {
  id: string;
  state: any;
}
