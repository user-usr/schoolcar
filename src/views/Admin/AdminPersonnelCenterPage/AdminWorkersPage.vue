<script>

import {mapGetters} from "vuex";
import axios from "@/Axios";
import InsertWorkerForm from "@/components/InsertWorkerForm.vue";
import WorkerInfForm from "@/components/Worker/WorkerInfForm.vue";
import {ElNotification} from "element-plus";

export default {
  name: 'AdminWorkersPage',
  components: {WorkerInfForm, InsertWorkerForm},
  data() {
    return {
      startIndex: 0,
      endIndex: 100,
      theWorkerTableData: [],
      importing: false,
      importWorker: false,
      uploadFalseData: [],
      workersData: [],
      creating: false,
      updating: false,
      emptyText: '没数据',
      wid: '',
      thePick: {
        realNamePick: '',
        departmentPick: '',
        branchPick: '',
        jobNumberPick: '',
      },
      realNames: [],
      departments: [],
      branches: [],
      jobNumbers: [],
    }
  },
  computed: {
    ...mapGetters({
      token: 'basic/token',
      id: 'basic/id',
    })
  },
  methods: {
    getWorkersData() {
      return axios({
        url: '/worker/all',
        method: 'get',
        headers: {
          token: this.token,
          id: this.id,
        },
        params: this.thePick
      }).then(res => {
        return res.data
      })
    },
    async getData() {
      this.startIndex = 0
      this.endIndex = 100
      this.theWorkerTableData = []
      this.workersData = []
      this.realNames = []
      this.departments = []
      this.branches = []
      this.jobNumbers = []
      const res = await this.getWorkersData()
      if (res.code === 200) {
        this.workersData = res.data
        this.getWorkerTableData()
      } else {
        this.emptyText = res.message
      }
    },
    getWorkerTableData() {
      if (this.theWorkerTableData.length < this.workersData.length) {
        let data = []
        if (this.endIndex <= this.workersData.length) {
          data = this.workersData.slice(this.startIndex, this.endIndex);
        } else {
          data = this.workersData.slice(this.startIndex, this.workersData.length)
        }
        this.theWorkerTableData = this.theWorkerTableData.concat(data);
        for (let item of data) {
          if (!this.realNames.includes(item.w_RealName)) {
            this.realNames.push(item.w_RealName)
          }
          if (!this.departments.includes(item.w_Department)) {
            this.departments.push(item.w_Department)
          }
          if (!this.branches.includes(item.w_Branch)) {
            this.branches.push(item.w_Branch)
          }
          if (!this.jobNumbers.includes(item.w_JobNumber)) {
            this.jobNumbers.push(item.w_JobNumber)
          }
        }
        this.startIndex += 100
        this.endIndex += 100
      }
    },
    clickRow(rowData) {
      this.wid = rowData.wid
      this.updating = true
    },
    async createSuccess() {
      this.creating = false
      await this.getData()
    },
    async pick() {
      await this.getData()
    },
    async reSet() {
      this.thePick = {
        realNamePick: '',
        departmentPick: '',
        branchPick: '',
        jobNumberPick: '',
      };
      await this.getData()
    },
    handleBeforeUpload(file) {
      if (file.size > 1048576) {
        ElNotification({
          title: '文件太大，请分批次导入',
          type: 'error',
          duration: 1500
        })
        return false
      }
    },
    submitUpload() {
      this.importing = true;
      this.$refs.upload.submit();
    },
    async handleUpload(item) {
      let formData = new FormData();
      formData.append('file', item.file)
      const res = await this.excelUpload(formData)
      if (res.code === 200) {
        ElNotification({
          title: '上传成功',
          type: 'success',
        })
        item.onSuccess()
      } else {
        ElNotification({
          title: '上传失败',
          type: 'error',
        })
        item.onError()
      }
      this.importing = false;
    },
    excelUpload(formData) {
      return axios({
        url: '/worker/import',
        method: 'post',
        headers: {
          'Content-Type': 'multipart/form-data',
          token: this.token,
          id: this.id,
        },
        data: formData
      }).then(res => {
        return res.data
      })
    },
    handleClose(done) {
      this.uploadFalseData = []
      done()
    },
    async handleExportWorker() {
      const data = await this.exportWorker()
      const link = document.createElement('a');
      let blob = new Blob([data], {
        type: 'application/msexcel;charset=utf-8'
      });
      link.style.display = 'none'
      link.href = URL.createObjectURL(blob);
      link.setAttribute('download', '工作人员信息.xlsx');
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    },
    exportWorker() {
      return axios({
        url: '/worker/export',
        method: 'post',
        headers: {
          token: this.token,
          id: this.id,
        },
        data: this.theWorkerTableData,
        responseType: 'blob'
      }).then(res => {
        return res.data
      })
    },
    async getImportModel() {
      const data = await axios({
        url: '/worker/import-model',
        method: 'get',
        headers: {
          token: this.token,
          id: this.id,
        },
        responseType: 'blob',
      }).then(res => {
        return res.data
      })
      let link = document.getElementById('importModel');
      let blob = new Blob([data], {
        type: 'application/msexcel;charset=utf-8'
      });
      link.href = URL.createObjectURL(blob);
      link.setAttribute('download', '工作人员批量注册模板.xlsx')
    },
    workerTableLoading() {
      let dom = document.querySelector('.el-scrollbar__wrap');
      dom.addEventListener('scroll', () => {
        const scrollDistance = dom.scrollHeight - dom.scrollTop -dom.clientHeight;
        if (scrollDistance <= 1) {
          this.getWorkerTableData()
        }
      });
    },
    async handleExportFalseWorker() {
      const data = await axios({
        url: '/worker/export-false',
        method: 'post',
        headers: {
          token: this.token,
          id: this.id,
        },
        data: this.uploadFalseData,
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
      link.setAttribute('download', '注册失败工作人员名单.xlsx');
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    },
    showAll() {
      this.theWorkerTableData = this.workersData
      this.realNames = []
      this.departments = []
      this.branches = []
      this.jobNumbers = []
      this.theWorkerTableData.forEach(item => {
        if (!this.realNames.includes(item.w_RealName)) {
          this.realNames.push(item.w_RealName)
        }
        if (!this.departments.includes(item.w_Department)) {
          this.departments.push(item.w_Department)
        }
        if (!this.branches.includes(item.w_Branch)) {
          this.branches.push(item.w_Branch)
        }
        if (!this.jobNumbers.includes(item.w_JobNumber)) {
          this.jobNumbers.push(item.w_JobNumber)
        }
      })
    }
  },
  async created() {
    await this.getData()
    await this.getImportModel()
  },
  mounted() {
    this.workerTableLoading()
  }
}
</script>

<template>
  <el-row justify="space-between" style="margin-bottom: 10px">
    <div>
      <el-link id="importModel" type="primary">工作人员批量注册模板.xlsx</el-link>
    </div>
    <div>
      <el-text type="danger" size="small">
        默认显示100条数据，下滑显示更多，查看所有请点击全部
        <br>查询功能请结合全部显示和筛选进行
      </el-text>
    </div>
    <div>
      <el-button plain type="primary" @click="creating = true">新建</el-button>
      <el-button plain type="success" @click="importWorker = true">批量注册</el-button>
      <el-tooltip
          effect="light"
          placement="bottom"
      >
        <template #content>
          <el-text size="small">
            导出的是表格当前显示的数据
          </el-text>
        </template>
        <el-button plain type="warning" @click="handleExportWorker">导出</el-button>
      </el-tooltip>
      <el-tooltip
          effect="light"
          placement="bottom"
      >
        <template #content>
          <el-text size="small">
            数量太多可能会导致页面卡顿，请耐心等待
            <br>点击筛选处重置按钮，恢复默认显示
          </el-text>
        </template>
        <el-button plain type="info" @click="showAll">全部</el-button>
      </el-tooltip>
    </div>
  </el-row>
  <el-row style="display: flex; flex-wrap: nowrap">
    <el-select
        v-model="thePick.realNamePick"
        placeholder="请选择姓名"
        size="small"
        style="width: 300px"
        filterable
        @clear="thePick.realNamePick = ''"
    >
      <el-option
          v-for="item in realNames"
          :key="item"
          :value="item"
      />
    </el-select>
    <el-select
        v-model="thePick.jobNumberPick"
        placeholder="请选择工号"
        size="small"
        style="width: 300px; margin-left: 10px"
        filterable
        @clear="thePick.jobNumberPick = ''"
    >
      <el-option
          v-for="item in jobNumbers"
          :key="item"
          :value="item"
      />
    </el-select>
    <el-select
        v-model="thePick.departmentPick"
        placeholder="请选择部门"
        size="small"
        style="width: 300px; margin-left: 10px"
        filterable
        @clear="thePick.departmentPick = ''"
    >
      <el-option
          v-for="item in departments"
          :key="item"
          :value="item"
      />
    </el-select>
    <el-select
        v-model="thePick.branchPick"
        placeholder="请选择科室"
        size="small"
        style="width: 300px; margin-left: 10px"
        filterable
        @clear="thePick.branchPick = ''"
    >
      <el-option
          v-for="item in branches"
          :key="item"
          :value="item"
      />
    </el-select>
    <el-button size="small" style="margin-left: 10px" @click="pick" plain type="primary">筛选</el-button>
    <el-button size="small" @click="reSet" plain type="warning">重置</el-button>
  </el-row>

  <el-table
      :data="theWorkerTableData"
      @row-click="clickRow"
      :empty-text="emptyText"
      height="600"
  >
    <el-table-column label="用户名" prop="w_Name"/>
    <el-table-column label="姓名" prop="w_RealName"/>
    <el-table-column label="工号" prop="w_JobNumber"/>
    <el-table-column label="部门" prop="w_Department"/>
    <el-table-column label="科室" prop="w_Branch"/>
  </el-table>
  <el-dialog
      v-model="creating"
      title="新建工作人员"
      :close-on-click-modal="false"
      style="display: flex;justify-content: center;align-items: center;flex-direction: column;"
  >
    <insert-worker-form @createSuccess="createSuccess"></insert-worker-form>
  </el-dialog>
  <el-dialog
      v-model="updating"
      :close-on-click-modal="false"
      :destroy-on-close="true"
  >
    <worker-inf-form :wid="wid"></worker-inf-form>
  </el-dialog>
  <el-dialog
      v-model="importWorker"
      :close-on-click-modal="false"
      :destroy-on-close="true"
      title="批量注册工作人员"
      :before-close="handleClose"
  >
    <el-row justify="center" style="margin-bottom: 10px;">
      <div class="tip">
        <p style="font-weight: 700">提醒</p>
        <p style="font-size: .9rem; margin-top: 10px">
          上传成功后即可关闭
          <br>注册完成后失败名单将以通知形式返回
          <br>通知见主页通知板块
        </p>
      </div>
    </el-row>
    <el-upload
        ref="upload"
        action=""
        :multiple="true"
        :show-file-list="true"
        :before-upload="handleBeforeUpload"
        :auto-upload="false"
        :limit="3"
        :http-request="handleUpload"
        accept=".xlsx"
    >
      <template #trigger>
        <el-button size="small" type="primary">选取文件</el-button>
      </template>
      <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload" :loading="importing">上传文件</el-button>
      <template #tip>
        <el-text type="info" class="upload-tip">
          <br>一次只能选择一个文件 一次只能上传一个文件
          <br>上传缓慢 请耐心等待
        </el-text>
      </template>
    </el-upload>
  </el-dialog>
</template>

<style scoped>
.tip {
  padding: 8px 16px;
  background-color: #66b1ff;
  border-radius: 4px;
  border-left: 5px solid #007cff;
  margin: 20px 0;
}
</style>
