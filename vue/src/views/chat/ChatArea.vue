<template>
  <div>
    <el-container>
      <el-aside v-if="videoStatus == 'CLOSE'"><ChatList /></el-aside>
      <el-main v-else>
        <VideoChatDetail />
      </el-main>
      <el-container>
        <!-- <transition name="chat-change" mode="out-in"> -->
        <div v-if="sessionId"><ChatDetail /></div>
        <div v-else class="center-content">
          <img class="clear-img" src="@/assets/FixLogo.png" alt="logo" />
        </div>
        <!-- </transition> -->
        <!-- <transition name="chat-change" mode="out-in"> -->
        <el-aside v-if="sessionId"><UserInfo /></el-aside>
        <!-- </transition> -->
      </el-container>
    </el-container>
  </div>
</template>
<script>
import ChatList from "../../components/chat/ChatList.vue";
import ChatDetail from "../../components/chat/ChatDetail.vue";
import UserInfo from "../../components/chat/UserInfo.vue";
import VideoChatDetail from "@/components/VideoChat/VideoChatDetail.vue";
import { useStore } from "vuex";
import { computed } from "vue";

export default {
  components: {
    ChatList,
    ChatDetail,
    UserInfo,
    VideoChatDetail,
  },
  setup() {
    const store = useStore();
    const sessionId = computed(() => store.getters["get_selected_idx"]);
    const videoStatus = computed(() => store.state.video_status);
    return {
      store,
      sessionId,
      videoStatus,
    };
  },
};
</script>
<style>
.el-main {
  padding: 0;
  margin: 0;
}
.center-content {
  text-align: center;
  display: block;
  margin: auto;
}

.clear-img {
  opacity: 0.7;
}

/* 생성 부분 */
/* 소멸 부분 */
/* .chat-change-enter-from {
  opacity: 0;
}
.chat-change-enter-to {
  opacity: 1;
}
.chat-change-enter-active {
  transition: all 0.2s ease-out;
}
.chat-change-leave-from {
  opacity: 1;
}
.chat-change-leave-to {
  opacity: 0;
}
.chat-change-leave-active {
  transition: all 0.2s ease-out;
} */
</style>
