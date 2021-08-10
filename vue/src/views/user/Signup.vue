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
                  <el-button id="green-color-btn" @click="duplicate">중복확인</el-button>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item label="비밀번호" prop="passwd">
              <el-input type="password" v-model="user.passwd" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="비밀번호 확인" prop="check">
              <el-input type="password" v-model="user.check" autocomplete="off"></el-input>
            </el-form-item>
            <el-button class="green-color-btn" @click="onSubmit()">회원가입</el-button>
            <el-button @click="resetForm()">다시쓰기</el-button>
          </el-form>
        </el-card>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import { reactive, ref } from 'vue';
import { useStore } from 'vuex';

export default {
  setup() {
    const store = useStore();
    const ruleForm = ref(null);
    const onSubmit = () => {
      let payload = {
        email: user.email,
        passwd: user.passwd,
        username: user.username,
      };
      ruleForm.value.validate((valid) => {
        if (valid) {
          store.dispatch('auth/signup', payload);
        }
      });
    };

    const duplicate = () => {
      let tmp = {
        email: user.email,
      };
      store.dispatch('auth/duplicateEmail', tmp);
    };

    const resetForm = () => {
      ruleForm.value.resetFields();
    };

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
        if (9 > value.length || 16 < value.length) {
          callback(new Error('Please check the pw rule'));
        } else {
          callback();
        }
      }
    };
    var validateCheck = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('Please input the password again'));
      } else if (value !== user.passwd) {
        callback(new Error("Two inputs don't match!"));
      } else {
        callback();
      }
    };
    var validateName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('Please input the username again'));
      } else {
        callback();
      }
    };
    const user = reactive({
      email: '',
      passwd: '',
      check: '',
      username: '',
    });
    const rules = {
      username: [{ validator: validateName, trigger: 'blur' }],
      passwd: [{ validator: validatePass, trigger: 'blur' }],
      check: [{ validator: validateCheck, trigger: 'blur' }],
      email: [{ validator: checkEmail, trigger: 'blur' }],
    };
    return {
      store,
      ruleForm,
      onSubmit,
      duplicate,
      resetForm,
      user,
      rules,
    };
  },
};
</script>

<style></style>
