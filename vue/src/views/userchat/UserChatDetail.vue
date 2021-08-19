<template>
  <div v-if="isOpen == 'OPEN'">
    <div v-loading="loading">상담 연결 중입니다. 잠시만 기다려주세요.</div>
  </div>
  <div v-if="isOpen == 'LIVE'">상담이 시작되었습니다.</div>
  <div v-for="(msg, index) in messages" :key="index">
    <el-row>
      <el-col v-if="msg.fk_author_idx == userPkidx">
        <div class="message-me">
          {{ msg.message }}
        </div>
      </el-col>
      <el-col v-else>
        <div class="message-other">
          {{ msg.message }}
          <br v-if="msg.type == 'VID'" />
          <el-button class="accept-btn" v-if="msg.type == 'VID'" @click="openVideo">
            수락하기
          </el-button>
        </div>
      </el-col>
    </el-row>
  </div>
  <p>{{ closeMsg }}</p>
</template>
<script>
import { useStore } from 'vuex';
import { computed } from 'vue';

export default {
  name: 'Chat',
  components: {},
  setup() {
    const store = useStore();
    const userPkidx = computed(() => store.state.auth.user.pk_idx);
    const sessionId = computed(() => store.getters['get_selected_idx']);
    const messages = computed(() => store.getters['get_user_messages']);
    const isOpen = computed(() => store.getters['get_user_room_status']);
    const closeMsg = computed(() => store.getters['clostMsgGetter']);
    const loading = true;
    const openVideo = () => {
      store.commit('OPEN_VIDEO');
    };

    return {
      store,
      sessionId,
      userPkidx,
      messages,
      isOpen,
      loading,
      closeMsg,
      openVideo,
    };
  },
};
</script>
<style scoped>
.accept-btn {
  cursor: pointer;
  margin-top: 0.5rem;
}
#topMessages {
  display: block;
  top: 0px;
  height: 21rem;
  width: 100%;
}
#bottomInput {
  bottom: 0px;
  width: 100%;
}
.el-scroll {
  overflow-x: hidden;
}
.message-me {
  /* border: 0.1px solid #004226; */
  border-radius: 10px 10px 0px 10px;
  color: white;
  background: #006f3e;
  float: right;
  padding: 10px;
  margin: 5px 10px 5px 5px;
  max-width: 300px;
  text-align: right;
}
.message-other {
  border-radius: 10px 10px 10px 0px;
  background-color: #f7f4f0;
  float: left;
  padding: 10px;
  margin: 5px 10px 5px 5px;
  max-width: 300px;
  text-align: left;
}
#inputBox {
  width: 90%;
  height: 100%;
  background-color: transparent;
  border: 0px solid #eee;
}
.vr:hover {
  background-color: white;
}
</style>
