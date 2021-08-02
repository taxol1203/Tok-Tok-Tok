import axios from 'axios';
export const moduleQna = {
  namespaced : true,
  state: { //data
    qnaList: [],
    select: {},
    pick_idx: '',
    old_answer: [],
    new_answer: [],
  },
  getters: { //computed
    allQnaCount: state => { //parameter에 사용할 컴포넌트 넣기
      return state.qnaList.length+1
    }
  },
  mutations: { //payload는 파라미터로 넘어오는 값
    addQna: (state, payload) => {
      let tmp = state.qnaList
      tmp.push(payload)
      state.qnaList = tmp
    },
    loadQna: (state, payload) => {
      state.qnaList = payload;
    },
    loadAnswer: (state, payload) => {
      state.old_answer = payload;
    },
    pickQna: (state, payload) => {
      state.pick_idx = payload
      state.qnaList.forEach(item => {
        if (item.pk_idx == payload) state.select = item
      })
    },
    // callOldAnswer: (state, payload) => {
    //   payload.forEach(item => {
    //     // var keyValue = item.pk_idx;
    //     // state.old_answer.push({keyValue: item})
    //   })
    // },
    // saveAnswer: (state, idx, payload) => {
    //   state.old_answer.idx = payload
    // },
    
  },
  actions: {
    addQna: ({ commit }, payload) => {
      axios.post('https://i5d204.p.ssafy.io/api/qna/question', {
        data: payload,
      })
      .then(() => {
        commit('addQna', payload)
      })
    },
    loadQna: ({ commit }) => {
      axios.get('https://i5d204.p.ssafy.io/api/qna/question')
        .then(payload => {
          commit('loadQna', payload.data)
      })
    },
    loadAnswer: ({ commit }, idx) => {
      axios.get('https://i5d204.p.ssafy.io/api/qna/question/nextAnswers/'+idx)
        .then(payload => {
        commit('loadAnswer', payload.data)
      })
    },
    pickQna: ({ commit }, payload) => {
      commit('pickQna', payload) 
    },
    callOldAnswer: ({ commit }, idx) => {
      axios.get('https://i5d204.p.ssafy.io/api/qna/question/nextAnswers/',{
        params: idx
      })
      .then(payload => {
        commit('callOldAnswer', payload.data)
      })
    },
    saveAnswer: ({ commit }, idx, payload) => {
      axios.put('https://i5d204.p.ssafy.io/api/qna/answer/', {
        params: idx,
        data: payload,
      })
      .then(() => {
        commit('saveAnswer', idx, payload)
      })
    },
    saveQuestion: ({ commit }, idx, payload) => {
      axios.put('https://i5d204.p.ssafy.io/api/qna/question/', {
        params: idx,
        data: payload,
      })
        .then(() => {
        commit('saveQuestion', idx, payload)
      })
    }
  },
  modules: {}
}