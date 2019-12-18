<template>
  <div>
    <h1>用户管理</h1>

    <div id="search-input">
      <el-input model="searchText" placeholder="请输入用户名(留空则加载全部)">
        <el-button slot="append" @click="search">查询</el-button>
      </el-input>
    </div>

    <div style="height: 30px" />
    <el-table :data="users" v-loading="loading">
      <el-table-column label="用户名" prop="username"></el-table-column>
      <el-table-column label="注册时间">
        <template slot-scope="scope">{{new Date(scope.row.addtime).toLocaleString()}}</template>
      </el-table-column>
      <el-table-column label="角色">
        <template slot-scope="scope">{{roleText(scope.row.role)}}</template>
      </el-table-column>
      <el-table-column label="状态">
        <template slot-scope="scope">
          <tag :status="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button v-if="!scope.row.status" size="mini" type="danger" @click="banUser(scope.row.id, true)">
            <i class="el-icon-lock" />封禁
          </el-button>
          <el-button v-else size="mini" type="primary" @click="banUser(scope.row.id, false)">
            <i class="el-icon-unlock" />解封
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { User, UserRole } from '../../types/user';
import { getUserByUsername, banUser } from '../../api/user';
import { apiErrorMessage } from '../../common/apiErrorMessage';
import Tag from './userStatusTag.vue';

@Component({
  components: {
    Tag,
  },
})
export default class UserManage extends Vue {
  private users: User[] = [];

  private searchText = '';
  private loading = false;

  private search() {
    this.loading = true;
    getUserByUsername(this.searchText)
      .then((users) => (this.users = users))
      .catch((err: any) => apiErrorMessage(this, err))
      .finally(() => (this.loading = false));
  }

  private roleText(role: UserRole) {
    switch (role) {
      case UserRole.General:
        return '普通用户';
      case UserRole.Manager:
        return '管理员';
    }
  }

  private banUser(userId: string, ban: boolean) {
    banUser(userId, ban)
      .then(() => {
        this.$message.success('操作成功');
        this.search();
      })
      .catch((err: any) => apiErrorMessage(this, err));
  }
}
</script>

<style scoped>
.clear {
  clear: both;
  width: 80%;
  height: max-content;
}

.full-width {
  width: 90% !important;
  margin-left: auto;
  margin-right: auto;
}

#description-wrapper {
  margin: 0px 40px;
  text-align: left;
}

#search-input {
  width: 60%;
  margin-left: auto;
  margin-right: auto;
}
</style>