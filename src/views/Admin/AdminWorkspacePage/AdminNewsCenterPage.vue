<script>

import {mapGetters} from "vuex";
import axios from "@/Axios";
import {ElNotification} from "element-plus";
import InsertNewsForm from "@/components/InsertNewsForm.vue";

export default {
  name: 'AdminNewsCenterPage',
  components: {InsertNewsForm},
  data() {
    return {
      expands: [],
      newsData: [],
      creating: false,
    }
  },
  computed: {
    ...mapGetters({
      token: 'basic/token',
      id: 'basic/id',
    })
  },
  methods: {
    getNewsData() {
      return axios({
        url: '/news/getAll',
        method: 'get',
        headers: {
          token: this.token,
          id: this.id,
        }
      }).then(res => {
        return res.data
      })
    },
    async getData() {
      const res = await this.getNewsData()
      this.newsData = res.data
    },
    rowKey(row) {
      return row.nid
    },
    handleRowClick(row) {
      if (this.expands.includes(row.nid)) {
        this.expands = []
      } else {
        this.expands = []
        this.expands.push(row.nid)
      }
    },
    async deleteNews(nid) {
      const res = await this.doDelete(nid)
      if (res.code === 200) {
        ElNotification({
          title: '删除成功',
          type: 'success'
        })
        await this.getData()
      } else {
        ElNotification({
          title: '删除失败',
          type: 'error'
        })
      }
    },
    doDelete(nid) {
      return axios({
        url: '/news/delete',
        method: 'post',
        headers: {
          token: this.token,
          id: this.id,
        },
        params: {
          nid: nid
        }
      }).then(res => {
        return res.data
      })
    }
  },
  async created() {
    await this.getData()
  },
}
</script>

<template>
  <div>
    <el-row justify="end">
      <el-button plain type="primary" @click="creating = true">新建</el-button>
    </el-row>
    <el-table
        :data="newsData"
        @row-click="handleRowClick"
        :row-key="rowKey"
        :expand-row-keys="expands"
        height="600"
    >
      <el-table-column type="expand">
        <template #default="props">
          <div style="background-color: #fdfdfd">
            <el-text class="news-content">
              {{ props.row.n_Content }}
            </el-text>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="标题" prop="n_Title"/>
      <el-table-column label="作者" prop="n_Author"/>
      <el-table-column label="发布时间" prop="n_Time"/>
      <el-table-column label="操作">
        <template #default="props">
          <el-button plain size="small" type="danger" @click="deleteNews(props.row.nid)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
        v-model="creating"
        title="新建新闻"
        :close-on-click-modal="false"
        style="display: flex;justify-content: center;align-items: center;flex-direction: column;"
    >
      <insert-news-form></insert-news-form>
    </el-dialog>
  </div>
</template>

<style scoped>

</style>
