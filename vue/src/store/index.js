import { createStore } from "vuex";
import axios from "@/axios";
import createPersistedState from "vuex-persistedstate";
import { moduleQna } from "@/store/modules/moduleQna";
import { auth } from "@/store/modules/auth";
import { userQna } from "@/store/modules/userQna";
import router from '@/router';

export default createStore({
  plugins: [
    createPersistedState({
      paths: ["auth"],
    }),
  ],
  modules: { moduleQna, auth, userQna },
  state: {
    rooms: {},
    selected_room: null,
    session_key: {},
    list_status: "LIVE",
    closeMsg: "",
    stompClient: '',
    video_status: "CLOSE",
    scrollbar: '',
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
      state.selected_room = payload;
    },
    USER_MSG_PUSH(state, payload) {
      state.session_key.messages.push(payload);
      if (state.session_key.status == "OPEN") {
        state.session_key.status = "LIVE";
      }
    },
    MESSAGE_PUSH(state, payload) {
      if (!(Object.keys(state.rooms[`${state.selected_room}`]).includes('messages'))) {
        state.rooms[`${state.selected_room}`].messages = [payload];
      }
      state.rooms[`${state.selected_room}`].messages.push(payload);
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
      state.rooms[`${state.selected_room}`].session.status = "END";
      state.selected_room = '';
    },
    CLOSE_MSG(state) {
      state.selected_room = "";
      state.closeMsg = "상담이 종료되었습니다.";
    },
    stompSetter(state, payload) {
      state.stompClient = payload;
    },
    changeSessionkeyStatus(state, payload) {
      state.session_key.status = payload;
      if (payload == 'END') state.closeMsg = "상담이 종료되었습니다.";
      else state.closeMsg = "";
    },
    OPEN_VIDEO(state) {
      state.video_status = "OPEN";
    },
    CLOSE_VIDEO(state) {
      state.video_status = "CLOSE";
    },
    LIVE_VIDEO(state) {
      state.video_status = "LIVE";
    },
    scrollbarSetter(state, payload) {
      state.scrollbar = payload;
    },
    userChatInit(state) {
      state.closeMsg = "";
      state.session_key = {};
    }
  },
  actions: {
    async getChatRooms({ commit, state }) {
      try {
        const res = await axios.get(`api/api/chat/admin/init/${state.auth.user.pk_idx}`);
        for (var p in res.data) {
          const client = await axios.get(`api/auth/user/${res.data[p].session.fk_client_idx}`);
          const tmp = client.data.pk_idx % 11;
          client.data.imgidx = tmp;
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
          fk_created_by_idx: state.auth.user.pk_idx,
          fk_client_idx: state.auth.user.pk_idx,
          status: "OPEN",
          qna_history: payload
        });
        commit("ADD_ROOMS", res.data);
        commit("SAVE_USER_CHAT_ROOM_ID", res.data.session_id);
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
        if (res.status == 200) commit("MESSAGE_PUSH", payload);
      } catch (err) {
        console.log(err);
      }
    },
    chatClose({ commit, state }, payload) {
      axios
        .delete(`/api/api/chat/room/${payload}`, {
          data: {
            admin_pk_idx: state.auth.user.pk_idx,
          }
        }).then(() => {
          commit('CHAT_CLOSE');
        }).catch(err => {
          console.log(err);
        });
    },
    loginCheck() {
      if (localStorage.getItem('jwt')) {
        router.push({
          name: 'chat'
        }).catch(error => { });
      } else {
        router.push({
          name: 'Login'
        }).catch(error => { });
      }
    },
  },
  getters: {
    get_messages: (state) => {
      return state.rooms[`${state.selected_room}`].messages;
    },
    get_room_list: (state) => {
      let roomList = [];
      for (let i in state.rooms) {
        let room = state.rooms[i];
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
      let tmp = state.rooms[`${state.selected_room}`].session.qna_history.split('|');
      if (tmp[0] == '') return ['즉시 상담'];
      else return tmp;
    },
    get_selected_idx: (state) => {
      return state.selected_room;
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
            cnt[1]++;
            break;
          case 'LIVE':
            cnt[0]++;
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
    },
    is_user: (state) => {
      if (state.auth.user.pk_idx == state.session_key.fk_client_idx) {
        return true;
      } else return false;
    },
    scrollbarGetter: (state) => {
      return state.scrollbar;
    },
    listStatusGetter: (state) => {
      return state.list_status;
    }
  },
});
