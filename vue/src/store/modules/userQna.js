import axios from '@/axios';
export const userQna = {
  namespaced: true,
  state: {
    scenes: {},
    select: '',
    current: {},
  },
  getters: {
    getCur: (state) => {
      // console.log(state.select)
      // console.log(state.scenes)
      // console.log(JSON.stringify(state.scenes))
      // console.log(typeof (state.scenes[`${state.select}`]))
      // console.log(Object.keys(state.scenes[`${state.select}`]))
      // return state.scenes[`${state.select}`];
      return state.current;
    }
  },
  mutations: {
    INIT_QUES: (state, payload) => {
      payload.forEach(item => {
        state.scenes[item.pk_idx] = item;
        state.scenes[item.pk_idx].answers = [];
      });
      console.log(state.scenes)
    },
    INIT_ANS: (state, payload) => {
      payload.forEach(item => {
        state.scenes[item.fk_previous_idx].answers.push(item)
      })
      console.log(state.scenes)
    },
    CHANGE_SELECT: (state, payload) => {
      state.select = payload;
      state.current = state.scenes[payload];
    }
  },
  actions: {
    async init({ commit }) {
      const que = await axios.get(`/api/qna/question`)
      const ans = await axios.get(`/api/qna/answer`)
      commit('INIT_QUES', que.data);
      commit('INIT_ANS', ans.data);
    }
  },
}