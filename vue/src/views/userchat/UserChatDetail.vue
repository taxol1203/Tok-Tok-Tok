<template>
  <div v-if="isOpen == 'OPEN'">
    <!-- <p>현재 모든 상담사가 상담 중입니다. 잠시만 기다려주세요.</p> -->
    <div v-loading="loading"></div>
    <i class="el-icon-loading" style="margin-right: 3px"></i>
    <!-- <i class="el-icon-loading"></i>
    <p>이거 기다리는거 디자인 물어보기</p> -->
  </div>
  <div v-if="isOpen == 'LIVE'">LIVE</div>
  <!-- 실시간채팅 시작 -->
  <div v-for="(msg, index) in messages" :key="index">
    <el-row>
      <el-col v-if="msg.fk_author_idx == userPkidx">
        <div class="message-me">
          {{ msg.message }}
        </div>
      </el-col>
      <el-col v-else>
        <div class="message-other">{{ msg.message }}</div>
      </el-col>
    </el-row>
  </div>
  <!-- 실시간채팅 끝 -->
</template>
<script>
import Stomp from 'webstomp-client';
import SockJS from 'sockjs-client';
import { useStore } from 'vuex';
import { ref, computed } from 'vue';

export default {
  name: 'Chat',
  components: {},
  setup() {
    const store = useStore();
    const sessionId = computed(() => store.state.selected_room); //user가 생성한 방 id
    const userPkidx = computed(() => store.state.auth.user.pk_idx);
    const message = ref('');
    const messages = computed(() => store.getters['get_user_messages']);
    const isOpen = computed(() => store.getters['get_user_room_status']);
    const loading = true;

    let connected = false;
    let stompClient = '';

    const connect = () => {
      const serverURL = 'https://i5d204.p.ssafy.io/api/chat'; // 서버 채팅 주소
      let socket = new SockJS(serverURL);
      stompClient = Stomp.over(socket);
      stompClient.connect(
        {},
        (frame) => {
          connected = true;
          console.log('CONNECT SUCCESS ++ status : established', frame);
          // 구독 == 채팅방 입장.
          stompClient.subscribe('/send/' + sessionId.value, (res) => {
            console.log('receive from server:', res.body);
            store.commit('USER_MSG_PUSH', JSON.parse(res.body)); // 수신받은 메세지 표시하기
            switch (res.body.type) {
              case 'MSG':
                break;
              case 'JOIN':
                // 방을 생성할 때 백엔드단에서 처리하므로 신경 x
                break;
              case 'QUIT':
                // 만약 둘 중 하나가 나가면 더 이상 채팅을 못치는 프론트구현
                break;
              case 'VID':
                // vid 시작시 -> 화상채팅 시작하기 버튼만 딸랑 띄우기
                break;
              default:
                // 알수없는 오류... 이거나 메시지가 하나도 없는 경우...
                break;
            }
          });
        },
        (error) => {
          // 소켓 연결 실패
          console.log('status : failed, STOMP CLIENT 연결 실패', error);
          connected = false;
        }
      );
    };
    connect(sessionId.value);
    const sendMessage = () => {
      if (userPkidx.value !== '' && message.value !== '') {
        // 이벤트 발생 엔터키 + 유효성 검사는 여기에서
        send({ message: message }); // 전송 실패 감지는 어떻게? 프론트단에서 고민좀 부탁 dream
      }
      message.value = '';
    };

    const send = () => {
      console.log('Send message:' + message.value);
      if (userPkidx.value <= 0) {
        console.log('0이하면 안됨) fk_author_idx: ' + userPkidx.value);
      }
      //DB에 없는 유저 idx(0같은 것)가 들어가면 안된다.
      if (stompClient && stompClient.connected && userPkidx.value > 0) {
        console.log('IN SOCKET');
        const msg = {
          message: message.value, // 메세지 내용. type이 MSG인 경우를 제외하곤 비워두고 프론트단에서만 처리.
          fk_author_idx: userPkidx.value, // 작성자의 회원 idx
          created: '', // 작성시간, 공란으로 비워서 메세지 보내기. response에는 담겨옵니다.
          deleted: false, // 삭제된 메세지 여부. default = false
          fk_session_id: sessionId.value, // 현재 채팅세션의 id.
          // 주의할 점은, 방 세션 id가 아닌, 방 정보의 pk_idx를 첨부한다. created 라이프사이클 메서드 참조.
          type: 'MSG', // 메세지 타입.
        };
        stompClient.send('/receive/' + sessionId.value, JSON.stringify(msg), {});
      }
    };

    return {
      store,
      sessionId,
      messages,
      message,
      sendMessage,
      send,
      connect,
      connected,
      stompClient,
      userPkidx,
      isOpen,
      loading,
    };
  },
};
</script>
<style scoped>
#topMessages {
  display: block;
  top: 0px;
  height: 21rem;
  width: 100%;
}
#bottomInput {
  bottom: 0px;
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
