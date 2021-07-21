<template>
  <el-row justify="center">
    <el-col :span="6">
      <div>
        <el-card shadow="always">
          <el-form
            label-position="top"
            label-width="100px"
            :model="ruleForm"
            :rules="rules"
            ref="formLabelAlign"
            status-icon
            @submit.prevent="onSubmit('formLabelAlign', ruleForm.email, ruleForm.password)"
          >
            <el-form-item label="이메일" prop="email">
              <el-input type="email" v-model="ruleForm.email" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="비밀번호" prop="pass">
              <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
              <!-- @click="submitForm('formLabelAlign')" -->
              <transition name="slide-fade">
                <el-button type="submit" id="primary">로그인</el-button>
              </transition>
              <el-button @click="resetForm('formLabelAlign')">다시쓰기</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </div>
    </el-col>
  </el-row>
</template>

<script>
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
      if (value === '') {
        callback(new Error('Please input the password'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    return {
      msg: '',
      ruleForm: {
        pass: '',
        email: '',
      },
      rules: {
        pass: [{ validator: validatePass, trigger: 'blur' }],
        email: [{ validator: checkEmail, trigger: 'blur' }],
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('submit!');
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    onSubmit(formName, email, password) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$store
            .dispatch('LOGIN', { email, password })
            .then(() => this.redirect())
            .catch(({ message }) => (this.msg = message));
        }
      });
    },
    redirect() {
      const { search } = window.location;
      const tokens = search.replace(/^\?/, '').split('&');
      const { returnPath } = tokens.reduce((qs, tkn) => {
        const pair = tkn.split('=');
        qs[pair[0]] = decodeURIComponent(pair[1]);
        return qs;
      }, {});
      this.$router.push(returnPath);
    },
  },
};
</script>

<style></style>
