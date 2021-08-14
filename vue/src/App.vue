<template>
  <div class="inner">
    <!-- transition 문제 이거 지우면 높은 확률로 해결됨 -->
    <!-- <transition name="page-change" mode="out-in"> -->
    <router-view />
    <!-- </transition> -->
  </div>
</template>
<script>
export default {
  name: "App",
};
</script>

<style>
/* 전체 프로젝트 폰트 변경(상속, cascading) */
* {
  font-family: "Noto Sans", "맑은 고딕", sans-serif;
  font-weight: bold;
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
  width: 95vw;
  height: 95vh;
  margin: 0;
  padding: 0;
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
