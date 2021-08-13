<template>
  <div class="container" id="LoginMain">
    <div id="LeftPosition">
      <el-row justify="" v-loading="loading">
        <el-col :span="24">
          <el-card shadow="always">
            <div style="position: static">
              <h1>
                <img src="@/assets/MainLogo.png" alt="MainLogo" id="MiniLogo" />
              </h1>
            </div>
            <el-form
              v-if="Object.keys(token).length === 0"
              label-position="top"
              label-width="100px"
              :model="user"
              :rules="rules"
              ref="formLabelAlign"
              status-icon
            >
              <el-form-item label="이메일" prop="email">
                <el-input
                  type="email"
                  v-model="user.email"
                  autocomplete="off"
                  placeholder="이메일을 입력해 주세요"
                  @keyup.enter="nextPasswd"
                ></el-input>
              </el-form-item>
              <el-form-item label="비밀번호" prop="passwd">
                <el-input
                  type="password"
                  v-model="user.passwd"
                  autocomplete="off"
                  placeholder="비밀번호를 입력해 주세요"
                  @keyup.enter="onSubmit('formLabelAlign')"
                  ref="refPasswd"
                ></el-input>
              </el-form-item>
              <el-form-item>
                <!-- <transition name="slide-fade"> -->
                <el-button
                  type="button"
                  class="green-color-btn"
                  @click="load()"
                  :disabled="!isValid"
                  >로그인</el-button
                >
                <!-- </transition> -->
                <el-button @click="resetForm('formLabelAlign')">다시쓰기</el-button>
                <el-button class="green-color-btn" @click="goSignUp()">회원가입</el-button>
              </el-form-item>
            </el-form>
            <div v-else>
              <h2>환영합니다.</h2>
              <el-button @click="logout">로그아웃</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="24" :offset="0" id="footer">
          <strong>Help Desk </strong>
          <em>
            <a href="tel:02-3429-5041">02-3429-5041</a>
          </em>
          <br />
          <span>평일 상담시간: 09:00 ~ 19:00</span>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import { reactive, ref, computed } from "vue";
import { useStore } from "vuex";
import router from "@/router";

export default {
  setup() {
    const store = useStore();
    const token = computed(() => store.state.auth.user);
    const formLabelAlign = ref(null);
    const loading = ref(false);
    const onSubmit = () => {
      let payload = {
        email: user.email,
        passwd: user.passwd,
      };
      formLabelAlign.value.validate((valid) => {
        if (valid) {
          store.dispatch("auth/login", payload);
          router.push("/userchat");
        }
      });
    };
    const refPasswd = ref("");
    const isValid = computed(() => user.email && user.passwd);

    const load = () => {
      loading.value = true;
      onSubmit();
      setTimeout(loadFinsh, 1000);
    };

    const loadFinsh = () => {
      loading.value = false;
    };

    const checkEmail = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("이메일을 입력해 주세요"));
      } else {
        let pattern =
          /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
        if (value.match(pattern) == null) {
          callback(new Error("이메일 형식을 맞춰 주세요"));
        } else {
          callback();
        }
      }
    };
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("비밀번호를 입력해 주세요"));
      } else {
        callback();
      }
    };
    const logout = () => {
      localStorage.clear();
      store.commit("auth/logout");
    };

    const user = reactive({
      email: "",
      passwd: "",
    });
    const rules = {
      email: [{ validator: checkEmail, trigger: "blur" }],
      passwd: [{ validator: validatePass, trigger: "blur" }],
    };
    const resetForm = () => {
      formLabelAlign.value.resetFields();
    };
    const nextPasswd = () => {
      refPasswd.value.focus();
    };

    const goSignUp = () => {
      router.push("/usersignup");
    };
    return {
      store,
      user,
      token,
      formLabelAlign,
      resetForm,
      rules,
      onSubmit,
      checkEmail,
      validatePass,
      logout,
      refPasswd,
      nextPasswd,
      isValid,
      goSignUp,
      loading,
      load,
      loadFinsh,
    };
  },
};
</script>

<style scoped>
#LoginMain {
  height: 98%;
  background-image: url("../../assets/work3.png");
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center;
}

#LoginMain::before {
  content: "";
  opacity: 0.5;
  position: absolute;
  top: 0px;
  left: 0px;
  right: 0px;
  bottom: 0px;
  background-color: #ffffff;
}

#LeftPosition {
  position: absolute;
  top: 30%;
  left: 10%;
}

#MiniLogo {
  width: 30%;
  height: 30%;
  display: block;
  position: static;
}

el-form {
  display: block;
}
</style>
