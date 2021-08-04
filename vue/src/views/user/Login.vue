<template>
  <el-row justify="center">
    <el-col :span="12">
      <div>
        <el-card shadow="always">
          <p>user11@naver.com</p>
          <p>asdf555!@#</p>
          <p>minsu2@naver.com</p>
          <p>minsu2navercom!</p>
          <el-form
            v-if="token === null"
            label-position="top"
            label-width="100px"
            :model="user"
            :rules="rules"
            ref="formLabelAlign"
            status-icon
          >
            <el-form-item label="이메일" prop="email">
              <el-input type="email" v-model="user.email" autocomplete="off"
                >user11@naver.com</el-input
              >
            </el-form-item>
            <el-form-item label="비밀번호" prop="passwd">
              <el-input type="password" v-model="user.passwd" autocomplete="off"
                >asdf555!@#</el-input
              >
            </el-form-item>
            <el-form-item>
              <transition name="slide-fade">
                <el-button type="button" class="green-color-btn" @click="onSubmit('formLabelAlign')"
                  >로그인</el-button
                >
              </transition>
              <el-button @click="resetForm('formLabelAlign')">다시쓰기</el-button>
            </el-form-item>
          </el-form>
          <div v-else>
            <h2>환영합니다.</h2>
            <el-button @click="logout">로그아웃</el-button>
          </div>
        </el-card>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import { reactive, ref } from 'vue';
import { useStore } from 'vuex';
import router from '@/router';

export default {
  setup() {
    const store = useStore();
    const formLabelAlign = ref(null);
    const onSubmit = () => {
      let payload = {
        email: user.email,
        passwd: user.passwd,
      };
      formLabelAlign.value.validate((valid) => {
        if (valid) {
          store.dispatch('auth/login', payload);
          // router.go(0);
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
      store,
      token: localStorage.getItem('vuex').token,
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
