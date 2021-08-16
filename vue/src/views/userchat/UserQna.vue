<template>
  <div style="position: relative; padding: 10px">
    <!-- 상대방 -->
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
      <user-chat-detail v-if="sessionId" />
      <p v-if="realChat == 'END'">상담이 종료되었습니다.</p>
    </el-scrollbar>
    <!-- 입력창시작 -->
    <el-row id="bottomInput" v-if="realChat == 'LIVE'">
      <el-col :span="14">
        <div>
          <el-input
            type="text"
            @keyup.enter="sendMessage"
            v-model="userMsg"
            placeholder="Please input"
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
    <!-- 입력창 끝 -->
  </div>
</template>
<script>
import Stomp from 'webstomp-client';
import SockJS from 'sockjs-client';
import { useStore } from 'vuex';
import { ref, computed, watch, onMounted } from 'vue';
import UserChatDetail from './UserChatDetail.vue';

export default {
  name: 'Chat',
  components: { UserChatDetail },
  setup() {
    const store = useStore();
    const userMsg = ref('');
    const scrollbar = ref('');
    const closeMsg = computed(() => store.getters['closeMsgGetter']);
    const log = computed(() => store.getters['userQna/logGetter']);
    store.commit('userQna/CHANGE_SELECT', 1);
    store.commit('userQna/SET_CURRENT');
    let history = '';

    const chooseAnswer = (next_idx, value) => {
      if (history == '') history += value;
      else history += '|' + value;
      store.commit('userQna/CHANGE_SELECT', next_idx);
      store.commit('userQna/ADD_LOG');
      setTimeout(() => {
        scrollbar.value.setScrollTop(scrollbar.value.scrollHeight);
      }, 150);
    };

    onMounted(() => {
      scrollbar.value.setScrollTop(scrollbar.value.scrollHeight);
    });
    const user_pk_idx = computed(() => store.state.auth.user.pk_idx);
    const realChat = computed(() => store.getters['get_user_room_status']);
    const sessionId = computed(() => store.getters['get_selected_idx']);
    const isHidden = computed(() => store.getters['userQna/showUserChat']);
    let stompClient = computed(() => store.getters['stompGetter']);
    let connected = ref(false);

    const createChatRoom = () => {
      console.log(user_pk_idx.value);
      send('JOIN');
      store.dispatch('createChatRooms', history);
      console.log(sessionId.value);
    };
    watch(sessionId, () => {
      connect();
    });
    watch(connected, () => {
      console.log('join');
      send('JOIN');
    });

    const connect = () => {
      const serverURL = 'https://i5d204.p.ssafy.io/api/chat'; // 서버 채팅 주소
      let socket = new SockJS(serverURL);
      store.commit('stompSetter', Stomp.over(socket));
      // stompClient = Stomp.over(socket);
      stompClient.value.connect(
        {},
        (frame) => {
          connected.value = true;
          console.log('CONNECT SUCCESS ++ status : established', frame);
          // 구독 == 채팅방 입장.
          stompClient.value.subscribe('/send/' + sessionId.value, (res) => {
            console.log('receive from server:', res.body);
            switch (JSON.parse(res.body).type) {
              case 'MSG':
                store.commit('USER_MSG_PUSH', JSON.parse(res.body)); // 수신받은 메세지 표시하기
                setTimeout(() => {
                  scrollbar.value.setScrollTop(scrollbar.value.scrollHeight);
                }, 150);
                break;
              case 'JOIN':
                // 방을 생성할 때 백엔드단에서 처리하므로 신경 x
                break;
              case 'END':
                store.commit('CLOSE_MSG');
                // 만약 둘 중 하나가 나가면 더 이상 채팅을 못치는 프론트구현
                break;
              case 'VID':
                store.commit('USER_MSG_PUSH', JSON.parse(res.body));
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
          connected.value = false;
        }
      );
    };

    const sendMessage = () => {
      if (user_pk_idx.value !== '' && userMsg.value !== '') {
        // 이벤트 발생 엔터키 + 유효성 검사는 여기에서
        send('MSG'); // 전송 실패 감지는 어떻게? 프론트단에서 고민좀 부탁 dream
      }
      userMsg.value = '';
    };

    const send = (type) => {
      console.log('Send message:' + userMsg.value);
      console.log(sessionId.value);
      if (user_pk_idx.value <= 0) {
        console.log('0이하면 안됨) fk_author_idx: ' + user_pk_idx.value);
      }
      //DB에 없는 유저 idx(0같은 것)가 들어가면 안된다.
      if (stompClient.value && stompClient.value.connected && user_pk_idx.value > 0) {
        console.log('IN SOCKET');
        const msg = {
          message: userMsg.value, // 메세지 내용. type이 MSG인 경우를 제외하곤 비워두고 프론트단에서만 처리.
          fk_author_idx: user_pk_idx.value, // 작성자의 회원 idx
          created: '', // 작성시간, 공란으로 비워서 메세지 보내기. response에는 담겨옵니다.
          deleted: false, // 삭제된 메세지 여부. default = false
          fk_session_id: sessionId.value, // 현재 채팅세션의 id.
          // 주의할 점은, 방 세션 id가 아닌, 방 정보의 pk_idx를 첨부한다. created 라이프사이클 메서드 참조.
          type: type, // 메세지 타입.
        };
        stompClient.value.send('/receive/' + sessionId.value, JSON.stringify(msg), {});
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
      connect,
      createChatRoom,
      chooseAnswer,
      sendMessage,
      send,
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
  height: 550px;
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
