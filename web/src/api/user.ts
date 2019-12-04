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
