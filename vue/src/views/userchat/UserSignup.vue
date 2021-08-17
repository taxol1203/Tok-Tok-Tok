<template>
  <div class="container" id="SignupMain">
    <div id="LeftPosition">
      <el-row justify="center" v-loading="loading">
        <el-col :span="24">
          <el-card shadow="always">
            <div style="position: static">
              <h1>
                <img src="@/assets/MainLogo.png" alt="MainLogo" id="MiniLogo" />
              </h1>
            </div>
            <el-form
              :model="user"
              :rules="rules"
              label-position="top"
              ref="ruleForm"
              class="demo-ruleForm"
            >
              <el-form-item label="닉네임" prop="username">
                <el-input
                  type="text"
                  v-model="user.username"
                  autocomplete="off"
                  placeholder="닉네임을 입력해 주세요"
                  @keyup.enter="nextEmail"
                ></el-input>
              </el-form-item>
              <el-form-item label="이메일" prop="email">
                <el-input
                  type="email"
                  v-model="user.email"
                  autocomplete="off"
                  placeholder="이메일을 입력해 주세요"
                  @keyup.enter="nextPasswd"
                  ref="refEmail"
                >
                  <template #append>
                    <el-button
                      class="green-color-btn"
                      id="green-btn"
                      @click="duplicate"
                      :disabled="!isEmailFormValid"
                      >중복확인</el-button
                    >
                  </template>
                </el-input>
              </el-form-item>
              <el-form-item label="비밀번호" prop="passwd">
                <el-input
                  type="password"
                  v-model="user.passwd"
                  autocomplete="off"
                  placeholder="9~16자, 영문, 숫자, 특수문자(~,!,@,#,$,%,^,&,*,(,),+,|,=)를 최소 1개씩 포함시켜 주세요)"
                  @keyup.enter="nextCheck"
                  ref="refPasswd"
                ></el-input>
              </el-form-item>
              <el-form-item label="비밀번호 확인" prop="check">
                <el-input
                  type="password"
                  v-model="user.check"
                  autocomplete="off"
                  placeholder="비밀번호 확인"
                  ref="refCheck"
                  @keyup.enter="onSubmit()"
                ></el-input>
              </el-form-item>
              <el-button
                class="green-color-btn"
                @click="onSubmit()"
                :disabled="!isValid"
                >회원가입</el-button
              >
              <el-button @click="resetForm()">다시쓰기</el-button>
            </el-form>
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
    const ruleForm = ref(null);
    const loading = ref(false);
    const onSubmit = () => {
      let payload = {
        username: user.username,
        email: user.email,
        passwd: user.passwd,
      };
      ruleForm.value.validate((valid) => {
        if (valid) {
          store.dispatch("auth/signup", payload).then(() => {
            router.push("/userchat");
          });
        }
      });
    };
    const refEmail = ref("");
    const refPasswd = ref("");
    const refCheck = ref("");
    const isValid = computed(
      () => user.username && user.email && user.passwd && user.check && isEmailValid.value
    );
    const isEmailValid = computed(() => store.state.auth.emailValid);
    const isEmailFormValid = ref(false);

    const load = () => {
      loading.value = true;
      onSubmit();
      setTimeout(loadFinsh, 1000);
    };
    const loadFinsh = () => {
      loading.value = false;
    };

    const duplicate = () => {
      let emailInfo = {
        email: user.email,
      };
      store.dispatch("auth/duplicateEmail", emailInfo);
    };

    const resetForm = () => {
      ruleForm.value.resetFields();
    };

    var validateName = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("닉네임을 입력해 주세요"));
      } else {
        callback();
      }
    };
    var checkEmail = (rule, value, callback) => {
      if (!value) {
        isEmailFormValid.value = false;
        return callback(new Error("이메일을 입력해 주세요"));
      } else {
        let pattern =
          /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
        if (value.match(pattern) == null) {
          isEmailFormValid.value = false;
          callback(new Error("이메일 형식을 맞춰 주세요"));
        } else {
          isEmailFormValid.value = true;
          callback();
        }
      }
    };
    var validatePass = (rule, value, callback) => {
      let specialPattern =
        /^(?=.*[A-Za-z])(?=.*\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\d~!@#$%^&*()+|=]{9,16}$/i;
      if (value === "") {
        callback(new Error("비밀번호를 입력해 주세요"));
      } else {
        if (9 > value.length) {
          callback(new Error("아직 9자리가 아니에요"));
        } else if (16 < value.length) {
          callback(new Error("16자리를 초과했습니다 :("));
        } else if (value.match(specialPattern) == null) {
          callback(new Error("영문자, 숫자, 특수문자를 최소 1개씩 포함시켜 주세요"));
        } else {
          callback();
        }
      }
    };
    var validateCheck = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("비밀번호를 다시 입력해주세요"));
      } else if (value !== user.passwd) {
        callback(new Error("두 비밀번호가 달라요! 확인해 보시겠어요?"));
      } else {
        callback();
      }
    };
    const user = reactive({
      username: "",
      email: "",
      passwd: "",
      check: "",
    });
    const rules = {
      username: [{ validator: validateName, trigger: "blur" }],
      passwd: [{ validator: validatePass, trigger: "blur" }],
      check: [{ validator: validateCheck, trigger: "blur" }],
      email: [{ validator: checkEmail, trigger: "blur" }],
    };

    const nextEmail = () => {
      refEmail.value.focus();
    };
    const nextPasswd = () => {
      refPasswd.value.focus();
    };
    const nextCheck = () => {
      refCheck.value.focus();
    };

    return {
      store,
      ruleForm,
      onSubmit,
      duplicate,
      resetForm,
      user,
      rules,
      isValid,
      refEmail,
      refPasswd,
      refCheck,
      nextEmail,
      nextPasswd,
      nextCheck,
      isEmailValid,
      isEmailFormValid,
      loading,
      load,
      loadFinsh,
    };
  },
};
</script>

<style scoped>
#SignupMain {
  height: 98%;
  background-image: url("../../assets/signup_back.png");
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center;
}

#SignupMain::before {
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
  top: 20%;
  left: 10%;
}

#MiniLogo {
  width: 30%;
  height: 30%;
  display: block;
  position: static;
}

#green-btn {
  background-color: #006f3e;
  color: #fff;
  border: 0;
}
</style>
