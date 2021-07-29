<template>
  <div id="nav">
    <router-link to="/signup">Sign Up</router-link> |
    <router-link to="/login">Log in</router-link> | <router-link to="/admin">Admin</router-link> |
    <router-link to="/dummy">Dummy Page</router-link>
  </div>
  <div id="top-padding">
    <transition name="page-change" mode="out-in">
      <router-view />
    </transition>
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
  font-family: BMJUA;
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
  color: #42b983;
}

#colorVer {
  background-color: #258c60;
  color: #fff;
  border: 0;
}

@font-face {
  font-family: "BMJUA";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/BMJUA.woff") format("woff");
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
  transition: all 1s ease-out;
}
/* 소멸 부분 */
.page-change-leave-from {
  opacity: 1;
}
.page-change-leave-to {
  opacity: 0;
}
.page-change-leave-active {
  transition: all 4s ease-out;
}

#top-padding {
  padding-top: 5%;
}
</style>
