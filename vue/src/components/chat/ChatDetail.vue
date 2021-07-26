<template>
  <div class="border-solid chat-detail grid-content">
    <el-row :gutter="12">
      <!-- 채팅창 부분+녹색 -->
      <el-col :span="24" :offset="0"> [Chat Detail]</el-col>
    </el-row>
    <!-- 상대방 -->
    <div v-for="(msg, index) in messages" :key="index">
      <el-row :gutter="12">
        <el-col :span="20" :offset="4" v-if="msg.fk_author_idx == '1'">
          <div class="message-me border-solid">
            {{ msg.message }}
          </div>
        </el-col>
        <el-col :span="20" :offset="0" v-else>
          <div class="message-other border-solid">{{ msg.message }}</div>
        </el-col>
      </el-row>
    </div>

    <el-row>
      <el-col :span="20" :offset="0">
        <div class="user-name border-solid">상담사 사진(기본 사진) | 상담사 이름</div>
      </el-col>
      <el-col :span="20" :offset="0">
        <div class="message-other border-solid">Other Message</div>
      </el-col>
      <el-col :span="20" :offset="4">
        <div class="message-me border-solid">My Message</div>
      </el-col>
      <!-- 입력창 -->
      <el-col :span="3" :offset="0">
        <el-button type="primary" icon="el-icon-video-camera" class="icon-m-p"></el-button>
      </el-col>
      <el-col :span="18" :offset="0">
        <div class="border-solid">
          <input type="text" @keyup.enter="sendMessage" v-model="message" />
        </div>
      </el-col>
      <el-col :span="3" :offset="0">
        <el-button
          @click="sendMessage"
          icon="el-icon-s-promotion"
          class="icon-m-p"
          plain
        ></el-button>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import axios from "axios";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

export default {
  name: "Chat",
  components: {},
  data: function () {
    return {
      sessionId: "",
      roomName: "",
      messages: [],
      message: "",
      session_pk: 0,
    };
  },
  created() {
    this.sessionId = "d6142966-8ac7-42be-a774-4be4f62a3940"; // 일단 임시로 고정된 값 사용
    // this.sessionId = this.$route.params.id; // path parameter로 방 id 전송함. 만약 url에 노출되는게 별로면 props로 전달하는 걸로 변경하셔도...
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
    //버튼을 누르거나, 채팅창 엔터 누르면 method 실행
    sendMessage: function () {
      if (this.userName !== "" && this.message !== "") {
        console.log("INNER EVENT!");
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
    connect: function (sessionId) {
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
<style>
.border-solid {
  border: solid 1px;
  border-radius: 4px;
  box-shadow: 0 2px 24px 0 rgba(0, 0, 0, 0.1);
  padding: 1px;
  margin: 5px 1px 5px 1px;
}
.user-name {
  padding: 1px 0px 0px 0px;
  margin: 15px 0px 0px 0px;
}

.chat-detail {
  color: white;
  background-color: rgb(39, 37, 31);
  font-family: "BMJUA";
}

.message-me {
  background-color: #258c60;
}
.message-other {
  background-color: #258c60;
}

.defualt-m-p {
  padding: 1px;
  margin: 5px 1px 5px 1px;
}
.icon-m-p {
  padding: 0px 10px 0px 10px;
  margin: 0px 5px 0px 5px;
}
</style>
