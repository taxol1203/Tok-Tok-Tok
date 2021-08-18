<template>
  <router-view />
</template>
<script>
export default {
  name: 'App',
};
</script>

<style>
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
  width: 100vw;
  height: 100vh;
  margin: 0px;
  padding: 0px;
}

.inner {
  height: 94%;
}

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
  /* min-width: 260px; */
  text-align: left;
  background-image: url("./assets/green-background.jpg");
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
</style>
