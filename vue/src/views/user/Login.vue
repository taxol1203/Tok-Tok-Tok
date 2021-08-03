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
import { reactive, ref } from "vue";
import axios from "axios";
import router from "@/router";

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
            .post("https://i5d204.p.ssafy.io/api/auth/login", payload)
            .then((res) => {
              alert("로그인이 완료되었습니다.");
              localStorage.setItem("jwt", res.data.token);
              router.go(0);
            })
            .catch(() => {
              console.log("login error");
            });
        }
      });
    };
    const checkEmail = (rule, value, callback) => {
      // console.log(value);
      if (!value) {
        return callback(new Error("Please input the email"));
      } else {
        let pattern =
          /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
        if (value.match(pattern) == null) {
          callback(new Error("Please input email"));
        } else {
          callback();
        }
      }
      setTimeout(() => {
        let pattern =
          /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
        if (pattern.test(value) == null) {
          callback(new Error("Please input email"));
        } else {
          callback();
        }
      }, 1000);
    };
    const validatePasswd = (rule, value, callback) => {
      // console.log(value);
      if (value === "") {
        callback(new Error("Please input the password"));
      } else {
        if (value.length < 9 || value.length > 16) {
          callback(new Error("Please check the password"));
        }
        callback();
      }
    };
    const logout = () => {
      localStorage.removeItem("jwt");
      router.go(0);
    };

    const user = reactive({
      email: "",
      passwd: "",
    });
    const rules = {
      passwd: [{ validator: validatePasswd, trigger: "blur" }],
      email: [{ validator: checkEmail, trigger: "blur" }],
    };
    const resetForm = () => {
      formLabelAlign.value.resetFields();
    };
    return {
      token: localStorage.getItem("jwt"),
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
