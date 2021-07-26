import axios from 'axios';
import { createStore } from "vuex";
export default createStore({
  state: { //data
    qnaList: [
      {
        q_idx: '1',
        contents: '시작합니다.',
        answers: [
          {
            q_idx: '1-1',
            contents: '예상답변1',
            fk_idx: '2',
          },
          {
            q_idx: '1-2',
            contents: '예상답변2',
            fk_idx: '2',
          },
        ],
      },
      {
        q_idx: '2',
        contents: '종료합니다.',
        answers: [],
      },
    ],
    user: {
      email: '',
      passwd: '',
      username: '',
      check: '',
    }
  },
  getters: { //computed
    allQnaCount: state => { //parameter에 사용할 컴포넌트 넣기
      return state.qnaList.length + 1
    }
  },
  mutations: { //payload는 파라미터로 넘어오는 값
    addQna: (state, payload) => {
      state.qnaList.push(payload)
    },
    successSignUp: () => {
      alert('회원가입이 완료되었습니다.')
    },
    successLogin: (state, payload) => {
      alert('로그인이 완료되었습니다.')
      localStorage.setItem('jwt', payload.data.token);
    }
  },
  actions: {
    addQna: ({ commit }, paylaod) => {
      //여기다가 로직을 넣고 생성된 데이터들을
      //mutation에다가 commit하기 위해 actions가 필요
      commit('addQna', paylaod)
    },
    signUp: ({ commit }, payload) => {
      axios.post('http://localhost:8088/temp/api/auth/register', payload)
        .then((res) => {
          commit('successSignUp', res);
        })
        .catch((data) => {
          console.log(data)
          console.log('signup error')
        })
    },
    login: ({ commit }, payload) => {
        axios.post('http://localhost:8088/temp/api/auth/login', payload)
        .then((res) => {
          commit('successLogin', res);
        })
        .catch((data) => {
          console.log(data)
          console.log('login error')
        })
    }
  },
  modules: {}
});
