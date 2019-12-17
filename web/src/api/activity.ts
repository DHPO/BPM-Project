import { ActivityAddVO } from './../types/activity';
import axios from 'axios';
import { ResultStatus } from './user';

export async function getCheckinUsers(activityId: number) {
  const url = '/api/activity/checkin/users';
  return axios.get(url, {
    params: {
      activityId,
    },
  })
  .then((res) => {
    if (res.data.status === ResultStatus.Success) {
      return res.data.data || [];
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

export async function addActivity(activity: ActivityAddVO) {
  const url = '/api/activity/add';
  return axios.post(url, activity)
  .then((res) => {
    if (res.data.status === ResultStatus.Success) {
      return res.data.data;
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
