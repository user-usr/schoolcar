<script>

import {mapGetters} from "vuex";
import {mixin} from "@/mixins";
import CarDataDescription from "@/components/User/CarPage/CarDataDescription.vue";
import {GobletSquareFull} from "@element-plus/icons-vue";
import axios from "@/Axios";

export default {
  name: 'WorkspaceScroller',
  components: {GobletSquareFull, CarDataDescription},
  mixins: [mixin],
  data() {
    return {
      getting: true,
      showDetail: false,
      detailData: {},
      carOldData: {},
      carNewData: {},
      messageDataS: [],
      haven: false,
      carState: false,
      stateValues: {
        '0': '审核中',
        '1': '审核通过',
        '2': '审核失败',
      },
      stateClass: {
        '0': 'class-doing',
        '1': 'class-access',
        '2': 'class-pass',
      }
    }
  },
  props: [
    'state',
  ],
  computed: {
    ...mapGetters({
      token: 'basic/token',
      id: 'basic/id',
    })
  },
  methods: {
    ShowDetail(item) {
      this.carState = false
      this.carNewData = {}
      this.carOldData = {}
      this.detailData = Object.assign({}, item)
      if (this.detailData.m_Type === '电动车信息更新') {
        if (this.detailData.m_State === '0') {
          this.getCarInformationData(this.token, this.id, this.detailData.uid).then(res => {
            this.carOldData = res.data
          })
        } else {
          this.getCarOldInfData(this.detailData.uid, this.detailData.m_CreateTime).then(res => {
            this.carOldData = res.data
          })
        }
        this.carState = true
      }
      this.getCarNewInformationData(this.detailData.uid, this.detailData.m_CreateTime).then(res => {
        this.carNewData = res.data
      })
      if (this.detailData.m_Evidence !== null) {
        this.detailData.m_Evidence = Object.assign([], item.m_Evidence.split(','))
      }
      this.showDetail = true
    },
    getCarOldInfData(uid, time) {
      return axios({
        url: '/car/car-inf-old-data',
        method: 'get',
        headers: {
          token: this.token,
          id: this.id,
        },
        params: {
          uid: uid,
          time: time,
        }
      }).then(res => {
        return res.data
      })
    },
    getCarNewInformationData(uid, time) {
      return axios({
        url: '/car/car-information-new-data',
        method: 'get',
        headers: {
          token: this.token,
          id: this.id,
        },
        params: {
          uid: uid,
          time: time,
        }
      }).then(res => {
        return res.data
      })
    },
    getIcon(state) {
      if (state === '0') {
        return 'iconfont icon-doing-icon'
      } else if (state === '1') {
        return 'iconfont icon-access-icon'
      } else if (state === '2') {
        return 'iconfont icon-pass-icon'
      }
    },
    getMessageByIDAndType() {
      return axios({
        url: `/message/getAll`,
        method: 'get',
        headers: {
          token: this.token,
          id: this.id,
        },
        params: {
          state: this.state
        }
      }).then(res => {
        return res.data
      })
    },
    async doMessage(type) {
      const res = await axios({
        url: `/message/` + type,
        method: 'post',
        headers: {
          token: this.token,
          id: this.id,
        },
        data: {
          mid: this.detailData.mid,
          m_Description: this.detailData.m_Description,
        }
      }).then(res => {
        return res.data
      })
      if (res.code === 200) {
        this.showElNotification('处理成功', '', 'success')
        await this.getMessages()
        this.showDetail = false
      }
    },
    async doYes() {
      await this.doMessage('doYes');
    },
    async doNo() {
      await this.doMessage('doNo');
    },
    async getMessages() {
      this.haven = false
      this.getting = true
      const res = await this.getMessageByIDAndType();
      if (res.code === 200 && res.data !== null) {
        this.messageDataS = res.data
        this.haven = this.messageDataS.length !== 0;
      }
      this.getting = false
    }
  },
  async created() {
    await this.getMessages()
  }
}
</script>

<template>
    <div v-if="haven">
      <el-skeleton :rows="4" animated style="margin-top: 10px" v-if="getting" :throttle="500"/>
      <el-scrollbar
          style="height: 600px"
      >
        <div v-for="(item, index) in messageDataS" :key="index"
             style="padding: 5px; border-radius: 5px; margin-top: 5px"
             :class="stateClass[item.m_State]">
          <el-descriptions border>
            <template #title>
              <i :class="getIcon(item.m_State)" style="font-size: small"></i>
              <el-text style="margin-left: 10px" size="large">{{ item.m_Type }}</el-text>
            </template>
            <el-descriptions-item label="发起人">{{ item.u_RealName }}</el-descriptions-item>
            <el-descriptions-item label="处理人">{{ item.w_RealName }}</el-descriptions-item>
            <el-descriptions-item label="状态">{{ stateValues[item.m_State] }}</el-descriptions-item>
            <el-descriptions-item label="描述">{{ item.m_Description }}</el-descriptions-item>
            <template #extra>
              <el-button type="primary" @click="ShowDetail(item)">查看详情</el-button>
            </template>
          </el-descriptions>
        </div>
      </el-scrollbar>

      <el-dialog v-model="showDetail"
                 :title="detailData.m_Type"
                 :close-on-click-modal="false"
                 :show-close="true"
                 :destroy-on-close="true"
      >
        <el-descriptions border>
          <el-descriptions-item label="发起人">{{ detailData.u_RealName }}</el-descriptions-item>
          <el-descriptions-item label="处理人">{{ detailData.w_RealName }}</el-descriptions-item>
          <el-descriptions-item label="状态">{{ stateValues[detailData.m_State] }}</el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ detailData.m_CreateTime }}</el-descriptions-item>
          <el-descriptions-item label="更新时间">{{ detailData.m_UpdateTime }}</el-descriptions-item>
          <el-descriptions-item label="描述">{{ detailData.m_Description }}</el-descriptions-item>
          <el-descriptions-item label="凭证">
            <el-row justify="center">
              <div v-if="detailData.m_Evidence !== null">
                <div v-for="(url, index) in detailData.m_Evidence" :key="url">
                  <el-image style="width: 100px; height: 100px; margin-left: 10px" :src="this.getImage(url)" fit="fill"
                            :preview-src-list="getAllImage(detailData.m_Evidence)"
                            :initial-index="index"></el-image>
                </div>
              </div>
            </el-row>
          </el-descriptions-item>
        </el-descriptions>
        <el-row justify="space-around" align="middle">
          <car-data-description :car-data="carOldData" v-if="carState"></car-data-description>
          <i :class="'iconfont icon-change-to-right'" v-if="carState"></i>
          <car-data-description :car-data="carNewData"></car-data-description>
        </el-row>
        <el-row v-if="id.startsWith('W') && detailData.m_State === '0'" justify="space-around">
          <el-button type="success" @click="doYes">通过</el-button>
          <el-button type="danger" @click="doNo">拒绝</el-button>
        </el-row>
      </el-dialog>
    </div>

    <div v-else style="display: flex; flex-direction: column">
      <el-text size="large">没有相关数据哟</el-text>
      <br>
      <el-text size="large" type="primary">
        祝您开心生活每一天
        <el-icon>
          <GobletSquareFull/>
        </el-icon>
      </el-text>
    </div>
</template>

<style scoped>
.class-doing {
  background-color: #66b1ff;
}
.class-access {
  background-color: #beefa0;
}
.class-pass {
  background-color: #ff7575;
}
</style>
