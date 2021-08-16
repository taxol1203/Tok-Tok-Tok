import axios from "@/axios";
import { ElMessage } from "element-plus";
import router from '@/router';

export const auth = {
  namespaced: true,
  state: {
    token: "",
    user: {},
    emailValid: "",
  },
  mutations: {
    login(state, payload) {
      state.user = payload;
    },
    logout(state) {
      state.user = {};
      state.token = "";
    },
    EMAIL_VALID(state) {
      state.emailValid = true;
      console.log(state.emailValid);
    },
    EMAIL_UNVALID(state) {
      state.emailValid = false;
      console.log(state.emailValid);
    },
  },
  actions: {
    async login({ state, commit }, payload) {
      try {
        const res = await axios.post("api/auth/login", payload);
        state.token = res.data.token;
        commit("login", res.data.user);
        localStorage.setItem("jwt", state.token);
        ElMessage({
          showClose: true,
          message: "로그인이 완료되었습니다.",
          type: "success",
        });
        router.push({
          name: 'chat'
        })
      } catch (res) {
        console.log(res);
        console.log("login error");
        ElMessage({
          showClose: true,
          message: "로그인에 문제가 발생했습니다.",
          type: "error",
        });
      }
    },
    signup: ({}, payload) => {
      axios
        .post("api/auth/register", payload)
        .then(() => {
          ElMessage({
            showClose: true,
            message: "회원가입이 완료되었습니다.",
            type: "success",
          });
        })
        .catch((res) => {
          console.log(res);
          ElMessage({
            showClose: true,
            message: "회원가입에 문제가 발생했습니다.",
            type: "error",
          });
        });
    },
    duplicateEmail: ({ commit }, payload) => {
      axios
        .post("api/auth/checkemail", payload)
        .then(() => {
          commit("EMAIL_UNVALID");
          ElMessage({
            showClose: true,
            message: "사용 중인 이메일입니다.",
            type: "error",
          });
        })
        .catch((e) => {
          // db에 이런 email이 없더라
          if (e.response.status == 404) {
            commit("EMAIL_VALID");
            ElMessage({
              showClose: true,
              message: "사용 가능한 이메일입니다.",
              type: "success",
            });
          }
        });
    },
  },
};
