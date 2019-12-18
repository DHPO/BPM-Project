import { ActivityAddVO, ActivityDetailVO, ActivityVO } from './../types/activity';
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

export async function addActivity(activity: ActivityAddVO): Promise<ActivityVO> {
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

export async function updateActivity(id: number, activity: ActivityAddVO): Promise<ActivityVO> {
  const url = `/api/activity/${id}`;
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

export async function getActivity(activityId: number): Promise<ActivityDetailVO> {
  const url = `/api/activity/${activityId}`;
  return axios.get(url)
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
