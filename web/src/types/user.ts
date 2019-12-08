export enum UserRole {
  Manager,
  General,
}

export enum UserStatus {
  Normal,
  BlackList,
}

export interface User {
  id: string;
  role: UserRole;
  status: UserStatus;
  username: string;
}

export enum UserAuth {
  NotLogin,
  General,
  Manager,
}
