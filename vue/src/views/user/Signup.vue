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
            <el-form-item label="닉네임" prop="username">
              <el-input
                type="text"
                v-model="user.username"
                autocomplete="off"
                placeholder="닉네임을 입력해주세요"
                @keyup.enter="nextEmail"
              ></el-input>
            </el-form-item>
            <el-form-item label="이메일" prop="email">
              <el-input
                type="email"
                v-model="user.email"
                autocomplete="off"
                placeholder="이메일을 입력해주세요"
                @keyup.enter="nextPasswd"
                ref="refEmail"
              >
                <template #append>
                  <el-button class="green-color-btn" id="green-btn" @click="duplicate"
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
                placeholder="비밀번호(9~16자 조건 명확히되면 여기 추가?)"
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
              ></el-input>
            </el-form-item>
            <el-button class="green-color-btn" @click="onSubmit()" :disabled="!isValid"
              >회원가입</el-button
            >
            <el-button @click="resetForm()">다시쓰기</el-button>
          </el-form>
        </el-card>
      </div>
    </el-col>
  </el-row>
  <el-row :gutter="20" justify="center">
    <el-col :span="12" :offset="0" id="footer">
      <strong>Help Desk </strong>
      <em>
        <a href="tel:02-3429-5041">02-3429-5041</a>
      </em>
      <br />
      <span>평일 상담시간: 09:00 ~ 19:00</span>
    </el-col>
  </el-row>
</template>

<script>
import { reactive, ref, watch, computed } from "vue";
import { useStore } from "vuex";

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
          store.dispatch("auth/signup", payload);
        }
      });
    };
    const refEmail = ref("");
    const refPasswd = ref("");
    const refCheck = ref("");
    // const isValid = ref(false);
    //일단은 입력만 다하면 켜지도록
    const isValid = computed(() => user.username && user.email && user.passwd && user.check);

    const duplicate = () => {
      let tmp = {
        email: user.email,
      };
      store.dispatch("auth/duplicateEmail", tmp);
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
        return callback(new Error("이메일을 입력해 주세요"));
      } else {
        let pattern =
          /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
        if (value.match(pattern) == null) {
          callback(new Error("이메일 형식을 맞춰주세요"));
        } else {
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
      email: "",
      passwd: "",
      check: "",
      username: "",
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
    };
  },
};
</script>

<style>
/* 왜 인지 class가 안 먹어서 따로 지정함 */
#green-btn {
  background-color: #006f3e;
  color: #fff;
  border: 0;
}
#footer {
  margin-top: 2rem;
  margin-bottom: 2rem;
}
</style>
