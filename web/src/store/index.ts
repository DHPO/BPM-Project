import Vue from 'vue';
import Vuex from 'vuex';
import createPersistedState from 'vuex-persistedstate';
import UserStore from './user';

Vue.use(Vuex);

export default new Vuex.Store({
  plugins: [
    createPersistedState(),
  ],
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    UserStore,
  },
});
