<template>
  <div v-if="isOpen == 'OPEN'">
    <!-- <p>현재 모든 상담사가 상담 중입니다. 잠시만 기다려주세요.</p> -->
    <div v-loading="loading">상담 연결 중입니다.</div>
    <i class="el-icon-loading" style="margin-right: 3px"></i>
    <!-- <i class="el-icon-loading"></i>
    <p>이거 기다리는거 디자인 물어보기</p> -->
  </div>
  <div v-if="isOpen == 'LIVE'">LIVE</div>
  <!-- 실시간채팅 시작 -->
  <div v-for="(msg, index) in messages" :key="index">
    <el-row>
      <el-col v-if="msg.fk_author_idx == userPkidx">
        <div class="message-me">
          {{ msg.message }}
        </div>
      </el-col>
      <el-col v-else>
        <div class="message-other">{{ msg.message }}</div>
        <div
          class="message-other vr"
          v-if="msg.type == 'VID'"
          @click="openVideo"
        >
          메세지를 클릭해 주세요.
        </div>
      </el-col>
    </el-row>
  </div>
  <p>{{ closeMsg }}</p>
  <!-- 실시간채팅 끝 -->
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
    const sessionId = computed(() => store.getters['get_selected_idx']); //user가 생성한 방 id
    const messages = computed(() => store.getters['get_user_messages']);
    const isOpen = computed(() => store.getters['get_user_room_status']);
    const closeMsg = computed(() => store.getters['clostMsgGetter']);
    const loading = true;
    const openVideo = () => {
      store.commit("OPEN_VIDEO");
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
  border: 1px solid #f7f4f0;
  border-radius: 10px 10px 0px 10px;
  background: #f7f4f0;
  float: right;
  padding: 10px;
  margin: 5px 10px 5px 5px;
  max-width: 300px;
}
.message-other {
  border: 1px solid #27251f;
  border-radius: 10px 10px 10px 0px;
  color: #fff;
  background: #27251f;
  float: left;
  padding: 10px;
  margin: 5px 10px 5px 5px;
  max-width: 300px;
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
/* hover로 버튼 색 변하게 하기: 추가기능 */
</style>
