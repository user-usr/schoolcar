<script>

import axios from "@/Axios";
import {mapGetters} from "vuex";
import {ElNotification} from "element-plus";

export default {
  name: 'InsertNewsForm',
  data() {
    return {
      newsData: {
        n_Title: '',
        n_Content: '',
        n_Time: '',
        n_Author: '',
      },
      newsFormRules: {
        n_Title: [
          {required: true, message: '请输入新闻标题', trigger: 'blur'},
        ],
        n_Content: [
          {required: true, message: '请输入新闻内容', trigger: 'blur'},
        ],
        n_Time: [
          {required: true, message: '请选择新闻时间', trigger: 'blur'},
        ],
        n_Author: [
          {required: true, message: '请输入新闻作者', trigger: 'blur'},
        ],
      }
    }
  },
  computed: {
    ...mapGetters({
      token: 'basic/token',
      id: 'basic/id',
    })
  },
  methods: {
    addNews() {
      return axios({
        url: '/news/add',
        method: 'post',
        data: this.newsData,
        headers: {
          token: this.token,
          id: this.id,
        }
      }).then(res => {
        return res.data
      })
    },
    submitForm() {
      this.$refs.theRef.validate((valid) => {
        if (valid) {
          this.addNews().then(res => {
            if (res.code === 200) {
              ElNotification({
                title: '添加成功',
                message: '',
                type: 'success',
              })
            } else {
              ElNotification({
                title: '添加失败',
                message: res.msg,
                type: 'error',
              })
            }
          })
        } else {
          return false
        }
      })
    }
  },
}
</script>

<template>
  <div>
    <el-form
        ref="theRef"
        :model="newsData"
        :rules="newsFormRules"
        label-position="right"
    >
      <el-form-item label="标题" prop="n_Title">
        <el-input v-model="newsData.n_Title" placeholder="输入标题"/>
      </el-form-item>
      <el-row>
        <el-form-item label="作者" prop="n_Author">
          <el-input v-model="newsData.n_Author" placeholder="输入作者"/>
        </el-form-item>
        <el-form-item label="发布时间" prop="n_Time" style="margin-left: 10px">
          <el-date-picker
              v-model="newsData.n_Time"
              type="datetime"
              placeholder="选择发布日期时间"
              editable
              clearable
              format="YYYY-MM-DD HH:mm:ss"
              value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-form-item>
      </el-row>
      <el-form-item label="内容" prop="n_Content">
        <el-input
            v-model="newsData.n_Content"
            type="textarea"
            :autosize="{ minRows: 10 , maxRows: 20}"
            placeholder="输入内容"
        />
      </el-form-item>
      <el-row justify="center">
        <el-button plain type="primary" @click="submitForm">添加</el-button>
      </el-row>
    </el-form>
  </div>
</template>

<style scoped>

</style>
