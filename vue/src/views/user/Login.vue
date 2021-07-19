<template>
  <div class="login">
    <!-- <form action="secret" @submit="onSubmit"> -->
      <h1>로그인페이지</h1>
      email: <input type="text" v-model="uid" /><br />
      password: <input type="password" v-model="password" /><br />
      <button type="submit" @click="onSubmit">Sign Up</button>
      
    <!-- </form> -->
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Login",
  data: () => ({
    uid: "",
    password: "",
    
    a : 1,
  }),
  methods: {
    onSubmit() {
      console.log(this.uid);
      console.log(this.password);
      axios
        .post("http://119.56.162.178:8088/project1/api/auth/login", {
          //http://119.56.162.178:8089/project1/swagger-ui/index.html
          email: this.uid,
          passwd: this.password,
        })
        .then((response) => {
          console.log(response.data);
          console.log(response);
          if(response.status == '200'){
            this.$router.push('/secret');
          }
          localStorage.setItem('auth-token', response.data.token);
        });
    },
  },
};
</script>

<style></style>
