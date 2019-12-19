import { UserAuth, UserStatus, UserRole } from './../types/user';
import {Module, VuexModule, Action, Mutation} from 'vuex-module-decorators';
import * as userapi from '@/api/user';
import { User } from '@/types/user';
import router from '../router';


@Module({
  name: 'UserStore',
  namespaced: true,
})
export default class UserStore extends VuexModule {

  public user?: User;

  get User() {
    return this.user;
  }

  get auth() {
    if (!this.user) {
      return UserAuth.NotLogin;
    } else if (this.user.role === UserRole.General) {
      return UserAuth.General;
    } else {
      return UserAuth.Manager;
    }
  }

  @Mutation
  private setUser(user: User) {
    this.user = user;
  }

  @Action({commit: 'setUser', rawError: true})
  public async login(payload: {username: string, password: string}) {
    const {username, password} = payload;
    return userapi.login(username, password)
      .then((user) => {
        if (window.history.length <= 1) {
          router.push({ path: '/manage' });
        } else {
          router.go(-1);
        }
        return user;
      })
      .catch((err) => {
        throw err;
      });
  }

  @Action({commit: 'setUser', rawError: true})
  public async register(payload: {username: string, password: string}) {
    const {username, password} = payload;
    return userapi.register(username, password)
      .then((user) => {
        return user;
      })
      .catch((err) => {
        throw err;
      });
  }

  @Action({commit: 'setUser', rawError: true})
  public async logout() {
    userapi.logout();
    return;
  }
}
