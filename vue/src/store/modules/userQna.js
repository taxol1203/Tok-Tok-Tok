import axios from '@/axios';
export const userQna = {
  namespaced: true,
  state: {
    scenes: {},
    select: '',
    log: [],
  },
  getters: {
    getQuestion: (state) => {
      for (let key in state.scenes) {
        console.log(key)
        if (key == state.select) {
          return state.scenes[key].content.replace('\n', '<br/>')
        }
      }
      // return state.scenes[state.select].content
    },
    getAnswers: (state) => {
      for (let key in state.scenes) {
        if (key == state.select) {
          return state.scenes[key].answers;
        }
      }
    },
    logGetter: (state) => {
      console.log(state.log)
      return state.log;
    }
  },
  mutations: {
    INIT_QUES: (state, payload) => {
      payload.forEach(item => {
        let lineBreak = item.content.replace('\n', '<br><br>');
        item.content = lineBreak;
        state.scenes[item.pk_idx.toString()] = item;
        state.scenes[item.pk_idx.toString()].answers = [];
      });
    },
    INIT_ANS: (state, payload) => {
      payload.forEach(item => {
        state.scenes[item.fk_previous_idx].answers.push(item)
      })
    },
    CHANGE_SELECT: (state, payload) => {
      state.select = payload;
    },
    SET_CURRENT: (state) => {
      state.current = state.scenes[`${state.select}`]
    },
    INIT_log: (state) => {
      state.log.push(state.scenes[1]);
    },
    ADD_LOG: (state) => {
      let tmp = state.log;
      tmp.push(state.scenes[`${state.select}`])
      state.log = tmp;
    }
  },
  actions: {
    async init({ commit }) {
      try {
        const que = await axios.get(`/api/qna/question`)
        if(que.status === 200) commit('INIT_QUES', que.data);
        const ans = await axios.get(`/api/qna/answer`)
        if (ans.status === 200) commit('INIT_ANS', ans.data);
        commit('INIT_log');
      } catch (error) {
        console.log(error)
      }
    }
  },
}