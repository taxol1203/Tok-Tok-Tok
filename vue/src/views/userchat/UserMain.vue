<template>
  <el-row :gutter="20">
    <div id="UserMain">
      <div class="fab-container">
        <transition class="same-pos" name="fade" mode="out-in">
          <el-button
            class="big-btn"
            type="primary"
            icon="el-icon-plus"
            @click="changeCondition"
            v-if="!isHidden"
            circle
          ></el-button>
        </transition>
        <transition class="same-pos" name="fade" mode="out-in">
          <div id="chat-box" v-if="isHidden">
            <el-card :body-style="{ padding: '0px' }" id="chat-card">
              <div id="card-head" class="card-header">
                <div>
                  <!-- 이유는 모르겠지만 이 요소가 없으면 버튼이 우측 정렬이 안 됨 -->
                </div>
                <div style="float: right">
                  <i @click="changeCondition" class="el-icon-close" id="close-btn"></i>
                </div>
              </div>
              <div class="full-box">
                <UserQna :close="isHidden" />
              </div>
            </el-card>
          </div>
        </transition>
      </div>
    </div>
  </el-row>
</template>
<script>
import Stomp from 'webstomp-client';
import SockJS from 'sockjs-client';
import { useStore } from 'vuex';
import UserChatDetail from './UserChatDetail.vue';
import UserQna from './UserQna.vue';
import ChatDetail from '../../components/chat/ChatDetail.vue';
import { computed, onMounted, ref } from 'vue';
/* eslint-disable */
export default {
  components: {
    UserQna,
    UserChatDetail,
    ChatDetail,
  },
  setup() {
    let isChatExist = ref(false);
    const isHidden = computed(() => store.getters['userQna/showUserChat']);
    const store = useStore();
    const user_pk_idx = computed(() => store.state.auth.user.pk_idx);
    const sessionId = computed(() => store.getters['get_selected_idx']);
    onMounted(() => {
      //state.userQna.scenes에 pk_idx별로 예상질문정보+answers에 정답정보 저장
      store.dispatch('userQna/init');
    });
    let changeCondition = () => {
      if (isHidden.value === true) connect();
      store.commit('userQna/CHANGE_STATE');
    };
    let changeisChatExist = () => {
      isChatExist.value = !isChatExist.value;
    };
    // 유저의 채팅방 개설요청
    let createChatRoom = () => {
      store.dispatch('createChatRooms', user_pk_idx.value);
      isChatExist.value = true;
    };
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
            switch (JSON.parse(res.body).type) {
              case 'MSG':
                store.commit('USER_MSG_PUSH', JSON.parse(res.body)); // 수신받은 메세지 표시하기
                setTimeout(() => {
                  scrollbar.value.setScrollTop(999999999999999999999);
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
    return {
      store,
      sessionId,
      user_pk_idx,
      isChatExist,
      isHidden,
      connected,
      stompClient,
      changeisChatExist,
      changeCondition,
      createChatRoom,
      connect,
    };
  },
};
</script>
<style scoped>
#UserMain {
  position: fixed;
  width: 100%;
  height: 100%;
  background-image: url('../../assets/Microsoft.png');
  background-repeat: no-repeat;
  background-position: center;
}

.fab-container {
  position: fixed;
  bottom: 50px;
  right: 50px;
  z-index: 999;
  cursor: pointer;
}

#chat-box {
  width: 25rem; /* 1rem = 16px */
  height: 45rem; /* 720px */
  padding: 10px;
  position: sticky;
  background-color: #09c7fb;
  background-image: linear-gradient(315deg, #09c7fb 0%, #93fb9d 74%);
  border-radius: 2rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.24), 0 0 6px rgba(0, 0, 0, 0.08);
}

.big-btn {
  width: 80px;
  height: 80px;
  /* icon 사이즈 키우는 법 */
  font-size: 2rem;
  background-color: #09c7fb;
  background-image: linear-gradient(315deg, #09c7fb 0%, #93fb9d 74%);
  border: none;
}

#close-btn {
  font-size: 3rem;
  color: white;
  font-style: bold;
  color: #006f3e;
}

/* 위치 고정을 시키지 않으면 렌더링하면서 (생명&소멸) 서로 다른 공간에 보여짐 */
.same-pos {
  position: fixed;
  bottom: 100px;
  right: 100px;
}

.full-box {
  box-sizing: border-box;
  width: 100%;
  height: auto;
  padding: 14px;
  position: relative;
}

#chat-card {
  border-radius: 1rem;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.5);
  border: none;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  /* background-color: #006f3e; */
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

/* 생성 부분 */
.fade-enter-from {
  opacity: 0;
}
.fade-enter-to {
  opacity: 1;
}
.fade-enter-active {
  transition: all 0.3s ease-out;
}
/* 소멸 부분 */
.fade-leave-from {
  opacity: 1;
}
.fade-leave-to {
  opacity: 0;
}
.fade-leave-active {
  transition: all 0.5s ease-out;
}
</style>
