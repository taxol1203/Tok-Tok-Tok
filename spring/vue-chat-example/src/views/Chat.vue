<template>
  <div v-cloak>
    {{ sessionId }}
    <div id="app" v-cloak>
      <div>
        <h2>{{ roomName }}</h2>
        <input type="text" v-model="session_pk" />
        <ul>
          <li v-for="(msg, index) in messages" :key="index">
            {{ msg.sender }} : {{ msg.message }}
          </li>
        </ul>
        <div>
          <input type="text" @keyup.enter="sendMessage" v-model="message" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
//@stomp/stompjs
//sockjs-client
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
export default {
  name: "Chat",
  components: {},
  data: function() {
    return {
      sessionId: "",
      roomName: "",
      messages: [],
      message: "",
      session_pk: 0,
    };
  },
  created() {
    this.sessionId = this.$route.params.id; // path parameter로 방 id 전송함. 만약 url에 노출되는게 별로면 props로 전달하는 걸로 변경하셔도...
    // 방 정보 가져오기
    axios.get("http://localhost:8088/temp/api/chat/room/" + this.sessionId).then((response) => {
      this.roomName = response.data.name; // 방에 대한 정보를 가져옵니다.
      console.log(response);
      console.log("room info");
      axios
        .get("http://localhost:8088/temp/api/chat/messages/" + this.sessionId)
        .then((response) => {
          console.log(response.data, "채팅 내역 수신");
          this.messages = response.data;
          this.connect(this.sessionId);
        });
    });
    // 소켓 연결 시작
    // this.connect(this.sessionId);
  },
  methods: {
    sendMessage: function(e) {
      if (e.keyCode === 13 && this.userName !== "" && this.message !== "") {
        // 이벤트 발생 엔터키 + 유효성 검사는 여기에서
        this.send({ message: this.message }); // 전송 실패 감지는 어떻게? 프론트단에서 고민좀 부탁 dream
      }
      this.message = "";
    },
    send() {
      console.log("Send message:" + this.message);
      if (this.stompClient && this.stompClient.connected) {
        const msg = {
          message: this.message, // 메세지 내용. type이 MSG인 경우를 제외하곤 비워두고 프론트단에서만 처리.
          fk_author_idx: 1, // 작성자의 회원 idx
          created: "", // 작성시간, 공란으로 비워서 메세지 보내기. response에는 담겨옵니다.
          deleted: false, // 삭제된 메세지 여부. default = false
          fk_session_id: this.sessionId, // 현재 채팅세션의 id.
          // 주의할 점은, 방 세션 id가 아닌, 방 정보의 pk_idx를 첨부한다. created 라이프사이클 메서드 참조.
          type: "MSG", // 메세지 타입.
        };
        this.stompClient.send("/receive/" + this.sessionId, JSON.stringify(msg), {});
      }
    },
    connect: function(sessionId) {
      const serverURL = "http://localhost:8088/temp/chat"; // 서버 채팅 주소
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`connecting to socket=> ${serverURL}`);
      this.stompClient.connect(
        {},
        (frame) => {
          this.connected = true;
          console.log("status : established", frame);
          // 구독 == 채팅방 입장.
          this.stompClient.subscribe("/send/" + sessionId, (res) => {
            console.log("receive from server:", res.body);
            this.messages.push(JSON.parse(res.body)); // 수신받은 메세지 표시하기
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
          console.log("status : failed", error);
          this.connected = false;
        }
      );
    },
  },
  mounted() {},
};
</script>
