import { InteractiveConfig } from './../types/interactive';
import axios from 'axios';
import { ResultStatus } from './user';

class InteractiveConverter {
  public static from<T>(config: InteractiveConfig<T>) {
    return {
      id: config.id ? parseInt(config.id, 10) : undefined,
      description: JSON.stringify(config.config),
      name: config.name,
      interactiontype: config.type,
    };
  }

  public static to<T>(rawData: any): InteractiveConfig<T> {
    return {
      id: rawData.id.toString(),
      name: rawData.name,
      config: JSON.parse(rawData.description) as T,
      type: rawData.interactiontype,
    };
  }
}

export async function getInteractive(activityId: number) {
  const url = '/api/interaction';
  return axios.get(url, {
    params: {
      activityId,
    }
  })
  .then((res) => {
    if (res.data.status === ResultStatus.Success) {
      return res.data.data.interactions.map(InteractiveConverter.to);
    } else {
      throw res.data.status as ResultStatus;
    }
  })
  .catch((err) => {
    throw err.response.data.status;
  });
}

export async function createInteractive<T>(activityId: number, config: InteractiveConfig<T>) {
  const url = '/api/interaction';
  return axios.post(url, {
    interactions: [{
      activityid: activityId,
      ...InteractiveConverter.from(config),
    }],
  })
  .then((res) => {
    if (res.data.status === ResultStatus.Success) {
      return InteractiveConverter.to(res.data.data.interactions[0]);
    } else {
      throw res.data.status as ResultStatus;
    }
  })
  .catch((err) => {
    console.log(err)
    throw err.response.data.status;
  });
}

export async function updateInteractive<T>(activityId: number, config: InteractiveConfig<T>) {
  const url = '/api/interaction/update';
  return axios.post(url, {
    activityid: activityId,
    ...InteractiveConverter.from(config),
  })
  .then((res) => {
    if (res.data.status === ResultStatus.Success) {
      return;
    } else {
      throw res.data.status as ResultStatus;
    }
  })
  .catch((err) => {
    throw err.response.data.status;
  });
}
