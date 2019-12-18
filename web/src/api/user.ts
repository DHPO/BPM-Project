import { UserStatus } from './../types/user';
import axios from 'axios';
import { User } from '@/types/user';

export enum ResultStatus {
  Success,
  Forbidden,
  PasswordError,
  UserNotExist,
  LockedAccount,
  NotLogin,
  SystemError,
}

export async function login(username: string, password: string) {
  const url = `/api/login`;
  return axios.post(url, null, {
    params: {
      username,
      password,
    },
  }).then((res) => {
      const { status, data } = res.data as {
        status: ResultStatus,
        data: User,
      };
      if (status === ResultStatus.Success) {
        return data;
      } else {
        throw status;
      }
    })
    .catch((err) => {
      throw err.response.data.status;
    });
}

export async function register(username: string, password: string) {
  const url = `/api/register`;
  return axios.post(url, null, {
    params: {
      username,
      password,
    },
  }).then((res) => {
      const { status, data } = res.data as {
        status: ResultStatus,
        data: User,
      };
      if (status === ResultStatus.Success) {
        return data;
      } else {
        throw status;
      }
    })
    .catch((err) => {
      throw err.response.data.status;
    });
}

export async function logout() {
  const url = `/api/logout`;
  return axios.post(url);
}

export async function getUserByUsername(username: string) {
  const url = '/api/user/find';

  return axios.get(url, {
    params: {
      username,
    },
  })
  .then((res) => {
    if (res.data.status === ResultStatus.Success) {
      return JSON.parse(res.data.data).User as User[];
    } else {
      throw res.data.status as ResultStatus;
    }
  })
  .catch((err) => {
    if (err.status === 500) {
      throw ResultStatus.SystemError;
    }
    throw err.response.data.status;
  });
}

export async function banUser(userId: string, ban: boolean) {
  const url = `/api/user/${userId}`;

  return axios.post(url, {}, {
    params: {
      userStatus: ban ? UserStatus.BlackList : UserStatus.Normal,
    },
  })
  .then((res) => {
    if (res.data.status === ResultStatus.Success) {
      return;
    } else {
      throw res.data.status as ResultStatus;
    }
  })
  .catch((err) => {
    if (err.status === 500) {
      throw ResultStatus.SystemError;
    }
    throw err.response.data.status;
  });
}
