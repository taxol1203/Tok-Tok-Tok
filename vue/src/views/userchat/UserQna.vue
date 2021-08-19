<template>
  <div style="position: relative; padding: 10px">
    <el-scrollbar ref="scrollbar" id="topMessages">
      <div v-for="(item, index) in log" :key="index">
        <el-row>
          <el-col>
            <div class="message-other" v-html="item.content"></div>
          </el-col>
        </el-row>
        <el-row v-for="ans in item.answers" :key="ans.pk_idx">
          <el-col>
            <div class="message-me" @click="chooseAnswer(ans.fk_next_idx, ans.content)">
              {{ ans.content }}
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <div class="message-me" @click="createChatRoom">상담원 연결</div>
          </el-col>
        </el-row>
      </div>
      <div v-if="isOpen == 'OPEN'">
        <div v-loading="loading">상담 연결 중입니다. 잠시만 기다려주세요.</div>
      </div>
      <user-chat-detail v-if="sessionId" />
      <p v-if="realChat == 'END'">상담이 종료되었습니다.</p>
    </el-scrollbar>
    <el-row id="bottomInput" v-if="realChat == 'LIVE'">
      <el-col :span="19">
        <div>
          <el-input
            type="text"
            @keyup.enter="sendMessage"
            v-model="userMsg"
            placeholder="메시지를 입력해주세요:)"
            clearable
          >
          </el-input>
        </div>
      </el-col>
      <el-col :span="5">
        <el-button
          @click="sendMessage"
          icon="el-icon-s-promotion"
          class="green-color-btn"
        ></el-button>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import { useStore } from "vuex";
import { ref, computed, watch, onMounted, onUpdated } from "vue";
import UserChatDetail from "./UserChatDetail.vue";

export default {
  name: "Chat",
  components: { UserChatDetail },
  setup() {
    const store = useStore();
    const userMsg = ref("");
    const scrollbar = ref("");
    const closeMsg = computed(() => store.getters["closeMsgGetter"]);
    const log = computed(() => store.getters["userQna/logGetter"]);
    store.commit("userQna/CHANGE_SELECT", 1);
    store.commit("userQna/SET_CURRENT");
    let history = "";
    const isOpen = computed(() => store.getters["get_user_room_status"]);
    const loading = true;

    const chooseAnswer = (next_idx, value) => {
      if (history == "") history += value;
      else history += "|" + value;
      store.commit("userQna/CHANGE_SELECT", next_idx);
      store.commit("userQna/ADD_LOG");
      setTimeout(() => {
        scrollbar.value.setScrollTop(Number.MAX_SAFE_INTEGER);
      }, 100);
    };

    onUpdated(() => scrollbar.value.setScrollTop(Number.MAX_SAFE_INTEGER));

    onMounted(() => {
      scrollbar.value.setScrollTop(Number.MAX_SAFE_INTEGER);
    });
    const user_pk_idx = computed(() => store.state.auth.user.pk_idx);
    const realChat = computed(() => store.getters["get_user_room_status"]);
    const sessionId = computed(() => store.getters["get_selected_idx"]);
    const isHidden = computed(() => store.getters["userQna/showUserChat"]);
    let stompClient = computed(() => store.getters["stompGetter"]);
    let connected = ref(false);

    const createChatRoom = () => {
      send("JOIN");
      store.dispatch("createChatRooms", history);
    };
    watch(sessionId, () => {
      connect();
    });
    watch(connected, () => {
      send("JOIN");
    });

    const connect = () => {
      const serverURL = "https://i5d204.p.ssafy.io/api/chat";
      let socket = new SockJS(serverURL);
      store.commit("stompSetter", Stomp.over(socket));
      stompClient.value.connect(
        {},
        (frame) => {
          connected.value = true;
          stompClient.value.subscribe("/send/" + sessionId.value, async (res) => {
            switch (JSON.parse(res.body).type) {
              case "MSG":
                await store.commit("USER_MSG_PUSH", JSON.parse(res.body));
                scrollbar.value.setScrollTop(Number.MAX_SAFE_INTEGER);
                break;
              case "JOIN":
                break;
              case "END":
                store.commit("changeSessionkeyStatus", "END");
                break;
              case "VID":
                await store.commit("USER_MSG_PUSH", JSON.parse(res.body));
                scrollbar.value.setScrollTop(Number.MAX_SAFE_INTEGER);
                break;
              default:
                break;
            }
          });
        },
        (error) => {
          console.log("error: ", error);
          connected.value = false;
        }
      );
    };

    const sendMessage = () => {
      if (user_pk_idx.value && userMsg.value) {
        send("MSG");
      }
      userMsg.value = "";
    };

    const send = (type) => {
      if (stompClient.value && stompClient.value.connected && user_pk_idx.value > 0) {
        const msg = {
          message: userMsg.value,
          fk_author_idx: user_pk_idx.value,
          created: "",
          deleted: false,
          fk_session_id: sessionId.value,
          type: type,
        };
        stompClient.value.send("/receive/" + sessionId.value, JSON.stringify(msg), {});
      }
    };
    return {
      sessionId,
      userMsg,
      log,
      user_pk_idx,
      realChat,
      history,
      connected,
      stompClient,
      closeMsg,
      isHidden,
      scrollbar,
      isOpen,
      loading,
      connect,
      createChatRoom,
      chooseAnswer,
      sendMessage,
      send,
    };
  },
};
</script>
<style scoped>
#bottomInput {
  bottom: 0px;
  width: 100%;
}
#topMessages {
  display: block;
  top: 0px;
  height: 40rem;
  width: 100%;
}
.el-scroll {
  overflow-x: hidden;
}
.message-me {
  border-radius: 10px 10px 0px 10px;
  color: white;
  background: #006f3e;
  float: right;
  padding: 10px;
  margin: 5px 10px 5px 5px;
  max-width: 300px;
  text-align: right;
  cursor: pointer;
}
.message-other {
  border-radius: 10px 10px 10px 0px;
  background-color: #f7f4f0;
  float: left;
  padding: 10px;
  margin: 5px 10px 5px 5px;
  max-width: 300px;
  text-align: left;
}
#inputBox {
  width: 90%;
  height: 100%;
  background-color: transparent;
  border: 0px solid #eee;
}
</style>
