<template lang="">
  <div id="container" height="750px">
    <el-row class="list-menu">
      <el-col
        @click="listMenuSelect('chatting')"
        class="list-menu-item"
        :class="{ activeMenu: selectedChattingRooms }"
        :span="8"
        >진행중</el-col
      >
      <el-col
        @click="listMenuSelect('OPEN')"
        class="list-menu-item"
        :class="{ activeMenu: selectedWatingRooms }"
        :span="8"
        >대기중</el-col
      >
      <el-col
        @click="listMenuSelect('END')"
        class="list-menu-item"
        :class="{ activeMenu: selectedClosedRooms }"
        :span="8"
        >종료</el-col
      >
    </el-row>
    <div v-show="selectedChattingRooms">
      <el-scrollbar height="700px">
        <div v-for="room in store.state.rooms" :key="room.session_id">
          <el-card @click="pickRoom(room.session_id)" class="list-item box-card">
            <ChatItem :room="room" />
          </el-card>
        </div>
      </el-scrollbar>
    </div>
    <div v-show="selectedWatingRooms">
      <el-scrollbar height="700px"> 대기중 채팅 </el-scrollbar>
    </div>
    <div v-show="selectedClosedRooms">
      <el-scrollbar height="700px"> 종료된 채팅 </el-scrollbar>
    </div>
  </div>
</template>
<script>
import ChatItem from './ChatItem.vue';
import { useStore } from 'vuex';
import { ref } from 'vue';
// import { computed } from 'vue'

export default {
  components: {
    ChatItem,
  },
  setup() {
    const store = useStore();
    const selectedChattingRooms = ref(true);
    const selectedWatingRooms = ref(false);
    const selectedClosedRooms = ref(false);
    // const activeRoom = reactive({});
    const listMenuSelect = (key) => {
      if (key === 'chatting' && selectedChattingRooms.value == false) {
        selectedChattingRooms.value = true;
        selectedWatingRooms.value = false;
        selectedClosedRooms.value = false;
      } else if (key === 'waiting' && selectedWatingRooms.value == false) {
        selectedChattingRooms.value = false;
        selectedWatingRooms.value = true;
        selectedClosedRooms.value = false;
        store.state.selected_room = null;
      } else if (key === 'closed' && selectedClosedRooms.value == false) {
        selectedChattingRooms.value = false;
        selectedWatingRooms.value = false;
        selectedClosedRooms.value = true;
        store.state.selected_room = null;
      }
      console.log(key);
      console.log(selectedClosedRooms.value);
    };
    store.dispatch('getChatRooms');
    const newChat = () => {
      store.dispatch('createChatRooms');
    };
    // 클릭한 채팅방의 세션id를 state에 저장
    // 클릭 한번에 새 창이 열리는 것은 좀 더 알아봐야함.
    const pickRoom = (key) => {
      store.dispatch('pickRoom', key);
      console.log(store.getters.get_messages);
    };
    return {
      newChat,
      store,
      pickRoom,
      selectedWatingRooms,
      selectedChattingRooms,
      listMenuSelect,
      selectedClosedRooms,
      // activeRoom,
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
