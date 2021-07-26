<template>
  <el-row justify="center">
    <el-col :span="12">
      <div>
        <!-- status-icon -->
        <el-card shadow="always">
          <el-form
            :model="user"
            :rules="rules"
            label-position="top"
            ref="ruleForm"
            class="demo-ruleForm"
          >
            <el-form-item label="사용자명" prop="username">
              <el-input type="text" v-model="user.username" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="이메일" prop="email">
              <el-input type="email" v-model="user.email" autocomplete="off">
                <template #append>
                  <el-button id="primary" @click="duplicate">중복확인</el-button>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item label="비밀번호" prop="passwd">
              <el-input type="password" v-model="user.passwd" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="비밀번호 확인" prop="check">
              <el-input type="password" v-model="user.check" autocomplete="off"></el-input>
            </el-form-item>
            <el-button id="primary" @click="onSubmit('ruleForm')">회원가입</el-button>
            <el-button @click="resetForm('ruleForm')">다시쓰기</el-button>
          </el-form>
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
    var validatePass = (rule, value, callback) => {
      console.log(value);
      if (value === '') {
        callback(new Error('Please input the password'));
      } else {
        if (9 > value.length || 16 < value.length) {
          callback(new Error('Please check the pw rule'));
        } else {
          callback();
        }
      }
    };
    var validateCheck = (rule, value, callback) => {
      console.log('check:', value);
      if (value === '') {
        callback(new Error('Please input the password again'));
      } else if (value !== this.user.passwd) {
        callback(new Error("Two inputs don't match!"));
      } else {
        callback();
      }
    };
    var validateName = (rule, value, callback) => {
      console.log('nickname:', value);
      if (value === '') {
        callback(new Error('Please input the username again'));
      } else {
        callback();
      }
    };
    return {
      user: {
        email: '',
        passwd: '',
        check: '',
        username: '',
      },
      rules: {
        username: [{ validator: validateName, trigger: 'blur' }],
        passwd: [{ validator: validatePass, trigger: 'blur' }],
        check: [{ validator: validateCheck, trigger: 'blur' }],
        email: [{ validator: checkEmail, trigger: 'blur' }],
      },
    };
  },
  methods: {
    ...mapActions(['signUp', 'duplicateEmail']),
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.signUp({
            email: this.user.email,
            passwd: this.user.passwd,
            username: this.user.username,
          });
          //로그인 화면으로 전환
          this.$router.push({ name: 'Login' }).catch(() => {});
        }
      });
    },
    duplicate() {
      let tmp = {
        email: this.user.email,
      };
      this.duplicateEmail(tmp);
    },
  },
};
</script>

<style></style>
