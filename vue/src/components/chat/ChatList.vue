<template lang="">
  <div>
    [ChatList]
    <el-button type="primary" @click="newChat">생성</el-button>
    <el-scrollbar height="400px">
      <div v-for="room in store.state.rooms" :key="room.session_id">
        <el-card @click="pickRoom(room.session_id)" class="list-item box-card" style="height: 80px;">
          <ChatItem :room="room" />
        </el-card>
      </div>
    </el-scrollbar>
  </div>
</template>
<script>
import ChatItem from "./ChatItem.vue"
import { useStore } from 'vuex'

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
<style lang="">
</style>
