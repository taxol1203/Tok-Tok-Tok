<template>
  <el-container>
    <div class="myInfo" style="height: 87vh; position: relative">
      <div class="avatar-flip">
        <div class="imgBox">
          <img :src="imgUrl" class="profileImg" />
        </div>
        <h2 id="username" style="margin-bottom: 0">
          {{ user.username }}
        </h2>
        <div style="color: gray; margin-bottom: 10px">
          {{ user.email }}
        </div>
      </div>
      <hr style="border: 2px solid whitesmoke" width="90%" />
      <el-row class="row">
        <i class="el-icon-user" style="margin-right: 3px"></i>
        내 정보
      </el-row>
      <br />
      <el-row>
        <span class="myinfo">닉네임: {{ user.username }}</span>
      </el-row>
      <el-row>
        <span class="myinfo">이메일: {{ user.email }}</span>
      </el-row>
      <el-row>
        <span class="myinfo">전화번호: 010-1234-5678</span>
      </el-row>
      <hr style="border: 2px solid whitesmoke; margin-top: 2rem" width="90%" />
      <el-row class="row">
        <i class="el-icon-data-analysis" style="margin-right: 3px"></i>
        상담 정보
      </el-row>
      <br />
      <el-row>
        <span class="myinfo">진행중인 상담: {{ count[0] }}개</span>
      </el-row>
      <el-row>
        <span class="myinfo">대기중인 상담: {{ count[1] }}개</span>
      </el-row>
      <el-row>
        <span class="myinfo">종료된 상담: {{ count[2] }}개</span>
      </el-row>
      <div style="position: absolute; bottom: 10px; margin: auto; width: 100%; float: center">
        <el-button class="green-color-btn" @click="logout">로그아웃</el-button>
      </div>
    </div>
    <el-main>
      <div class="white-space">
        <img class="clear-img" src="@/assets/FixLogo.png" alt="logo" />
      </div>
    </el-main>
  </el-container>
</template>
<script>
import { useStore } from 'vuex';
import router from '@/router';
import { ref, computed, onMounted } from 'vue';

export default {
  setup() {
    const store = useStore();
    const user = store.state.auth.user;
    let temp = user.pk_idx % 11;
    const imgUrl = ref(require('@/assets/Profile/profile' + temp + '.jpg'));
    const count = computed(() => store.getters['countGetter']);
    const logout = () => {
      localStorage.clear();
      store.commit('auth/logout');
      router.push('/login');
    };
    onMounted(() => {
      store.dispatch('getChatRooms');
    });
    return {
      logout,
      user,
      router,
      imgUrl,
      count,
    };
  },
};
</script>
<style scoped>
.myinfo {
  margin-left: 2.5rem;
  margin-bottom: 0.2rem;
  width: 25rem;
  color: #808080;
  text-align: left;
}
i {
  margin-top: 3px;
}
.avatar-flip {
  display: inline-block;
  margin-top: 2rem;
}
.row {
  margin-left: 2rem;
  font-size: 18px;
  font-weight: bold;
}
.rowitem2 {
  color: gray;
  margin-left: 20px;
  margin-bottom: 10px;
}
.imgBox {
  width: 9rem;
  height: 9rem;
  border-radius: 70%;
  overflow: hidden;
}
.profileImg {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.clear-img {
  margin-top: 20%;
}
</style>
