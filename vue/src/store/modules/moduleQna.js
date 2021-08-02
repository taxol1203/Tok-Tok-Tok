import axios from 'axios';
export const moduleQna = {
  namespaced : true,
  state: { //data
    qnaList: [],
    select: {},
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
    pickQna: (state, payload) => {
      state.qnaList.forEach(item => {
        if (item.pk_idx == payload) state.select = item
      })
      
    }
  },
  actions: {
    addQna: ({ commit }, paylaod) => {
      commit('addQna', paylaod)
    },
    loadQna: ({ commit }) => {
      axios.get('https://i5d204.p.ssafy.io/api/qna/question')
        .then(payload => {
          commit('loadQna', payload.data)
      })
    },
    pickQna: ({ commit }, payload) => {
      commit('pickQna', payload) 
    }
  },
  modules: {}
}