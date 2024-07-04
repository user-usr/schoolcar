<script>
import InsertUserForm from "@/components/InsertUserForm.vue";

export default {
  name: 'LogonPage',
  components: {InsertUserForm},
}
</script>

<template>
  <div class="logon">
    <div class="tip">
      <p style="font-weight: 700">提醒</p>
      <p style="font-size: .9rem; margin-top: 10px">仅支持普通用户注册,工作人员请联系管理员注册</p>
    </div>
    <insert-user-form/>
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

.logon {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;

  background-image: linear-gradient(-20deg, #dcb0ed 0%, #99c99c 100%);
}

</style>
