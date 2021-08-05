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
      console.log(state.select)
      console.log(state.scenes)
      console.log(typeof(state.scenes))
      console.log(Object.keys(state.scenes))
      console.log(state.scenes[`${state.select}`])
      return state.scenes[`${state.select}`];
    }
  },
  mutations: {
    INIT_QUES: (state, payload) => {
      payload.forEach(item => {
        state.scenes[item.pk_idx] = item;
        state.scenes[item.pk_idx].answers = [];
      });
    },
    INIT_ANS: (state, payload) => {
      payload.forEach(item => {
        state.scenes[item.fk_previous_idx].answers.push(item)
      })
    },
    CHANGE_SELECT: (state, payload) => {
      state.select = payload;
      state.current = state.scenes[`${payload}`];
    }
  },
  actions: {
    async init({ commit }) {
      try {
        const que = await axios.get(`/api/qna/question`)
        if(que.status === 200) commit('INIT_QUES', que.data);
        const ans = await axios.get(`/api/qna/answer`)
        if(ans.status === 200) commit('INIT_ANS', ans.data);
      } catch (error) {
        console.log(error)
      }
    }
  },
}