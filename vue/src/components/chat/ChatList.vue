<template lang="">
  <el-tabs type="card" v-model="status" @tab-click="handleClick" style="margin-top: 10px;">
    <el-tab-pane label="진행" name="LIVE">
      <template #label>진행<el-badge :value="count[0]" class="badge"/></template>
    </el-tab-pane>
    <el-tab-pane label="대기" name="OPEN">
      <template #label>대기<el-badge :value="count[1]" class="badge"/></template>
    </el-tab-pane>
    <el-tab-pane label="종료" name="END">
      <template #label>종료<el-badge :value="count[2]" class="badge"/></template>
    </el-tab-pane>
  </el-tabs>
  <el-scrollbar style="max-height: 87vh;">
    <div v-for="room in listStatus" :key="room.session.session_id" class="noborder">
      <div
        @click="pickRoom(room.session.session_id)"
        class="list-item box-card"
        :class="{ selected: room.session.session_id == selectedSession }"
      >
        <ChatItem :room="room" />
      </div>
    </div>
  </el-scrollbar>
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
    const selectedSession = ref('');
    const count = computed(() => store.getters['countGetter']);
    const stompClient = computed(() => store.getters['stompGetter']);
    const sessionId = computed(() => store.getters['get_selected_idx']);
    const chatStatus = computed(() => store.getters['statusGetter']);
    const status = computed(() => store.getters['listStatusGetter']);
    const scrollbar = computed(() => store.getters['scrollbarGetter']);
    let connected = false;

    const listMenuSelect = (key) => {
      console.log(key);
      store.commit('STATUS_CHANGE', key);
    };
    onMounted(() => {
      store.dispatch('getChatRooms');
      connect();
    });
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
                setTimeout(() => {
                  scrollbar.value.setScrollTop(999999999999999999999);
                }, 150);
                break;
              case 'JOIN':
                store.dispatch('getChatRooms');
                break;
              case 'END':
                store.dispatch('chatClose', msg.fk_session_id);
                break;
              case 'VID':
                store.commit('MESSAGE_PUSH', msg);
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
    const handleClick = (tab, event) => {
      console.log(tab, event);
      console.log(tab.props.name);
      listMenuSelect(tab.props.name);
    };
    return {
      listStatus,
      selectedSession,
      count,
      stompClient,
      sessionId,
      chatStatus,
      status,
      scrollbar,
      connected,
      listMenuSelect,
      pickRoom,
      connect,
      handleClick,
    };
  },
};
</script>
<style>
/* .el-tabs{
  width: 400px;
} */
.noborder {
  border: 0px solid;
}
.list-item {
  height: 60px;
  background-color: #fff;
  padding: 20px;
  text-align: left;
  /* background-color: #f7f4f0; */
  /* border: 1px solid #eee; */
}
.list-item:hover {
  /* background-color: #f7f4f0; */
  filter: brightness(95%);
}
.selected {
  background-color: '#006f3e';
  filter: brightness(95%);
  /* background-color: #fff; */
  border-right: 0px;
}
.list-menu {
  /* background-color: #eee; */
  height: 50px;
  background-color: rgb(254, 254, 254);
  /* border-bottom: 1px solid rgb(230, 236, 231); */
  /* border: 2px solid red; */
  display: flex;
  text-align: center;
  align-content: center;
  font-size: 1.1rem;
  box-sizing: border-box;
}
.list-menu-item:hover {
  /* filter: brightness(150%); */
  background-color: #fff;
  /* border: 1px solid #000; */
  border-radius: 15px 15px 0px 0px;
}
.list-menu-item {
  height: 100%;
  font-size: 1.1rem;
  box-sizing: border-box;
  display: inherit;
  text-align: center;
  justify-content: center;
  align-items: center;
  border-radius: 15px 15px 0px 0px;
}
.activeMenu {
  /* border: 1px solid #000; */
  border-bottom: 0px solid;
  background-color: #fff;
}
.badge sup {
  background-color: #006f3e;
}
.badge {
  padding: 3px;
}
.el-tabs__item:hover {
  color: #006f3e;
}
.el-tabs__item.is-active {
  color: #006f3e;
}
.el-tabs__header {
  margin: 0px;
}
.el-tabs__item {
  font-family: 'Noto Sans', '맑은 고딕', sans-serif;
  font-weight: bold;
  font-size: 0.95rem;
}
</style>
