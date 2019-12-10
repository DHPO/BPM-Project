export enum InteractiveType {
  Signin,
  Questionnaire,
  Lottery,
}

export interface SigninConfig{
  url: string;
}

export interface QuestionnaireConfig{
  id: string;
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

export interface StateRequest {
  id: string;
  state: any;
}
