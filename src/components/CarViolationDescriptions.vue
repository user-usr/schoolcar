<script>

import {mixin} from "@/mixins";
import {
  Aim,
  Bicycle,
  CameraFilled,
  MapLocation,
  Memo,
  Timer,
  UserFilled,
  WarnTriangleFilled
} from "@element-plus/icons-vue";

export default {
  name: 'CarViolationDescriptions',
  components: {CameraFilled, Memo, Aim, WarnTriangleFilled, MapLocation, Timer, UserFilled, Bicycle},
  mixins: [mixin],
  props: [
      'theData'
  ]
}
</script>

<template>
  <el-descriptions
      :title="theData.c_PlateNumber + ' 的违规详情'"
      size="large"
      border
  >
    <el-descriptions-item>
      <template #label>
        <div class="descriptions-label">
          <el-icon>
            <Bicycle/>
          </el-icon>
          <el-text>车牌号</el-text>
        </div>
      </template>
      <template #default>
        <el-text>{{ theData.c_PlateNumber }}</el-text>
      </template>
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="descriptions-label">
          <el-icon>
            <UserFilled/>
          </el-icon>
          <el-text>办理人员</el-text>
        </div>
      </template>
      <template #default>
        <el-text>{{ theData.w_RealName }}</el-text>
      </template>
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="descriptions-label">
          <el-icon>
            <Timer/>
          </el-icon>
          <el-text>时间</el-text>
        </div>
      </template>
      <template #default>
        <el-text>{{ theData.v_Time }}</el-text>
      </template>
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="descriptions-label">
          <el-icon>
            <MapLocation/>
          </el-icon>
          <el-text>地点</el-text>
        </div>
      </template>
      <template #default>
        <el-text>{{ theData.v_Where }}</el-text>
      </template>
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="descriptions-label">
          <el-icon>
            <WarnTriangleFilled/>
          </el-icon>
          <el-text>类型</el-text>
        </div>
      </template>
      <template #default>
        <el-text>{{ theData.v_Type }}</el-text>
      </template>
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="descriptions-label">
          <el-icon>
            <Aim/>
          </el-icon>
          <el-text>处罚</el-text>
        </div>
      </template>
      <template #default>
        <el-text>{{ theData.v_Punish }}</el-text>
      </template>
    </el-descriptions-item>
    <el-descriptions-item span="3">
      <template #label>
        <div class="descriptions-label">
          <el-icon>
            <Memo/>
          </el-icon>
          <el-text>描述</el-text>
        </div>
      </template>
      <template #default>
        <el-text>{{ theData.v_Description }}</el-text>
      </template>
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="descriptions-label">
          <el-icon>
            <CameraFilled/>
          </el-icon>
          <el-text>凭证</el-text>
        </div>
      </template>
      <template #default>
        <el-row justify="center">
          <div v-for="(url, index) in theData.v_Evidence" :key="url">
            <el-image style="width: 100px; height: 100px; margin-left: 10px" :src="getImage(url)" fit="fill"
                      :preview-src-list="getAllImage(theData.v_Evidence)"
                      :initial-index="index"></el-image>
          </div>
        </el-row>
      </template>
    </el-descriptions-item>
  </el-descriptions>
</template>

<style scoped>
.descriptions-label {
  display: flex;
  align-items: center;
}
</style>
