<template>
  <!-- <div class="inner"> -->
  <!-- transition 문제 이거 지우면 높은 확률로 해결됨 -->
  <!-- <transition name="page-change" mode="out-in"> -->
  <router-view />
  <!-- </transition> -->
  <!-- </div> -->
</template>
<script>
export default {
  name: 'App',
};
</script>

<style>
/* 전체 프로젝트 폰트 변경(상속, cascading) */
* {
  font-family: 'Noto Sans', '맑은 고딕', sans-serif;
  font-weight: bold;
  /* font-size: 17px; */
}

html,
body {
  margin: 0;
  padding: 0;
  height: 100%;
}
.el-main {
  overflow: hidden;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  width: 100vw;
  height: 100vh;
  margin: 0px;
  padding: 0px;
}

.inner {
  height: 94%;
}

/* 258c60  */
.green-color-btn {
  background-color: #006f3e;
  color: #fff;
  border: 0;
}
.el-menu-vertical-demo {
  position: relative;
  height: 100vh;
  max-height: 100vh;
  min-height: 100%;
  overflow: auto;
  /* width: 260px; */
  min-width: 260px;
  text-align: left;
  background-image: url('./assets/green-background.jpg');
}
.navbar {
  border: 0;
  font-size: 16px;
  border-radius: 0;
  min-height: 50px;
  background-color: hsla(0, 0%, 100%, 0.96);
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  padding: 5px 15px;
}
/* .el-scrollbar {
  height: 87vh;
} */
/* 생성 부분 */
/* .page-change-enter-from {
  opacity: 0;
}
.page-change-enter-to {
  opacity: 1;
}
.page-change-enter-active {
  transition: all 0.5s ease-out;
}
.page-change-leave-from {
  opacity: 1;
}
.page-change-leave-to {
  opacity: 0;
}
.page-change-leave-active {
  transition: all 1s ease-out;
} */
</style>
