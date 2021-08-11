import axios from '@/axios';
import { ElMessage } from 'element-plus';


export const auth = {
  namespaced: true,
  state: {
    token:'',
    user: {},
  },
  mutations: {
    login(state, payload){
      state.user = payload
    },
    logout(state) {
      state.user = {}
      state.token = ''
    }
  },
  actions: {
    async login({ state, commit }, payload) {
      try {
        const res = await axios.post('api/auth/login', payload)
        state.token = res.data.token
        commit('login', res.data.user);
        localStorage.setItem('jwt', state.token)
        ElMessage({
          showClose: true,
          message: '로그인이 완료되었습니다.',
          type: 'success',
        });
      } catch (res) {
        console.log(res);
        console.log('login error');
        ElMessage({
          showClose: true,
          message: '로그인에 문제가 발생했습니다.',
          type: 'error',
        });
      }
      
    },
    signup: ({ }, payload) => {
      axios
            .post("api/auth/register", payload)
            .then(() => {
              ElMessage({
                showClose: true,
                message: '회원가입이 완료되었습니다.',
                type: 'success',
              });
            })
        .catch((res) => {
              console.log(res)
              ElMessage({
                showClose: true,
                message: '회원가입에 문제가 발생했습니다.',
                type: 'error',
              });
            });
    },
    duplicateEmail: ({ }, payload) => {
      console.log(payload)
      axios
        .post('api/auth/checkemail', payload)
        .then(() => {
          ElMessage({
            showClose: true,
            message: '사용 중인 이메일입니다.',
            type: 'error',
          });
          user.email = '';
        })
        .catch((e) => {
          if (e.response.status == 404) {
            ElMessage({
              showClose: true,
              message: '사용 가능한 이메일입니다.',
              type: 'success',
            });
          }
        });
    }
  }
}