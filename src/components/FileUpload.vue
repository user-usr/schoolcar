<script>

import {mapGetters} from "vuex";
import {mixin} from "@/mixins";

export default {
  name: 'FileUpload',
  mixins: [mixin],
  props: {
    fileUsage: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      fileList: [],
      headers: {},
    }
  },
  computed: {
    ...mapGetters({
      token: 'basic/token',
      id: 'basic/id',
    })
  },
  created() {
    this.headers = {
      'Content-Type': 'multipart/form-data',
      token: this.token,
      id: this.id,
      fileUsage: encodeURIComponent(this.fileUsage),
    }
  },
  methods: {
    handleUploadFile(item) {
      let formData = new FormData();
      formData.append('file', item.file)
      this.uploadFile(this.headers, formData)
          .then(res => {
            if (res.code === 200) {
              this.showElNotification('上传成功','','success')
              this.$emit('fileSubmit', res.message)
              item.onSuccess()
            } else {
              this.showElNotification('上传失败','','error')
              item.onError()
            }
          })
    },
    handleUploadBeforeUpload(file) {
      if (file.size > 1048576) {

        return false
      }
    },
    submitUpload() {
      this.$refs.upload.submit();
    }
  },
}
</script>

<template>
  <div class="upload-page">
    <el-text type="info">
      {{ fileUsage }}
    </el-text>
    <el-upload
        action=""
        :multiple="true"
        :show-file-list="true"
        :before-upload="handleUploadBeforeUpload"
        :auto-upload="false"
        :file-list="fileList"
        ref="upload"
        :http-request="handleUploadFile"
        accept=".jpg, .jpeg, .png"
    >
      <template #trigger>
        <el-button size="small" type="primary">选取文件</el-button>
      </template>
      <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传文件</el-button>

      <template #tip>
        <el-text type="info" class="upload-tip">
          <br>一次只能选择一个文件，多个文件请多次选择
          <br>支持.jpg .jpeg .png格式 大小不超过1MB
        </el-text>
      </template>
    </el-upload>
  </div>
</template>

<style scoped>
.upload-tip {
  font-size: small;
}

</style>
