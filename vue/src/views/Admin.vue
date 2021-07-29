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
            <router-link to="/admin/chat">
            <el-menu-item index="chat">
              <i class="el-icon-menu"></i>
              <template #title>상담</template>
            </el-menu-item></router-link>
            <router-link  to="/admin/qna">
            <el-menu-item index="qna">
              <i class="el-icon-document"></i>
              <template #title>예상 시나리오 작성</template>
            </el-menu-item></router-link>
            <el-menu-item index="3">
              <i class="el-icon-setting"></i>
              <template #title>설정</template>
            </el-menu-item>
          </el-menu>
      <!-- NavBar 끝 -->
    <el-main>
      <router-view></router-view>
    </el-main>
    </el-container>
    
</template>
<script>
export default {
  data() {
    return {
      isCollapse: false,
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

</style>