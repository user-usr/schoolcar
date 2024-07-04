<script>

import {mapGetters} from "vuex";
import axios from "@/Axios";

export default {
  name: 'NotifyListTable',
  data() {
    return {
      notifyList: [],
      expands: [],
      emptyText: '',
    }
  },
  computed: {
    ...mapGetters({
      token: 'basic/token',
      id: 'basic/id',
    })
  },
  methods: {
    getNotifyData() {
      return axios({
        url: `/notify/${this.id}`,
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
      const res = await this.getNotifyData()
      if (res.code === 200) {
        this.notifyList = res.data.sort((a, b) => {
          const dateA = new Date(a.time);
          const dateB = new Date(b.time);
          if (dateA > dateB) {
            return -1;
          } else if (dateA < dateB) {
            return 1;
          } else {
            return 0;
          }
        })
      } else {
        this.emptyText = res.message
      }
    },
    rowKey(row) {
      return row.id
    },
    handleRowClick(row) {
      if (this.expands.includes(row.id)) {
        this.expands = []
      } else {
        this.expands = [row.id]
      }
    },
    async downloadFile(path) {
      const data = await axios({
        url: '/file/download',
        method: 'get',
        headers: {
          token: this.token,
          id: this.id
        },
        params: {
          path: path
        },
        responseType: 'blob'
      }).then(res => {
        return res.data
      })
      const link = document.createElement('a');
      let blob = new Blob([data], {
        type: 'application/msexcel;charset=utf-8'
      });
      link.style.display = 'none'
      link.href = URL.createObjectURL(blob);
      link.setAttribute('download', path.split('/').pop());
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    }
  },
  async created() {
    await this.getData()
  }
}
</script>

<template>
  <div class="list-table">
    <el-table
        :data="notifyList"
        :row-key="rowKey"
        @row-click="handleRowClick"
        :expand-row-keys="expands"
        :empty-text="emptyText"
    >
      <el-table-column type="expand">
        <template #default="props">
          <div style="background-color: #fdfdfd" :id="props">
            <el-link v-if="props.row.content_type === 'link'" @click="downloadFile(props.row.content)">
              {{ props.row.content.split('/').pop() }}
            </el-link>
            <el-text v-if="props.row.content_type === 'text'" class="text-content">
              {{ props.row.content }}
            </el-text>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="标题" prop="title"/>
      <el-table-column label="发起人">
        <template #default="props">
          <el-text v-if="props.row.fid === '0'">
            系统通知
          </el-text>
        </template>
      </el-table-column>
      <el-table-column label="时间" prop="time"/>
    </el-table>
  </div>
</template>

<style scoped src="@/assets/css/ListTableCss.css">

</style>
