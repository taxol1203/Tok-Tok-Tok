<template>
  <div v-cloak>
    {{ sessionId }}
    <div id="app" v-cloak>
      <div>
        <h2>{{ roomName }}</h2>
        <button @click="rerender()">56</button>
        <ul>
          <li v-for="(chat, index) in get_chats" :key="index">
            <hr>
            <button @click="modifyChatStatus(index, 'LIVE')">상담 맡기</button>
            <!-- 위 버튼에 v-if조건으로 fk_host_idx가 0이면 버튼 보여주기 -->
            <button @click="modifyChatStatus(index, 'END')">상담종료</button>
            <!-- 위 버튼에 v-if조건으로 현재 로그인한 상담사와 fk_host_idx가 일치하면 버튼 보여주기 -->
            <h3>
              {{ chat.session.fk_host_idx}} : {{ chat.session.status }} : {{ index }}
            </h3>
            <ul v-for="(message, index2) in chat.messages" :key="index2">
              {{
                message.message
              }}
            </ul>
            <hr>
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
      sessionId: "happy",
      roomName: "",
      chats: {},
      message: "",
      render: true,
    };
  },
  computed: {
    get_chats: {
      get() {
        return this.chats;
      },
      set(newMsg) {
        if (newMsg !== {} && newMsg !== undefined && newMsg !== null) {
          console.log(newMsg);
          let s_id = newMsg["fk_session_id"];
          console.log(s_id);
          console.log(newMsg.fk_session_id);
          console.log(this.chats);
          console.log(this.chats[s_id]);
          // console.log(this.chats[s_id].messages.push(newMsg));
          // let msg = JSON.parse(res.body);
          this.chats[s_id].messages.push(newMsg);
        }
      },
    },
  },
  created() {
    this.init();
    console.log(this.chats);

    // 소켓 연결 시작
  },
  methods: {
    init: function(){
    axios
      .get("http://localhost:8088/temp/api/chat/admin/init", {})
      .then((res) => {
        console.log(res);
        this.chats = res.data;
        // {
        //   "다른세션아이디": {
        //       "session": {
        //           //...
        //       },
        //       "messages": []
        //   },
        //   "b8ba83b5-a540-4818-8f96-b4e301a4c7f1": {
        //       "session": {
        //           "pk_idx": null,
        //           "session_id": "b8ba83b5-a540-4818-8f96-b4e301a4c7f1",
        //           "created_at": "2021-07-21 11:15:13.3125+09",
        //           "fk_created_by_idx": 1, // 방 개설자. 지금 구현에선 fk_client_idx와 같음.
        //           "fk_client_idx": 1, // 고객의 pk_idx
        //           "fk_host_idx": 0, // 상담사의 pk_idx. 어드민에서 fK_host_idx == 0일 경우 채팅창 위에 상담 담당하기 버튼이 생성되어 누르면 자기자신이 배정할 수 있도록
        //           "fk_permission_idx": 0, // 지금은 신경쓰지 말 것
        //           "status": "OPEN", // modifyChatStauts() 메서드 참조하기.
        //           "unread": 0
        //       },
        //       "messages": [
        //           {
        //               "pk_idx": 1,
        //               "message": "*** 회원님이 입장하였습니다.",
        //               "fk_author_idx": 1,
        //               "created": "2021-07-21 11:15:16.652515+09",
        //               "deleted": false,
        //               "fk_session_id": "b8ba83b5-a540-4818-8f96-b4e301a4c7f1",
        //               "type": "JOIN"
        //           },
        // {
        //   //다른 메세지
        // }
        //       ]
        //   }
        // }
        this.connect(this.sessionId);
        // 데이터를 다 수신하고 connect 실시
      });
    },
    modifyChatStatus: function(session_id,to){
      axios
      .put("http://localhost:8088/temp/api/chat/room/"+session_id, {
        status : to, 
        /* 
        OPEN -> 상담이 열렸지만 상담사 배정 x
        END -> 상담 종료. 상담사 혹은 고객이 종료버튼을 누르면 종료됨
        LIVE -> 상담사가 배정되어있음.
        */
      })
      .then(() => {
        this.init();
        // 종료 혹은 상담 개시에 따른 메세지 전송하는 코드 여기에 작성.
      })
      .catch(() => {
        alert("실패");
      })
      ;
    },
    rerender: function() {
      this.render = false;
      this.render = true;
      this.$forceUpdate();
      console.log("!");
      console.log(this.chats);
    },
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
          pk_idx: "", // 메세지 보낼 때 공란으로 비워두기, db용 메세지 index
          message: this.message, // 메세지 내용
          fk_author_idx: "", // 작성자의 회원 idx
          created: "", // 작성시간, 공란으로 비워서 메세지 보내기. response에는 담겨옵니다.
          deleted: false, // 삭제된 메세지 여부. default = false
          fk_session_idx: this.sessionId, // 현재 보고 있는 채팅방의 세션아이디
          type: "MSG", // 메세지 타입.
        };
        this.stompClient.send(
          "/receive/" + this.sessionId,
          JSON.stringify(msg),
          {}
        );
      }
    },
    connect: function() {
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
          this.stompClient.subscribe("/send/admin", (res) => {
            // /send/admin -> 모든 메세지를 수신함
            console.log("receive from server:", res.body);
            let msg = JSON.parse(res.body);
            this.get_chats = msg;
            switch (res.body.type) {
              case "MSG":
                break;
              case "JOIN":
                break;
              case "QUIT":
                break;
              case "VID":
                break;
              default:
                // 알수없는 오류...
                break;
            }
          });
        },
        (error) => {
          // 소켓 연결 실패. 프론트단에서 어떻게 처리할 지 생각해보세요!
          console.log("status : failed", error);
          this.connected = false;
        }
      );
    },
  },
  mounted() {},
};
</script>
