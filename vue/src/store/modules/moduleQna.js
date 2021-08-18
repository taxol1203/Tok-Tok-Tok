import axios from '@/axios';
import { ElMessage } from 'element-plus';
export const moduleQna = {
  namespaced: true,
  state: {
    qnaList: [],
    select: {},
    old_answer: [],
    new_answer: [],
    next_scene: [],
  },
  getters: {
    allQnaCount: state => {
      return state.qnaList.length + 1;
    },
    getKey: state => {
      return state.select.pk_idx;
    }
  },
  mutations: {
    //시나리오 생성
    addQna: (state, payload) => {
      let tmp = state.qnaList;
      tmp.push(payload);
      state.qnaList = tmp;
    },
    //전체 질문 불러오기
    loadQna: (state, payload) => {
      state.qnaList = payload;
    },
    //특정 질문 선택(클릭)
    pickQna: (state, payload) => {
      state.pick_idx = payload;
      state.qnaList.forEach(item => {
        if (item.pk_idx == payload) {
          state.select = item;
        }
      });
    },
    //특정 질문의 모든 답변 불러오기
    loadAnswer: (state, payload) => {
      state.old_answer = payload.data;
      let tmp = [];
      payload.data.forEach(item => {
        tmp.push(item.fk_next_idx);
      });
      state.next_scene = tmp;
    },
    //새 답변 추가하기
    addNewAnswer: (state, idx) => {
      state.new_answer.push({
        content: '',
        fk_next_idx: 0,
        fk_previous_idx: idx,
        pk_idx: 0,
      });
    },
    //새 답변 저장하기
    addAnswer: (state) => {
      let tmp = state.old_answer;
      let tmp_scene = state.next_scene;
      state.new_answer.forEach(item => {
        tmp.push(item);
        tmp_scene.push(item.fk_next_idx);
      });
      state.old_answer = tmp;
      state.next_scene = tmp_scene;
      state.new_answer = [];

    },
    //title, content 수정하기(등록 버튼으로)
    editContent: (state, payload) => {
      state.qnaList.forEach(item => {
        if (item.pk_idx == payload.pk_idx) item = payload;
      });
      state.select = payload;
      ElMessage({
        showClose: true,
        message: '시나리오의 질문이 수정되었습니다.',
        type: 'success',
      });
    },
    //선택한 시나리오 삭제하기(연결, 종료는 삭제되지 않음)
    removeQna: (state, payload) => {
      let i = 0;
      for (i = 0; i < state.qnaList.length; i++) {
        if (state.qnaList[i].pk_idx == payload) break;
      }
      state.qnaList.splice(i, 1);
      state.select = {};
    },
    resetNewAns: (state) => {
      state.new_answer = [];
    },
    removeOldAns: (state, index) => {
      state.old_answer.splice(index, 1);
    },
    removeNewAns: (state, index) => {
      state.new_answer.splice(index, 1);
    }
  },
  actions: {
    addQna: ({ commit }, payload) => {
      axios.post('api/qna/question', payload)
        .then(() => {
          commit('addQna', payload);
        });
    },
    loadQna: ({ commit }) => {
      axios.get('api/qna/question')
        .then(payload => {
          commit('loadQna', payload.data);
        });
    },
    loadAnswer: ({ commit }, idx) => {
      axios.get('api/qna/question/nextAnswers/' + idx)
        .then(payload => {
          commit('loadAnswer', payload.data);
        });
    },
    pickQna: ({ commit }, payload) => {
      commit('pickQna', payload);
    },
    loadAnswer: ({ commit }, idx) => {
      axios.get(`api/qna/question/nextAnswers/${idx}`)
        .then(payload => {
          commit('loadAnswer', payload);
        });
    },
    editContent: ({ commit }, payload) => {
      axios.put(`api/qna/question/${payload.pk_idx}`, payload)
        .then(() => {
          commit('editContent', payload);
        });
    },
    updateAnswer: ({ state }) => {
      var flag = true;
      state.old_answer.forEach(item => {
        axios.put(`api/qna/answer/${item.pk_idx}`, item)
          .then(() => {
          })
          .catch(() => {
            flag = false;
          });
      });
      if (flag) {
        ElMessage({
          showClose: true,
          message: '시나리오의 답변이 수정되었습니다.',
          type: 'success',
        });
      } else {
        ElMessage({
          showClose: true,
          message: '수정 중 문제가 생겼습니다. 다시 시도해주세요.',
          type: 'fail',
        });
      }
    },
    removeQna: ({ commit }, idx) => {
      axios.delete(`api/qna/question/${idx}`)
        .then(() => {
          commit('removeQna', idx);
        });
    },
    addAnswer: ({ commit, state }) => {
      state.new_answer.forEach(item => {
        let tmp = {
          content: item.content,
          fk_next_idx: item.fk_next_idx,
          fk_previous_idx: item.fk_previous_idx
        };
        axios.post(`api/qna/answer`, tmp)
          .then(() => {
            commit('addAnswer');
          });
      });
    },
    removeOldAns: ({ state, commit }, idx) => {
      var key = state.old_answer[idx].pk_idx;
      axios.delete(`api/qna/answer/${key}`)
        .then(() => {
          commit('removeOldAns', idx);
          ElMessage({
            showClose: true,
            message: '삭제가 완료되었습니다.',
            type: 'success',
          });
        });
    }
  },
  modules: {}
};