<template>
  <el-row justify="center">
    <el-col :span="12">
      <div>
        <el-card shadow="always">
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
                ref="refPasswd"
              ></el-input>
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
  <Footer />
</template>

<script>
import { reactive, ref, computed } from "vue";
import { useStore } from "vuex";
import Footer from "@/components/footer.vue";

export default {
  components: {
    Footer,
  },
  setup() {
    const store = useStore();
    const token = computed(() => store.state.auth.user);
    const formLabelAlign = ref(null);
    const onSubmit = () => {
      let payload = {
        email: user.email,
        passwd: user.passwd,
      };
      formLabelAlign.value.validate((valid) => {
        if (valid) {
          store.dispatch("auth/login", payload);
        }
      });
    };
    const refPasswd = ref("");

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
      // 이거 필요한가?
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
      passwd: [{ validator: validatePasswd, trigger: "blur" }],
    };
    const resetForm = () => {
      formLabelAlign.value.resetFields();
    };
    const nextPasswd = () => {
      refPasswd.value.focus();
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
      validatePasswd,
      logout,
      refPasswd,
      nextPasswd,
    };
  },
};
</script>

<style></style>
