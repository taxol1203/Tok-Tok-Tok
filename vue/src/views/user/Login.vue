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
  setup() {
    const formLabelAlign = ref(null);
    const onSubmit = () => {
      let payload = {
        email: user.email,
        passwd: user.passwd,
      };
      formLabelAlign.value.validate((valid) => {
        if (valid) {
          axios
            .post('https://i5d204.p.ssafy.io/api/auth/login', payload)
            .then((res) => {
              alert('로그인이 완료되었습니다.');
              localStorage.setItem('jwt', res.data.token);
              router.go(0);
            })
            .catch(() => {
              console.log('login error');
            });
        }
      });
    };
    const checkEmail = (rule, value, callback) => {
      // console.log(value);
      if (!value) {
        return callback(new Error('Please input the email'));
      } else {
        let pattern =
          /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
        if (value.match(pattern) == null) {
          callback(new Error('Please input email'));
        } else {
          callback();
        }
      }
      setTimeout(() => {
        let pattern =
          /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
        if (pattern.test(value) == null) {
          callback(new Error('Please input email'));
        } else {
          callback();
        }
      }, 1000);
    };
    const validatePasswd = (rule, value, callback) => {
      // console.log(value);
      if (value === '') {
        callback(new Error('Please input the password'));
      } else {
        if (value.length < 9 || value.length > 16) {
          callback(new Error('Please check the password'));
        }
        callback();
      }
    };
    const logout = () => {
      localStorage.removeItem('jwt');
      router.go(0);
    };

    const user = reactive({
      email: '',
      passwd: '',
    });
    const rules = {
      passwd: [{ validator: validatePasswd, trigger: 'blur' }],
      email: [{ validator: checkEmail, trigger: 'blur' }],
    };
    const resetForm = () => {
      formLabelAlign.value.resetFields();
    };
    return {
      token: localStorage.getItem('jwt'),
      user,
      formLabelAlign,
      resetForm,
      rules,
      onSubmit,
      checkEmail,
      validatePasswd,
      logout,
    };
  },
};
</script>

<style></style>
