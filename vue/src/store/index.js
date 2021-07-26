import { createStore } from 'vuex'
import axios from 'axios'


export default createStore({
  state: {
    user_idx: 1,
    rooms: [],
    selected_room: null,
  },
  mutations: {
    GET_ROOMS(state, payload) {
      state.rooms = payload
      // console.log(payload)
    },
    ADD_ROOMS(state, payload) {
      state.rooms.push(payload)
    },
    PICK_ROOM(state, payload) {
      state.selected_room = payload
    }
  },
  actions: {
    async getChatRooms({ commit, state }) {
      try {
        const res = await axios.get(`http://localhost:8088/temp/api/chat/rooms/user/${state.user_idx}`)
        console.log(res.data)
        commit('GET_ROOMS', res.data)
      } catch (error) {
        console.log(error)
      }
    },
    async createChatRooms({ commit }) {
      try {
        const res = await axios.post('http://localhost:8088/temp/api/chat/room', {
          unread: 0,
          fk_created_by_idx: 1, // 상담 신청하는 고객의 userid
          fk_client_idx: 1, // 위 필드와 동일값 넣어주면 됨.
        })
        console.log(res.data)
        commit('ADD_ROOMS', res.data)
      } catch (error) {
        console.log(error)
        alert('채팅방 개설 실패')
      }
    },
    pickRoom({ commit }, key) {
      commit('PICK_ROOM', key)
    }
  },
  getters: {

  }
})
