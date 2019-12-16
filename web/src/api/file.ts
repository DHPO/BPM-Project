import axios from 'axios';
import { ResultStatus } from './user';

export async function uploadRichText(content: string) {
  const url = '/api/content';
  return axios.post(url, content, {
    headers: {
      'Content-Type': 'multipart/form-data',
      'Content-Disposition': `form-data; name=""; filename="description"`,
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
