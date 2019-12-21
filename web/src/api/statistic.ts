import { OrganizerActivityStatistics, AdminActivityStatistics, AdminUserStatistics } from './../types/statistics';
import axios from 'axios';
import { ResultStatus } from './user';

export async function getActivityStatistic(activityId: number) {
  const url = `/api/statistic/activity/${activityId}`;

  return axios.get(url)
  .then((res) => {
    if (res.data.status === ResultStatus.Success) {
      const result =  res.data.data;
      result.comment = result.comment || [];
      result.interaction = result.interaction || [];
      return result as OrganizerActivityStatistics;
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

export async function getAdminUserStatistic(starttime: number, endtime: number, splittime: number) {
  const url = '/api/statistic/users';

  return axios.post(url, {
    starttime,
    endtime,
    splittime,
  })
  .then((res) => {
    if (res.data.status === ResultStatus.Success) {
      const result =  res.data.data;
      return result as AdminUserStatistics[];
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

export async function getAdminActivityStatistic(starttime: number, endtime: number, splittime: number) {
  const url = '/api/statistic/activities';

  return axios.post(url, {
    starttime,
    endtime,
    splittime,
  })
  .then((res) => {
    if (res.data.status === ResultStatus.Success) {
      const result =  res.data.data;
      return result as AdminActivityStatistics[];
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
