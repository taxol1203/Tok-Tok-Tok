<template lang="">
  <div>
    [ChatList]
    <el-button type="primary" @click="newChat">생성</el-button>
    <el-scrollbar height="400px">
      <div v-for="room in store.state.rooms" :key="room.session_id">
        <el-card 
        @dblclick="pickRoom(room.session_id)"
        class="list-item box-card">
          <ChatItem :room="room" />
        </el-card>
      </div>
    </el-scrollbar>
  </div>
</template>
<script>
import ChatItem from "./ChatItem.vue"
import { useStore } from 'vuex'
// import { computed } from 'vue'

export default {
  components: {
    ChatItem,
  },
  setup() {
    const store = useStore()
    store.dispatch('getChatRooms')
    const newChat = () => {
      store.dispatch('createChatRooms')
    }
    // 더블클릭한 채팅방의 세션id를 state에 저장
    const pickRoom = (key) => {
      store.dispatch('pickRoom', key)
    }
    return {
      newChat,
      store,
      pickRoom,
    }
  },
};
</script>
<style>
.list-item {
  height: 100px;
  background-color: whitesmoke;
}
</style>
