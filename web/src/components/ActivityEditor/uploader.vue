<template>
  <el-upload
    class="avatar-uploader"
    action="/api/photo"
    name="img"
    :show-file-list="false"
    :on-success="handleAvatarSuccess"
    :before-upload="beforeAvatarUpload"
  >
    <el-image id="image" v-if="url" :src="url" fit="contain" class="avatar">
      <div slot="placeholder">加载中</div>
    </el-image>
    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
  </el-upload>
</template>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 300px;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  text-align: center;
  display: block;
}
.avatar {
  width: 100%;
  height: 100%;
  display: block;
}

#image {
  width: 100%;
  height: 100%;
}
</style>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';

@Component({})
export default class ImageUploader extends Vue {
  @Prop({default: ''})
  private imageUrl!: string;

  get url() {
    return this.imageUrl;
  }

  private handleAvatarSuccess(res: {data: string}) {
    this.$emit('submit', res.data);
  }

  private beforeAvatarUpload(file: any) {
    return true;
  }
}
</script>
