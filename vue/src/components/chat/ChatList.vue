<template lang="">
  <div id="container" height="750px">
    <el-scrollbar height="700px;">
      <div v-for="room in store.state.rooms" :key="room.session_id">
        <el-card @click="pickRoom(room.session_id)" class="list-item box-card">
          <ChatItem :room="room" />
        </el-card>
      </div>
    </el-scrollbar>
    <el-button
      type="primary"
      style="background-color: #f7f4f0; color: #111; border: 0px; height: 50px"
      @click="newChat"
      >채팅 임시 생성</el-button
    >
  </div>
</template>
<script>
import ChatItem from "./ChatItem.vue";
import { useStore } from "vuex";
// import { computed } from 'vue'

export default {
  components: {
    ChatItem,
  },
  setup() {
    const store = useStore();
    store.dispatch("getChatRooms");
    const newChat = () => {
      store.dispatch("createChatRooms");
    };
    // 클릭한 채팅방의 세션id를 state에 저장
    // 클릭 한번에 새 창이 열리는 것은 좀 더 알아봐야함.
    const pickRoom = (key) => {
      if (store.state.selected_room != null) {
        store.dispatch("pickRoom", null);
      } else {
        store.dispatch("pickRoom", key);
      }
    };
    return {
      newChat,
      store,
      pickRoom,
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
#container {
  background: #f7f4f0;
}
</style>
