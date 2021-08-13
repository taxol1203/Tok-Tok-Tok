<template lang="">
  <div id="container" height="750px">
    <el-row class="list-menu">
      <el-col
        @click="listMenuSelect('LIVE')"
        class="list-menu-item"
        :class="{ activeMenu: status == 'LIVE' }"
        :span="8"
      >
        <el-badge :value="count[0]" class="item">
          <span>진행중</span>
        </el-badge>
      </el-col>
      <el-col
        @click="listMenuSelect('OPEN')"
        class="list-menu-item"
        :class="{ activeMenu: status == 'OPEN' }"
        :span="8"
      >
        <el-badge :value="count[1]" class="badge">
          <span>대기중</span>
        </el-badge>
      </el-col>
      <el-col
        @click="listMenuSelect('END')"
        class="list-menu-item"
        :class="{ activeMenu: status == 'END' }"
        :span="8"
        ><el-badge :value="count[2]" class="item">
          <span>종료</span>
        </el-badge></el-col
      >
    </el-row>
    <div>
      <!-- {{ listStatus }} -->
      <el-scrollbar height="700px">
        <div v-for="room in listStatus" :key="room.session.session_id">
          <el-card
            @click="pickRoom(room.session.session_id)"
            class="list-item box-card"
            :class="{ selected: room.session.session_id == selectedSession }"
          >
            <ChatItem :room="room" />
          </el-card>
        </div>
      </el-scrollbar>
    </div>
  </div>
</template>
<script>
import Stomp from 'webstomp-client';
import SockJS from 'sockjs-client';
import ChatItem from './ChatItem.vue';
import { useStore } from 'vuex';
import { computed, ref, onMounted } from 'vue';

export default {
  components: {
    ChatItem,
  },
  setup() {
    const store = useStore();
    const listStatus = computed(() => store.getters.get_room_list);
    const status = computed(() => store.state.list_status);
    const selectedSession = ref('');
    const count = computed(() => store.getters['countGetter']);
    const stompClient = computed(() => store.getters['stompGetter']);
    const sessionId = computed(() => store.getters['get_selected_idx']);
    const chatStatus = computed(() => store.getters['statusGetter']);
    let connected = false;

    const listMenuSelect = (key) => {
      store.commit('STATUS_CHANGE', key);
    };
    onMounted(() => {
      store.dispatch('getChatRooms');
      connect();
    });
    const newChat = () => {
      store.dispatch('createChatRooms');
    };
    // 클릭한 채팅방의 세션id를 state에 저장
    const pickRoom = (key) => {
      store.commit('PICK_ROOM', key);
      selectedSession.value = key;
    };

    const connect = () => {
      // console.log(sessionId.value);
      const serverURL = 'https://i5d204.p.ssafy.io/api/chat'; // 서버 채팅 주소
      let socket = new SockJS(serverURL);
      store.commit('stompSetter', Stomp.over(socket));
      console.log(`connecting to socket=> ${serverURL}`);
      stompClient.value.connect(
        {},
        (frame) => {
          connected = true;
          // 구독 == 채팅방 입장.
          stompClient.value.subscribe('/send/admin', (res) => {
            // console.log('receive from server:', JSON.parse(res.body).type);
            const msg = JSON.parse(res.body);
            switch (msg.type) {
              case 'MSG':
                if (msg.fk_session_id != sessionId.value) break;
                if (chatStatus.value == 'OPEN') store.dispatch('enterRoom', msg);
                else store.commit('MESSAGE_PUSH', msg); // 수신받은 메세지 표시하기
                break;
              case 'JOIN':
                console.log('누군가 대기 중입니다...');
                store.dispatch('getChatRooms');
                // 방을 생성할 때 백엔드단에서 처리하므로 신경 x
                break;
              case 'END':
                store.dispatch('chatClose', msg.fk_session_id);
                // 만약 둘 중 하나가 나가면 더 이상 채팅을 못치는 프론트구현
                break;
              case 'VID':
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
          // console.log('status : failed, STOMP CLIENT 연결 실패', error);
          connected = false;
        }
      );
    };

    return {
      store,
      listStatus,
      status,
      count,
      selectedSession,
      newChat,
      pickRoom,
      listMenuSelect,
      connect,
      connected,
    };
  },
};
</script>
<style>
.list-item {
  height: 100px;
  background-color: #f7f4f0;
  border: 1px solid #eee;
}
.list-item:hover {
  filter: brightness(105%);
}
.selected {
  /* filter: brightness(105%); */
  background-color: #fff;
  border-right: 0px;
}
#container {
  background: #f7f4f0;
  cursor: pointer;
  /* border: 2px solid purple; */
}
.list-menu {
  /* background-color: #eee; */
  height: 50px;
  background-color: rgb(254, 254, 254);
  border-bottom: 1px solid rgb(230, 236, 231);
  /* border: 2px solid red; */
  display: flex;
  text-align: center;
  align-content: center;
  font-size: 1.1rem;
  box-sizing: border-box;
}
.list-menu-item:hover {
  /* filter: brightness(150%); */
  background-color: #f7f4f0;
}
.list-menu-item {
  height: 100%;
  font-size: 1.1rem;
  box-sizing: border-box;
  display: inherit;
  text-align: center;
  justify-content: center;
  align-items: center;
}
.activeMenu {
  background-color: #f7f4f0;
}
</style>
