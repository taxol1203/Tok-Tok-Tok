// import axios from 'axios'

export default {
  namespaced: true,
  state: {
    // 채팅방 전체 목록 받을 배열
    rooms: [],
  },
  mutations: {
    GET_ROOMS(state, payload) {
      state.rooms.push(payload)
    },
  },
  actions: {

  },
  getters: {},
}