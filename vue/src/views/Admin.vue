<template lang="">
    <el-container>
          <el-menu 
            :default-active="$route.name"
            class="el-menu-vertical-demo"
            background-color="transparent"
            text-color= "#999999"
            active-text-color="#fff" 
            :collapse="isCollapse"
          >
            <el-menu-item index="comein" @click="handleNav">
              <i class="el-icon-d-arrow-right" v-if="isCollapse===true"></i>
              <i class="el-icon-d-arrow-left"  v-if="isCollapse===false"></i>
            </el-menu-item>
            <router-link to="/admin/chat" style="text-decoration:none">
            <el-menu-item index="chat" >
              <i class="el-icon-menu"></i>
              <template #title>상담</template>
            </el-menu-item>
            </router-link>
            <router-link  to="/admin/qna" style="text-decoration:none">
            <el-menu-item index="qna">
              <i class="el-icon-document"></i>
              <template #title>예상 시나리오 작성</template>
            </el-menu-item></router-link>
            <router-link  to="/admin/user" style="text-decoration:none">
            <el-menu-item index="user">
              <i class="el-icon-setting"></i>
              <template #title>설정</template>
            </el-menu-item>
            </router-link>
          </el-menu>
    <el-main style="height:100vh">
      <el-menu
        :default-active="activeIndex"
        class="el-menu-demo navbar"
        mode="horizontal"
        background-color="#fff"
        text-color="#000"
        active-text-color="#006f3e">
        <el-menu-item index="0" @click="moveToMain">언제든지 어디서나 간편하게</el-menu-item>
        <el-menu-item index="2" style="float: right" @click="logout">로그아웃</el-menu-item>
        <el-menu-item index="1" style="float: right" @click="moveToUser">내 계정</el-menu-item>
      </el-menu>
        <router-view />
    </el-main>
    </el-container>
    
</template>
<script>
import { useStore } from 'vuex';
import router from '@/router';
export default {
  setup() {
    const store = useStore();
    const activeIndex = '0';
    store.dispatch('loginCheck');
    const logout = () => {
      localStorage.clear();
      store.commit("auth/logout");
      router.push("/login");
    };
    return {
      activeIndex,
      logout
    };
  },
  data() {
    return {
      isCollapse: false,
      mode: '1',
    };
  },
  methods: {
    handleNav() {
      this.isCollapse = !this.isCollapse;
    },
    moveToUser() {
      router.push("/admin/user");
    },
    moveToMain() {
      router.push("/admin/chat");
    },
  }
};
</script>
<style scoped>
li.el-menu-item {
  font-size: 16px;
}
.el-menu-item:hover {
  filter: brightness(105%) !important;
}
</style>
