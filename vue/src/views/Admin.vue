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
        :default-active="activeIndex2"
        class="el-menu-demo navbar"
        mode="horizontal"
        @select="handleSelect"
        background-color="#fff"
        text-color="#000"
        active-text-color="#006f3e">
        <el-menu-item index="0" disabled>톡톡톡</el-menu-item>
        <el-menu-item index="1" style="float: right">내 계정</el-menu-item>
        <el-menu-item index="2" style="float: right">로그아웃</el-menu-item>
      </el-menu>
      <!-- <transition name="list-change" mode="out-in"> -->
        <router-view />
      <!-- </transition> -->
    </el-main>
    </el-container>
    
</template>
<script>
import {useStore} from 'vuex'
export default {
  setup(){
    const store = useStore();
    store.dispatch('loginCheck')
  },
  data(){
    return{
      isCollapse : false,
      mode : '1'
    }
  },
  methods:{
    handleNav(){
      this.isCollapse = !this.isCollapse
    }
  }
};
</script>
<style>

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
