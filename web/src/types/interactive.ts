export enum InteractiveType {
  Signin,
  Questionnaire,
  Lottery,
  Slide,
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

export interface StateRequest {
  id: string;
  state: any;
}


