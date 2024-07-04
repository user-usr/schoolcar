<script>
import {mapGetters} from "vuex";
import axios from "@/Axios";
import InsertUserForm from "@/components/InsertUserForm.vue";
import UserInformationForm from "@/components/User/UserPage/UserInformationForm.vue";
import {ElNotification} from "element-plus";

export default {
  name: 'AdminRegularUsersPage',
  components: {UserInformationForm, InsertUserForm},
  data() {
    return {
      startIndex: 0,
      endIndex: 100,
      theUserTableData: [],
      importing: false,
      emptyText: '没数据',
      usersData: [],
      creating: false,
      updating: false,
      importUser: false,
      uid: '',
      thePick: {
        realNamePick: '',
        studentIdPick: '',
        collegePick: '',
        majorPick: '',
        gradePick: '',
        classPick: '',
      },
      studentIds: [],
      realNames: [],
      colleges: [],
      majors: [],
      grades: [],
      classes: [],
    }
  },
  computed: {
    ...mapGetters({
      token: 'basic/token',
      id: 'basic/id',
    })
  },
  methods: {
    getUsersData() {
      return axios({
        url: '/user/all',
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
      this.theUserTableData = []
      this.usersData = []
      this.studentIds = []
      this.realNames = []
      this.colleges = []
      this.majors = []
      this.grades = []
      this.classes = []
      const res = await this.getUsersData()
      if (res.code === 200) {
        this.usersData = res.data
        this.getUserTableData()
      } else {
        this.emptyText = res.message
      }
    },
    getUserTableData() {
      if (this.theUserTableData.length < this.usersData.length) {
        let data = []
        if (this.endIndex <= this.usersData.length) {
          data = this.usersData.slice(this.startIndex, this.endIndex);
        } else {
          data = this.usersData.slice(this.startIndex, this.usersData.length)
        }
        this.theUserTableData = this.theUserTableData.concat(data);
        for (let item of data) {
          if (!this.studentIds.includes(item.u_StudentID)) {
            this.studentIds.push(item.u_StudentID)
          }
          if (!this.realNames.includes(item.u_RealName)) {
            this.realNames.push(item.u_RealName)
          }
          if (!this.colleges.includes(item.u_College)) {
            this.colleges.push(item.u_College)
          }
          if (!this.majors.includes(item.u_Major)) {
            this.majors.push(item.u_Major)
          }
          if (!this.grades.includes(item.u_Grade)) {
            this.grades.push(item.u_Grade)
          }
          if (!this.classes.includes(item.u_Class)) {
            this.classes.push(item.u_Class)
          }
        }
        this.startIndex += 100
        this.endIndex += 100
      }
    },
    clickRow(rowData) {
      this.uid = rowData.uid
      this.updating = true
    },
    async createSuccess() {
      await this.getData()
      this.creating = false
    },
    async pick() {
      await this.getData()
    },
    async reSet() {
      this.thePick = {
        realNamePick: '',
        studentIdPick: '',
        collegePick: '',
        majorPick: '',
        gradePick: '',
        classPick: '',
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
      this.importing = true
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
      this.importing = false
    },
    excelUpload(formData) {
      return axios({
        url: '/user/import',
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
    async handleExportUser() {
      const data = await this.exportUser()
      const link = document.createElement('a');
      let blob = new Blob([data], {
        type: 'application/msexcel;charset=utf-8'
      });
      link.style.display = 'none'
      link.href = URL.createObjectURL(blob);
      link.setAttribute('download', '用户信息.xlsx');
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    },
    exportUser() {
      return axios({
        url: '/user/export',
        method: 'post',
        headers: {
          token: this.token,
          id: this.id,
        },
        data: this.theUserTableData,
        responseType: 'blob'
      }).then(res => {
        return res.data
      })
    },
    async getImportModel() {
      const data = await axios({
        url: '/user/import-model',
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
      link.setAttribute('download', '用户批量注册模板.xlsx')
    },
    userTableLoading() {
      let dom = document.querySelector('.el-scrollbar__wrap');
      dom.addEventListener('scroll', () => {
        const scrollDistance = dom.scrollHeight - dom.scrollTop -dom.clientHeight;
        if (scrollDistance <= 1) {
          this.getUserTableData()
        }
      });
    },
    showAll() {
      this.theUserTableData = this.usersData

      this.studentIds = []
      this.realNames = []
      this.colleges = []
      this.majors = []
      this.grades = []
      this.classes = []
      this.theUserTableData.forEach(item => {
        if (!this.studentIds.includes(item.u_StudentID)) {
          this.studentIds.push(item.u_StudentID)
        }
        if (!this.realNames.includes(item.u_RealName)) {
          this.realNames.push(item.u_RealName)
        }
        if (!this.colleges.includes(item.u_College)) {
          this.colleges.push(item.u_College)
        }
        if (!this.majors.includes(item.u_Major)) {
          this.majors.push(item.u_Major)
        }
        if (!this.grades.includes(item.u_Grade)) {
          this.grades.push(item.u_Grade)
        }
        if (!this.classes.includes(item.u_Class)) {
          this.classes.push(item.u_Class)
        }
      })
    },
  },
  async created() {
    await this.getData()
    await this.getImportModel()
  },
  mounted() {
    this.userTableLoading()
  }
}

</script>

<template>
  <div>
    <el-row justify="space-between" style="margin-bottom: 10px">
      <div>
        <el-link id="importModel" type="primary">用户批量注册模板.xlsx</el-link>
      </div>
      <div>
        <el-text type="danger" size="small">
          默认显示100条数据，下滑显示更多，查看所有请点击全部
          <br>查询功能请结合全部显示和筛选进行
        </el-text>
      </div>
      <div>
        <el-button plain type="primary" @click="creating = true">新建</el-button>
        <el-button plain type="success" @click="importUser = true">批量注册</el-button>
        <el-tooltip
            effect="light"
            placement="bottom"
        >
          <template #content>
            <el-text size="small">
              导出的是表格当前显示的数据
            </el-text>
          </template>
          <el-button plain type="warning" @click="handleExportUser">导出</el-button>
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
    <el-row style="display: flex; flex-wrap: nowrap;">
      <el-select
          v-model="thePick.realNamePick"
          placeholder="请选择姓名"
          size="small"
          style="width: 300px"
          filterable
          clearable
          @clear="thePick.realNamePick = ''"
      >
        <el-option
            v-for="item in realNames"
            :key="item"
            :value="item"
        />
      </el-select>
      <el-select
          v-model="thePick.studentIdPick"
          placeholder="请选择学号"
          size="small"
          style="width: 300px; margin-left: 10px"
          filterable
          clearable
          @clear="thePick.studentIdPick = ''"
      >
        <el-option
            v-for="item in studentIds"
            :key="item"
            :value="item"
        />
      </el-select>
      <el-select
          v-model="thePick.collegePick"
          placeholder="请选择学院"
          size="small"
          style="width: 300px; margin-left: 10px"
          filterable
          clearable
          @clear="thePick.collegePick = ''"
      >
        <el-option
            v-for="item in colleges"
            :key="item"
            :value="item"
        />
      </el-select>
      <el-select
          v-model="thePick.majorPick"
          placeholder="请选择专业"
          size="small"
          style="width: 300px; margin-left: 10px"
          filterable
          clearable
          @clear="thePick.majorPick = ''"
      >
        <el-option
            v-for="item in majors"
            :key="item"
            :value="item"
        />
      </el-select>
      <el-select
          v-model="thePick.gradePick"
          placeholder="请选择年级"
          size="small"
          style="width: 300px; margin-left: 10px"
          filterable
          clearable
          @clear="thePick.gradePick = ''"
      >
        <el-option
            v-for="item in grades"
            :key="item"
            :value="item"
        />
      </el-select>
      <el-select
          v-model="thePick.classPick"
          placeholder="请选择班级"
          size="small"
          style="width: 300px; margin-left: 10px"
          filterable
          clearable
          @clear="thePick.classPick = ''"
      >
        <el-option
            v-for="item in classes"
            :key="item"
            :value="item"
        />
      </el-select>
      <el-button size="small" style="margin-left: 10px" @click="pick" plain type="primary">筛选</el-button>
      <el-button size="small" @click="reSet" plain type="warning">重置</el-button>
    </el-row>
    <el-table
        :data="theUserTableData"
        @row-click="clickRow"
        :empty-text="emptyText"
        height="600"
    >
      <el-table-column label="用户名" prop="u_Name"/>
      <el-table-column label="姓名" prop="u_RealName"/>
      <el-table-column label="学号" prop="u_StudentID"/>
      <el-table-column label="学院" prop="u_College"/>
      <el-table-column label="专业" prop="u_Major"/>
      <el-table-column label="年级" prop="u_Grade"/>
      <el-table-column label="班级" prop="u_Class"/>
    </el-table>

    <el-dialog
        v-model="creating"
        title="新建用户"
        :close-on-click-modal="false"
        style="display: flex;justify-content: center;align-items: center;flex-direction: column;"
    >
      <insert-user-form @createSuccess="createSuccess"></insert-user-form>
    </el-dialog>
    <el-dialog
        v-model="updating"
        :close-on-click-modal="false"
        :destroy-on-close="true"
    >
      <user-information-form :uid="uid"></user-information-form>
    </el-dialog>
    <el-dialog
        v-model="importUser"
        :close-on-click-modal="false"
        :destroy-on-close="true"
        title="批量注册用户"
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
          :limit="1"
          :http-request="handleUpload"
          accept=".xlsx"
      >
        <template #trigger>
          <el-button size="small" type="primary">选取文件</el-button>
        </template>
        <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload" :loading="importing">
          上传文件
        </el-button>
        <template #tip>
          <el-text type="info" class="upload-tip">
            <br>一次只能选择一个文件 一次只能上传一个文件
            <br>上传缓慢 请耐心等待
          </el-text>
        </template>
      </el-upload>
    </el-dialog>
  </div>
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
