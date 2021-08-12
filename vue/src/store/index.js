import { createStore } from "vuex";
import axios from "@/axios";
import createPersistedState from "vuex-persistedstate";
import { moduleQna } from "@/store/modules/moduleQna";
import { auth } from "@/store/modules/auth";
import { userQna } from "@/store/modules/userQna"

export default createStore({
  plugins: [
    createPersistedState({
      paths: ["auth"],
    }),
  ],
  modules: { moduleQna, auth, userQna },
  state: {
    //user-info: state.auth.user로 사용하면 됨
    rooms: {},
    selected_room: null, // 클릭한 채팅방의 세션id를 저장
    session_key: {},
    //qnahistory를 아마 넣을 예정
    list_status: "LIVE",
    closeMsg: "",
    stompClient: '',
  },
  mutations: {
    GET_ROOMS(state, payload) {
      state.rooms = payload;
    },
    ADD_ROOMS(state, payload) {
      payload.messages = [];
      state.session_key = payload;
    },
    PICK_ROOM(state, payload) {
      // console.log(state.rooms[payload])
      state.selected_room = payload;
    },
    USER_MSG_PUSH(state, payload) {
      state.session_key.messages.push(payload);
      //방이 대기 상태일 때, msg가 처음 들어오면 live로 바꿈
      if (state.session_key.status == "OPEN") {
        state.session_key.status = "LIVE";
      }
    },
    MESSAGE_PUSH(state, payload) {
      // console.log(state.rooms[`${state.selected_room}`].session.status)
      // console.log(payload)
      if (!(Object.keys(state.rooms[`${state.selected_room}`]).includes('messages'))) {
        state.rooms[`${state.selected_room}`].messages = [payload];
      }
      state.rooms[`${state.selected_room}`].messages.push(payload);
      // 관리자가 첫 메세지 보냈을때 방상태를 LIVE로 바꾸기
      if (state.rooms[`${state.selected_room}`].session.status == "OPEN") {
        state.rooms[`${state.selected_room}`].session.status = "LIVE";
        state.list_status = "LIVE";
      }
    },
    SAVE_USER_CHAT_ROOM_ID(state, payload) {
      state.selected_room = payload;
    },
    STATUS_CHANGE(state, payload) {
      state.list_status = payload;
    },
    CHAT_CLOSE(state) {
      state.rooms[`${state.selected_room}`].session.status = "END"
      state.selected_room = '';
      // console.log("close")
    },
    CLOSE_MSG(state) {
      state.selected_room = ""
      state.closeMsg = "상담이 종료되었습니다."
    },
    stompSetter(state, payload) {
      console.log(payload)
      state.stompClient = payload;
    }
  },
  actions: {
    async getChatRooms({ commit, state }) {
      try {
        const res = await axios.get(`api/api/chat/admin/init/${state.auth.user.pk_idx}`);
        for (var p in res.data) {
          const client = await axios.get(`api/auth/user/${res.data[p].session.fk_client_idx}`) //해당 방에 관련된 유저정보 담기
          res.data[p].client = client.data;
        }
        commit("GET_ROOMS", res.data);
      } catch (error) {
        console.log(error);
      }
    },
    async createChatRooms({ commit, state }, payload) {
      try {
        const res = await axios.post("api/api/chat/room", {
          unread: 0,
          fk_created_by_idx: state.auth.user.pk_idx, // 상담 신청하는 고객의 userid
          fk_client_idx: state.auth.user.pk_idx, // 위 필드와 동일값 넣어주면 됨.
          status: "OPEN", //아마 default 값
          qna_history: payload
        });
        commit("ADD_ROOMS", res.data); //rooms에 저장
        commit("SAVE_USER_CHAT_ROOM_ID", res.data.session_id); //selected_id
      } catch (error) {
        console.log(error);
        alert("채팅방 개설 실패");
      }
    },
    pickRoom({ commit }, key) {
      commit("PICK_ROOM", key);
    },
    async enterRoom({ commit, state }, payload) {
      try {
        const res = await axios.put(`/api/api/chat/room/${state.selected_room}`, {
          admin_pk_idx: state.auth.user.pk_idx,
        });
        // console.log(payload)
        if(res.status == 200) commit("MESSAGE_PUSH", payload);
      } catch (err) {
        console.log(err);
      }
    },
    chatClose({ commit, state }) {
      axios
        .delete(`/api/api/chat/room/${state.selected_room}`, {
          data: {
            admin_pk_idx: state.auth.user.pk_idx,
          }
        }).then(() => {
          commit('CHAT_CLOSE')
        }).catch(err => {
          console.log(err)
        });
    }

  },
  getters: {
    get_messages: (state) => {
      return state.rooms[`${state.selected_room}`].messages;
    },
    get_room_list: (state) => {
      let roomList = [];
      for (let i in state.rooms) {
        let room = state.rooms[i];
        // console.log(room.session.status)
        if (room.session.status === state.list_status) {
          roomList.push(room);
        }
      }
      roomList.sort(function (a, b) {
        return a.session.created_at > b.session.created_at ? -1 : 1;
      });
      return roomList;
    },
    get_user_messages: (state) => {
      return state.session_key.messages;
    },
    get_user_room_status: (state) => {
      return state.session_key.status;
    },
    clientGetter: (state) => {
      return state.rooms[`${state.selected_room}`].client;
    },
    qnaGetter: (state) => {
      return state.rooms[`${state.selected_room}`].session.qna_history.split('|')
    },
    get_selected_idx: (state) => {
      return state.selected_room
    },
    statusGetter: (state) => {
      return state.rooms[`${state.selected_room}`].session.status;
    },
    closeMsgGetter: (state) => {
      return state.closeMsg;
    },
    countGetter: (state) => {
      let cnt = [0, 0, 0];
      for (let i in state.rooms) {
        let room = state.rooms[i];
        switch (room.session.status) {
          case 'OPEN':
            cnt[0]++;
            break;
          case 'LIVE':
            cnt[1]++;
            break;
          case 'END':
            cnt[2]++;
            break;
        }
      }
      return cnt;
    },
    stompGetter: (state) => {
      return state.stompClient;
    }
  },
});
