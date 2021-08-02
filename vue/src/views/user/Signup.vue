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
  setup() {
    const store = useStore();
    const ruleForm = ref(null);
    const router = useRouter();

    const onSubmit = () => {
      let payload = {
        email: user.email,
        passwd: user.passwd,
        username: user.username,
      };
      ruleForm.value.validate((valid) => {
        if (valid) {
          axios
            .post('https://i5d204.p.ssafy.io/api/auth/register', payload)
            .then(() => {
              alert('회원가입이 완료되었습니다.');
              router.push({ name: 'Login' });
            })
            .catch(() => {
              console.log('signup error');
            });
        }
      });
    };

    const duplicate = () => {
      let tmp = {
        email: user.email,
      };
      axios
        .post('https://i5d204.p.ssafy.io/api/auth/checkemail', tmp)
        .then(() => {
          alert('사용 중인 이메일입니다.');
          user.email = '';
        })
        .then((response) => {
          if (response.status == "200") {
            this.$router.push("/login");
          }
          //commit("UPDATE_BOARD_LIST", data);
        });
    },
  },
};
</script>

<style></style>
