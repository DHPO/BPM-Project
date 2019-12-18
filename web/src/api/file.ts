import axios from 'axios';
import { ResultStatus } from './user';

export async function uploadRichText(content: string) {
  const url = '/api/content';
  const boundary = '---------------------------7da24f2e50046';
  const body = '--' + boundary + '\r\n'
  + 'Content-Disposition: form-data; name="content";'
  + 'filename="description"\r\n'
  + 'Content-type: plain/text\r\n\r\n'
  + content + '\r\n'
  + '--' + boundary + '--';
  return axios.post(url, body, {
    headers: {
      'Content-Type': `multipart/form-data; boundary=${boundary}`,
    },
  })
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

export async function fetchRichText(url: string) {
  return axios.get(`/api/content?url=${url}`)
    .then((res) => {
      return res.data.data;
    });
}
