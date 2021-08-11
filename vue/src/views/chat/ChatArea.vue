<template lang="">
  <div>
    <el-container>
      <el-aside v-if="videoMode == 'CLOSE'"><ChatList /></el-aside>
      <el-container v-if="videoMode != 'CLOSE'">
        <el-main>
          <VideoChatDetail />
        </el-main>
      </el-container>
      <el-container>
        <!-- <el-header v-if="store.state.selected_room"><UserTitle /></el-header> -->
        <!-- <el-main> -->
        <!-- <el-container> -->
        <transition name="chat-change" mode="out-in">
          <div v-if="store.state.selected_room"><ChatDetail /></div>
        </transition>
        <transition name="chat-change" mode="out-in">
          <el-aside v-if="store.state.selected_room"><UserInfo /></el-aside>
        </transition>
        <!-- </el-container> -->
        <!-- </el-main> -->
      </el-container>
    </el-container>
  </div>
</template>
<script>
import ChatList from "../../components/chat/ChatList.vue";
import ChatDetail from "../../components/chat/ChatDetail.vue";
import VideoChatDetail from "@/components/VideoChat/VideoChatDetail.vue";
import UserInfo from "../../components/chat/UserInfo.vue";
import { computed } from "vue";
import { useStore } from "vuex";

export default {
  components: {
    ChatList,
    VideoChatDetail,
    ChatDetail,
    UserInfo,
  },
  setup() {
    const store = useStore();
    const videoMode = computed(() => store.state.video_open);

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
