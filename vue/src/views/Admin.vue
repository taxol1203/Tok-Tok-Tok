<template lang="">
    <el-container>
      <!-- NavBar 시작 -->
          <el-menu 
            :default-active="$route.name"
            class="el-menu-vertical-demo"
            background-color="#006f3e"
            text-color= "#999999"
            active-text-color="#fff" 
            :collapse="isCollapse"
          >
          <!-- ffd04b style="height: 750px; text-align:left"-->
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
      <!-- NavBar 끝 -->
    <el-main>
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
      <!-- <transition name="list-change" mode="out-in"> -->
        <router-view />
      <!-- </transition> -->
    </el-main>
    </el-container>
    
</template>
<script>
import {useStore} from 'vuex'
import router from '@/router'
export default {
  setup(){
    const store = useStore();
    const activeIndex = '0'
    store.dispatch('loginCheck')
    const logout = () => {
      localStorage.clear();
      store.commit("auth/logout");
      router.push("/login");
    };
    return{
      activeIndex,
      logout
    }
  },
  data(){
    return{
      isCollapse : false,
      mode : '1',
    }
  },
  methods:{
    handleNav(){
      this.isCollapse = !this.isCollapse
    },
    moveToUser(){
      router.push("/admin/user")
    },
    moveToMain(){
      router.push("/admin/chat")
    },
    handleSelect(key, keyPath){
      console.log(key, keyPath)
      // localStorage.clear();
      // store.commit("auth/logout");
      // router.push("/login");
    }
  }
};
</script>
<style>
li.el-menu-item{
  font-size: 16px;
}
/* 생성 부분 */
/* .list-change-enter-from {
  opacity: 0;
}
.list-change-enter-to {
  opacity: 1;
}
.list-change-enter-active {
  transition: all 0.5s ease-out;
}
.list-change-leave-from {
  opacity: 1;
}
.list-change-leave-to {
  opacity: 0;
}
.list-change-leave-active {
  transition: all 0.7s ease-out;
} */
</style>
