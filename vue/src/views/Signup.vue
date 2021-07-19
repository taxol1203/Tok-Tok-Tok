<template>
  <div class="signup">
    <!-- <form action="login" @submit="onSubmit"> -->
      <h1>회원가입</h1>
      email: <input type="text" v-model="uid" /><br />
      password: <input type="password" v-model="password" /><br />
      <button type="submit" @click="onSubmit">Sign Up</button>
    <!-- </form> -->
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Signup",
  data: () => ({
    uid: "",
    password: "",
  }),
  methods: {
    onSubmit() {
      console.log(this.uid);
      console.log(this.password);
      axios
        .post("http://119.56.162.178:8088/project1/api/auth/register", {
          //http://119.56.162.178:8089/project1/swagger-ui/index.html
          email: this.uid,
          passwd: this.password,
        })
        .then((response) => {
          if(response.status == "200"){
            this.$router.push('/login');
          }
          //commit("UPDATE_BOARD_LIST", data);
        });
    },
  },
};
</script>

<style></style>
