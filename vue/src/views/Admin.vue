<template lang="">
    <el-container>
      <!-- NavBar 시작 -->
          <el-menu 
            :default-active="$route.name"
            class="el-menu-vertical-demo"
            background-color="#006f3e"
            text-color= "#999999"
            active-text-color="#fff" 
            style="height: 750px; text-align:left"
            :collapse="isCollapse"
          >
          <!-- ffd04b -->
            <el-menu-item index="comein" @click="handleNav()">
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
      <!-- <transition name="list-change" mode="out-in"> -->
        <router-view />
      <!-- </transition> -->
    </el-main>
    </el-container>
    
</template>
<script>
export default {
  data() {
    return {
      isCollapse: true,
      mode:'1',
    };
  },
  methods:{
    handleNav(){
      this.isCollapse = !this.isCollapse;
    }
  }
};
</script>
<style scoped>
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
