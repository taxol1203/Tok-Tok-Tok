<template>
  <div class="secret">
    HELLO! I'm SECRET!
    <!-- <div v-model="ss">{{this.ss}}</div> -->
    {{ss}}
    <input v-model="ss">
  </div>
</template>

<script>
import axios from "axios";

export default {
  // new Vue({})
  name: "Secret",
  data() {
    return {
      ss: [],
    }
  },
  mounted() {
    console.log(localStorage.getItem('auth-token'));
    axios
        .post(
          "http://119.56.162.178:8088/project1/api/jwt/secret", {},
          {
            headers: {"auth-token" : localStorage.getItem('auth-token')}
          },
          //http://119.56.162.178:8089/project1/swagger-ui/index.html
        )
        .then((response) => {
          this.ss = response.data;
        });
  },
};
</script>

<style></style>
