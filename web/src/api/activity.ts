import { ActivityAddVO, ActivityDetailVO, ActivityVO, ActivityStatus } from './../types/activity';
import axios from 'axios';
import { ResultStatus } from './user';

function acitivityStatusMap(activity: ActivityVO) {
  if (activity.status === ActivityStatus.Passed) {
    if (activity.endtime < Date.now()) {
      activity.status = ActivityStatus.End;
    } else if (activity.starttime < Date.now()) {
      activity.status = ActivityStatus.InProcess;
    } else if (activity.registerendtime < Date.now()) {
      activity.status = ActivityStatus.NotStart;
    } else if (activity.registerstarttime < Date.now()) {
      activity.status = ActivityStatus.Registering;
    }
  }
  return activity;
}

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
        return res.data.data as ActivityDetailVO;
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

export async function approveActivity(activityId: number, approve: boolean) {
  const url = `/api/activity/check/${activityId}`;
  return axios.post(url, {}, {
    params: {
      activityStatus: approve ? ActivityStatus.Passed : ActivityStatus.Unpassed,
    },
  }).then((res) => {
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

export async function getCheckList() {
  const url = '/api/activity/check';
  return axios.get(url)
  .then((res) => {
    if (res.data.status === ResultStatus.Success) {
      return (res.data.data || []).map(acitivityStatusMap) as ActivityVO[];
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

export async function getActivityByKeyword(keyword: string) {
  const url = '/api/activity';
  return axios.get(url, {
    params: {
      keyword,
    },
  })
  .then((res) => {
    if (res.data.status === ResultStatus.Success) {
      return (res.data.data || []).map(acitivityStatusMap) as ActivityVO[];
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

export async function getActivityByTag(tag: string) {
  const url = '/api/activity/tag';
  return axios.get(url, {
    params: {
      tag,
    },
  })
  .then((res) => {
    if (res.data.status === ResultStatus.Success) {
      return (res.data.data || []).map(acitivityStatusMap) as ActivityVO[];
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

export async function getActivityOfUser(userId: string) {
  const url = '/api/activities';

  return axios.get(url, {
    params: {
      organizerId: userId,
    },
  })
  .then((res) => {
    if (res.data.status === ResultStatus.Success) {
      return (res.data.data || []).map(acitivityStatusMap) as ActivityVO[];
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
