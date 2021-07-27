<template>
  <div id="nav">
    <router-link to="/signup">Sign Up</router-link> |
    <router-link to="/login">Log in</router-link> |
    <router-link to="/admin">Admin</router-link>
  </div>
  <router-view />
</template>
<script>
export default {
  name: 'App',
};
</script>

<style>
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
  font-family: 'BMJUA';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/BMJUA.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}
</style>
