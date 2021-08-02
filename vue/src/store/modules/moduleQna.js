import axios from 'axios';
export const moduleQna = {
  namespaced : true,
  state: { //data
    qnaList: [],
    select: {},
    old_answer: [],
  },
  getters: { //computed
    allQnaCount: state => { //parameter에 사용할 컴포넌트 넣기
      return state.qnaList.length+1
    },
    setKey: state => {
      return state.select.pk_idx
    },
    setTitle: state => {
      return state.select.title
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
        if (item.pk_idx == payload) {
          state.select = item
        } 
      })
    },
    loadAnswer: (state, payload) => {
      state.old_answer = payload.data;
    },
    addAnswer: (state) => {
      let tmp = state.old_answer
      tmp.push({
        pk_idx: tmp.length,
        content: '',
        fk_next_idx: 2,
        fk_previous_idx: state.select.pk_idx
      })
      state.old_answer = tmp
    },
    editContent: (state, payload) => {
      state.select.title = payload.title
      state.select.content = payload.content
    }
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
    loadAnswer: ({ commit }, idx) => {
      axios.get(`https://i5d204.p.ssafy.io/api/qna/question/nextAnswers/${idx}`)
        .then(payload => {
          commit('loadAnswer', payload)
        })
    },
    editContent: ({ commit }, payload) => {
      console.log(payload)
      axios.put(`https://i5d204.p.ssafy.io/api/qna/question/${payload.pk_idx}`, {
        data: {
          content: payload.content,
          pk_idx: payload.pk_idx,
          title: payload.title,
        }
      })
        .then(() => {
        commit('editContent', payload)
      })
    }
  },
  modules: {}
}