import { createStore } from "vuex";
import axios from "axios";
import { moduleQna } from "@/store/modules/moduleQna";
import { auth } from "@/store/modules/auth";

export default createStore({
  modules: { moduleQna, auth },
  state: {
    user_idx: 1,
    //user-info는: create room을 할 때
    //   fk_created_by_idx를 가지고(앞으로 만들어질) API를 활용해서 user- info를 store 저장해둔다.
    user_info: {},
    rooms: [],
    selected_room: null, // 더블클릭한 채팅방의 세션id를 저장
    session_key: {},
    //qnahistory를 아마 넣을 예정
  },
  mutations: {
    GET_ROOMS(state, payload) {
      state.rooms = payload;
      // console.log(payload)
    },
    ADD_ROOMS(state, payload) {
      state.rooms.push(payload);
    },
    PICK_ROOM(state, payload) {
      state.selected_room = payload;
      console.log(payload);
      console.log(state.user_info);
    },
    save_userinfo(state, payload) {
      state.user_info = payload;
    },
  },
  actions: {
    async getChatRooms({ commit, state }) {
      try {
        const res = await axios.get(
          `https://i5d204.p.ssafy.io/api/api/chat/rooms/user/${state.user_idx}`
        );
        console.log(res.data);
        commit("GET_ROOMS", res.data);

        // const res = await axios.get(
        //   `http://localhost:8088/temp/api/chat/rooms/user/${state.user_idx}`
        // );
      } catch (error) {
        console.log(error);
      }
    },
    async createChatRooms({ commit }) {
      try {
        // const res = await axios.post("https://i5d204.p.ssafy.io/api/api/chat/room", {
        //   unread: 0,
        //   fk_created_by_idx: 1, // 상담 신청하는 고객의 userid state.user_info
        //   fk_client_idx: 1, // 위 필드와 동일값 넣어주면 됨.
        // });
        console.log(res.data);
        console.log("CREATE ROOM할 때" + state.user_info);
        console.log("CREATE ROOM할 때" + state.user_info);
        // commit("ADD_ROOMS", res.data);
      } catch (error) {
        console.log(error);
        alert("채팅방 개설 실패");
      }
    },
    pickRoom({ commit }, key) {
      commit("PICK_ROOM", key);
    },
  },
  getters: {},
});
//test
