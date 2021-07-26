<template>
  <el-row justify="center">
    <el-col :span="12">
      <div>
        <el-card shadow="always">
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
              <el-input type="email" v-model="user.email" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="비밀번호" prop="passwd">
              <el-input type="password" v-model="user.passwd" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
              <transition name="slide-fade">
                <el-button type="button" id="primary" @click="onSubmit('formLabelAlign')"
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
import { mapActions } from 'vuex';
export default {
  data() {
    var checkEmail = (rule, value, callback) => {
      console.log(value);
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
    var validatePasswd = (rule, value, callback) => {
      console.log(value);

      if (value === '') {
        callback(new Error('Please input the password'));
      } else {
        if (value.length < 9 || value.length > 16) {
          callback(new Error('Please check the password'));
        }
        callback();
      }
    };
    return {
      token: localStorage.getItem('jwt'),
      msg: '',
      user: {
        email: '',
        passwd: '',
      },
      rules: {
        passwd: [{ validator: validatePasswd, trigger: 'blur' }],
        email: [{ validator: checkEmail, trigger: 'blur' }],
      },
    };
  },
  methods: {
    ...mapActions(['login']),
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.login(this.user);
          this.$router.go();
        }
      });
    },
    logout() {
      localStorage.removeItem('jwt');
      this.$router.go();
    },
  },
};
</script>

<style></style>
