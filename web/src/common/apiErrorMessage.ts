import { ResultStatus } from './../api/user';
import { Vue } from 'vue-property-decorator';
import router from '../router';

export function apiErrorMessage(vue: Vue, err: ResultStatus) {
  let message = '';
  switch (err) {
    case ResultStatus.Forbidden: {
      message = '权限不足';
      break;
    }
    case ResultStatus.NotLogin: {
      message = '用户未登录';
      router.push({
        path: '/login',
      });
      break;
    }
    case ResultStatus.SystemError: {
      message = '系统错误';
      break;
    }
    default: {
      message = `其他错误：${err}`;
      break;
    }
  }
  vue.$message.error(message);
}
