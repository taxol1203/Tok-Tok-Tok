<template>
  <div style="height: 100vh">
    <div v-if="videoStatus == 'CLOSE'">
      <div style="width: 25%; float: left; height: 100vh">
        <ChatList />
      </div>
      <el-container style="width: 75%; height: 87vh">
        <div
          v-if="sessionId"
          style="width: 66%; margin-top: 10px; margin-left: 20px; display: relative"
        >
          <ChatDetail />
        </div>
        <div v-else class="center-content">
          <img class="clear-img" src="@/assets/FixLogo.png" alt="logo" />
        </div>
        <div v-if="sessionId" style="width: 33%; right: 10px; margin: 20px">
          <UserInfo />
        </div>
      </el-container>
    </div>
    <div v-else>
      <el-row>
        <el-col :span="12">
          <div style="height: 100vh; float: left; padding: 10px">
            <VideoChatDetail />
          </div>
        </el-col>
        <el-col :span="11">
          <div style="height: 100%">
            <div v-if="sessionId" style="margin-top: 10px; margin-left: 20px; display: relative">
              <ChatDetail />
            </div>
          </div>
        </el-col>
        <el-col :span="1"></el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
import ChatList from '../../components/chat/ChatList.vue';
import ChatDetail from '../../components/chat/ChatDetail.vue';
import UserInfo from '../../components/chat/UserInfo.vue';
import VideoChatDetail from '@/components/VideoChat/VideoChatDetail.vue';
import { useStore } from 'vuex';
import { computed } from 'vue';

export default {
  components: {
    ChatList,
    ChatDetail,
    UserInfo,
    VideoChatDetail,
  },
  setup() {
    const store = useStore();
    const sessionId = computed(() => store.getters['get_selected_idx']);
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
</style>
