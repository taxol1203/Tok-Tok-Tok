<template lang="">
  <div>
    <el-container>
      <el-aside v-if="!videoMode"><ChatList /></el-aside>
      <el-container v-if="videoMode">
        <el-main>
          <VideoChatDetail />
        </el-main>
      </el-container>
      <el-container>
        <transition name="chat-change" mode="out-in">
          <div v-if="store.state.selected_room"><ChatDetail /></div>
        </transition>
        <transition name="chat-change" mode="out-in">
          <el-aside v-if="store.state.selected_room"><UserInfo /></el-aside>
        </transition>
      </el-container>
    </el-container>
  </div>
</template>
<script>
import axios from "@/axios";
import ChatList from "../../components/chat/ChatList.vue";
import ChatDetail from "../../components/chat/ChatDetail.vue";
import UserInfo from "../../components/chat/UserInfo.vue";
import VideoChatDetail from "@/components/VideoChat/VideoChatDetail.vue";
import { useStore } from "vuex";
import { computed } from '@vue/runtime-core';

export default {
  components: {
    ChatList,
    ChatDetail,
    UserInfo,
    VideoChatDetail,
  },
  setup() {
    const store = useStore();
    const videoMode = computed(() => store.state.video_open);

    // axios.get("api/api/chat/admin/init").then((response) => {
    //   store.state.session_key = response.data;

    //   console.log("CHAT AREA" + store.state.session_key);
    // });

    return {
      store,
      videoMode,
    };
  },
};
</script>
<style>
.el-main {
  padding: 0;
  margin: 0;
}
/* 생성 부분 */
.chat-change-enter-from {
  opacity: 0;
}
.chat-change-enter-to {
  opacity: 1;
}
.chat-change-enter-active {
  transition: all 0.2s ease-out;
}
/* 소멸 부분 */
.chat-change-leave-from {
  opacity: 1;
}
.chat-change-leave-to {
  opacity: 0;
}
.chat-change-leave-active {
  transition: all 0.2s ease-out;
}
</style>
