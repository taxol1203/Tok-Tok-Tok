<template lang="">
  <div id="container" height="750px">
    <el-row class="list-menu">
      <el-col
        @click="listMenuSelect('LIVE')"
        class="list-menu-item"
        :class="{ activeMenu: status == 'LIVE' }"
        :span="8"
        >진행중</el-col
      >
      <el-col
        @click="listMenuSelect('OPEN')"
        class="list-menu-item"
        :class="{ activeMenu: status == 'OPEN' }"
        :span="8"
      >
        <el-badge :value="waitingCnt" class="item">
          <span>대기중</span>
        </el-badge>
      </el-col>
      <el-col
        @click="listMenuSelect('END')"
        class="list-menu-item"
        :class="{ activeMenu: status == 'END' }"
        :span="8"
        >종료</el-col
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
import ChatItem from './ChatItem.vue';
import { useStore } from 'vuex';
import { computed, onMounted, ref } from 'vue';

export default {
  components: {
    ChatItem,
  },
  setup() {
    const store = useStore();
    const listStatus = computed(() => store.getters.get_room_list);
    const status = computed(() => store.state.list_status);
    const selectedSession = ref('');
    const waitingCnt = computed(() => store.getters['waitingGetter']);

    const listMenuSelect = (key) => {
      store.commit('STATUS_CHANGE', key);
    };
    onMounted(() => {
      store.dispatch('getChatRooms');
    });
    const newChat = () => {
      store.dispatch('createChatRooms');
    };
    // 클릭한 채팅방의 세션id를 state에 저장
    const pickRoom = (key) => {
      store.commit('PICK_ROOM', key);
      selectedSession.value = key;
    };
    return {
      newChat,
      store,
      pickRoom,
      listStatus,
      listMenuSelect,
      status,
      waitingCnt,
      selectedSession,
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
