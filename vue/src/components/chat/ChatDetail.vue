<template>
  <div style="position: relative; width: 650px; height: 750px; padding: 10px">
    <!-- 상대방 -->
    <el-scrollbar ref="scrollbar" id="topMessages">
      <div v-for="(msg, index) in messages" :key="index">
        <el-row>
          <el-col v-if="msg.fk_author_idx == userName">
            <div class="message-me">
              {{ msg.message }}
            </div>
          </el-col>
          <el-col v-else>
            <div class="message-other">{{ msg.message }}</div>
          </el-col>
        </el-row>
      </div>
    </el-scrollbar>
    <div>
      <el-row id="bottomInput">
        <!-- 입력창 -->
        <el-col :span="2">
          <el-button
            icon="el-icon-video-camera"
            class="icon-m-p green-color-btn"
          ></el-button>
        </el-col>
        <el-col :span="20">
          <div>
            <el-input
              type="text"
              @keyup.enter="sendMessage"
              v-model="message"
              placeholder="Please input"
              clearable
            >
            </el-input>
          </div>
        </el-col>
        <el-col :span="2">
          <el-button
            @click="sendMessage"
            icon="el-icon-s-promotion"
            class="icon-m-p green-color-btn"
          ></el-button>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
// import axios from "axios";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import { useStore } from "vuex";
import { ref, computed } from "vue";

export default {
  name: "Chat",
  components: {},
  setup() {
    const store = useStore();
    const sessionId = computed(() => store.state.selected_room);
    const messages = computed(() => store.getters.get_messages);
    const message = ref("");
    let connected = false;
    let stompClient = "";
    let userName = ref(store.state.user_info.pk_idx); // pk_idx를 저장하는 곳

    const connect = () => {
      const serverURL = "https://i5d204.p.ssafy.io/api/chat"; // 서버 채팅 주소
      let socket = new SockJS(serverURL);
      stompClient = Stomp.over(socket);
      console.log(`connecting to socket=> ${serverURL}`);
      stompClient.connect(
        {},
        (frame) => {
          connected = true;
          console.log("CONNECT SUCCESS ++ status : established", frame);
          // 구독 == 채팅방 입장.
          stompClient.subscribe("/send/" + sessionId.value, (res) => {
            console.log("receive from server:", res.body);
            store.commit("MESSAGE_PUSH", JSON.parse(res.body)); // 수신받은 메세지 표시하기
            switch (res.body.type) {
              case "MSG":
                break;
              case "JOIN":
                // 방을 생성할 때 백엔드단에서 처리하므로 신경 x
                break;
              case "QUIT":
                // 만약 둘 중 하나가 나가면 더 이상 채팅을 못치는 프론트구현
                break;
              case "VID":
                // vid 시작시 -> 화상채팅 시작하기 버튼만 딸랑 띄우기
                break;
              default:
                // 알수없는 오류...
                break;
            }
          });
        },
        (error) => {
          // 소켓 연결 실패
          console.log("status : failed, STOMP CLIENT 연결 실패", error);
          connected = false;
        }
      );
    };
    connect(sessionId.value);

    const sendMessage = () => {
      if (userName.value !== "" && message.value !== "") {
        // 이벤트 발생 엔터키 + 유효성 검사는 여기에서
        send({ message: message }); // 전송 실패 감지는 어떻게? 프론트단에서 고민좀 부탁 dream
      }
      message.value = "";
    };

    const send = () => {
      console.log("Send message:" + message.value);
      if (stompClient && stompClient.connected) {
        console.log("IN SOCKET");
        const msg = {
          message: message.value, // 메세지 내용. type이 MSG인 경우를 제외하곤 비워두고 프론트단에서만 처리.
          fk_author_idx: userName.value, // 작성자의 회원 idx
          created: "", // 작성시간, 공란으로 비워서 메세지 보내기. response에는 담겨옵니다.
          deleted: false, // 삭제된 메세지 여부. default = false
          fk_session_id: sessionId.value, // 현재 채팅세션의 id.
          // 주의할 점은, 방 세션 id가 아닌, 방 정보의 pk_idx를 첨부한다. created 라이프사이클 메서드 참조.
          type: "MSG", // 메세지 타입.
        };
        stompClient.send("/receive/" + sessionId.value, JSON.stringify(msg), {});
      }
    };

    return {
      sessionId,
      messages,
      message,
      store,
      sendMessage,
      send,
      connect,
      connected,
      stompClient,
      userName,
    };
  },
};
</script>
<style>
#bottomInput {
  bottom: 0px;
  width: 100%;
}
#topMessages {
  display: block;
  top: 0px;
  height: 700px;
  width: 100%;
}
.el-scroll {
  overflow-x: hidden;
}
.message-me {
  border: 1px solid #f7f4f0;
  border-radius: 10px 10px 0px 10px;
  background: #f7f4f0;
  float: right;
  padding: 10px;
  margin: 5px 10px 5px 5px;
  max-width: 300px;
}
.message-other {
  border: 1px solid #27251f;
  border-radius: 10px 10px 10px 0px;
  color: #fff;
  background: #27251f;
  float: left;
  padding: 10px;
  margin: 5px 10px 5px 5px;
  max-width: 300px;
}
#inputBox {
  width: 90%;
  height: 100%;
  background-color: transparent;
  border: 0px solid #eee;
}
/* hover로 버튼 색 변하게 하기: 추가기능 */
</style>
