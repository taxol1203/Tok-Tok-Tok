<template>
  <el-row>
    <el-col>
      <div class="avatar-flip">
        <div class="imgBox">
          <img :src="imgUrl" class="profileImg" />
        </div>
        <h3 style="margin: 10px">
          {{ user.username }}
        </h3>
        <div style="color: gray; margin-bottom: 10px">
          {{ user.email }}
        </div>
      </div>
      <hr style="border: 2px solid whitesmoke" width="90%" />

      <el-row class="row">
        <i class="el-icon-info" style="margin-right: 3px"></i>
        고객 정보
      </el-row>
    </el-col>

    <el-row :gutter="20">
      <el-col :span="24" :offset="0">username: {{ user.username }}</el-col>
      <el-col :span="24" :offset="0">email: {{ user.email }}</el-col>
      <el-col :span="24" :offset="0">phone: 010-1234-5678</el-col>
    </el-row>
    <hr style="border: 2px solid whitesmoke; margin-top: 40px" width="90%" />
  </el-row>
  <el-button @click="logout">로그아웃</el-button>
</template>
<script>
import { useStore } from "vuex";
import router from "@/router";
import { ref } from "vue";

export default {
  setup() {
    const store = useStore();
    const user = store.state.auth.user;
    let max = 10;
    let min = 0;
    let temp = Math.floor(Math.random() * (max - min)) + min;
    const imgUrl = ref(require("@/assets/Profile/profile" + temp + ".jpg"));
    const logout = () => {
      localStorage.clear();
      store.commit("auth/logout");
      router.push("/login");
    };
    return {
      logout,
      user,
      router,
      imgUrl,
    };
  },
};
</script>
<style scoped>
.rowitem2 {
  color: gray;
  margin-left: 20px;
  margin-bottom: 10px;
}
.imgBox {
  width: 150px;
  height: 150px;
  border-radius: 70%;
  overflow: hidden;
}
.profileImg {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>
