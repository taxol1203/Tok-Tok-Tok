<template>
  <div id="nav">
    <router-link to="/signup">Sign Up</router-link> |
    <router-link to="/login">Log in</router-link> | <router-link to="/admin">Admin</router-link> |
    <router-link to="/dummy">Dummy Page</router-link>
  </div>
  <div class="inner">
    <transition name="page-change" mode="out-in">
      <router-view />
    </transition>
  </div>
</template>
<script>
export default {
  name: 'App',
};
</script>

<style>
/* 전체 프로젝트 폰트 변경(상속, cascading) */
* {
  font-family: BMJUA;
  text-decoration: none;
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
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #006f3e;
}
/* 258c60  */
.colorVer {
  background-color: #006f3e;
  color: #fff;
  border: 0;
}
/* .inner {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
} */
@font-face {
  font-family: 'BMJUA';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/BMJUA.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}

/* 생성 부분 */
.page-change-enter-from {
  opacity: 0;
}
.page-change-enter-to {
  opacity: 1;
}
.page-change-enter-active {
  transition: all 0.5s ease-out;
}
/* 소멸 부분 */
.page-change-leave-from {
  opacity: 1;
}
.page-change-leave-to {
  opacity: 0;
}
.page-change-leave-active {
  transition: all 1s ease-out;
}
</style>
