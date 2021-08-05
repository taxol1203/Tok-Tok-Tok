import { createStore } from "vuex";
import axios from "@/axios";
import createPersistedState from "vuex-persistedstate";
import { moduleQna } from "@/store/modules/moduleQna";
import { auth } from "@/store/modules/auth";

export default createStore({
  plugins: [
    createPersistedState({
      paths: ["auth"],
    }),
  ],
  modules: { moduleQna, auth },
  state: {
    //user-info: state.auth.user로 사용하면 됨
    rooms: [],
    selected_room: null, // 클릭한 채팅방의 세션id를 저장
    session_key: {},
    user_selected_room: "",
    user_message: {},
    //qnahistory를 아마 넣을 예정
  },
  mutations: {
    GET_ROOMS(state, payload) {
      state.rooms = payload;
    },
    ADD_ROOMS(state, payload) {
      state.rooms.push(payload);
    },
    PICK_ROOM(state, payload) {
      state.selected_room = payload;
    },
    MESSAGE_PUSH(state, payload) {
      state.session_key[`${state.selected_room}`].messages.push(payload);
    },
    SAVE_USER_CHAT_ROOM_ID(state, payload) {
      state.user_selected_room = payload;
      console.log("IN SAVE ^^ SELECTED_ROOM: " + state.user_selected_room);
    },
    SESSION_INIT(state, payload) {
      payload.message = [];
      state.session_key = payload;
    },
  },
  actions: {
    async getChatRooms({ commit, state }) {
      try {
        const res = await axios.get(`api/api/chat/rooms/user/${state.auth.user.pk_idx}`);
        console.log(res.data);
        commit("GET_ROOMS", res.data);
      } catch (error) {
        console.log(error);
      }
    },
    async createChatRooms({ commit, state }) {
      try {
        const res = await axios.post("api/api/chat/room", {
          unread: 0,
          fk_created_by_idx: state.auth.user.pk_idx, // 상담 신청하는 고객의 userid
          fk_client_idx: state.auth.user.pk_idx, // 위 필드와 동일값 넣어주면 됨.
          status: "OPEN", //아마 default 값
        });
        console.log(res.data);
        commit("ADD_ROOMS", res.data);
        commit("SAVE_USER_CHAT_ROOM_ID", res.data.session_id);
        //
      } catch (error) {
        console.log(error);
        alert("채팅방 개설 실패");
      }
    },
    pickRoom({ commit }, key) {
      commit("PICK_ROOM", key);
    },
    sessionInit({ commit }, key) {
      const res = axios.get(`api/api/chat/room/${key}`);
      commit("SESSION_INIT", res);
    },
  },
  getters: {
    get_messages: (state) => {
      return state.session_key[`${state.selected_room}`].messages;
    },
    get_user_messages: (state) => {
      // return state.session_key[`${state.selected_room}`].messages;
      return state.session_key[`${state.selected_room}`].messages;
    },
  },
});
