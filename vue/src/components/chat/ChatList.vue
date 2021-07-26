<template lang="">
  <div>
    [ChatList]
     <el-button type="primary" @click="getUser">temp</el-button>
    <el-scrollbar height="400px">
      <el-card v-for="(room, index) in chatRooms"
          :key="index" class="list-item box-card" style="height: 80px;">
        <ChatItem :room="room" />
      </el-card>
    </el-scrollbar>
  </div>
</template>
<script>
import ChatItem from "./ChatItem.vue"
import axios from "axios"
import { reactive } from 'vue'
// import { useStore } from 'vuex'

export default {
  components: {
    ChatItem,
  },
  props: ['test'],
  setup() {
    const chatRooms = reactive([])
    // const addOne = async () => {
    //   try {
    //     const res = await axios.post('')
    //   }      
    // }
    const getUser = async () => {
      try {
        const res = await axios.get('https://reqres.in/api/users/2')
        console.log(res.data.data.last_name)
        chatRooms.push(res.data.data)
        console.log(chatRooms)
      } catch (error) {
        console.log(error)
      }
    }
    // 여기서 나와 연결된 모든 채팅방을 받아와야함
    return {
      getUser,
      chatRooms,
    }
  },
  data: function () {
    return {
      asdf: "asdf",
    }
  },
};
</script>
<style lang="">
</style>
